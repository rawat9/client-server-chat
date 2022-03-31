package Server;

import Client.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Vector;

// Singleton class which is responsible for storing the state of the application
public class SharedState {
    private static SharedState instance = null;

    private final Vector<Message> messages;
    // This list is to maintain the order of the members
    private final LinkedList<Long> membersOrder;
    // This hash map is to store members.
    // Members will be accessed frequently so map is the best ds for that reason
    private final HashMap<Long, Member> membersMap;

    private SharedState() {
        this.messages = new Vector<>();
        this.membersMap = new HashMap<>();
        this.membersOrder = new LinkedList<>();
    }

    private String[] parseClientInfo(String clientInfo) {
        return clientInfo.split(":");
    }

    private void broadcastMessage(Message messageContent) {
        for (Member member : this.membersMap.values()) {
            ConnectionHandler connection = member.getConnection();
            connection.sendMessage(messageContent);
        }
    }

    // It sends the broadcast message to every connected member
    // with updated list of the users
    private void updateMembers() {
        for (Member member: this.membersMap.values()) {
            ConnectionHandler connection = member.getConnection();
            connection.sendMembersList(this.membersMap.values());
        }
    }

    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    // It adds message to the list and broadcasts it to other users
    public synchronized void addMessage(Message messageContent) {
        this.messages.add(messageContent);

        if (Objects.equals(messageContent.getReceiverID(), "Everyone")) {
            // Broadcast newly added message to every other member
            this.broadcastMessage(messageContent);
        } else {
            for (Member member : membersMap.values()) {
                User user = member.getUser();
                if (Objects.equals(user.getID(), messageContent.getReceiverID())) {
                    member.getConnection().sendMessage(messageContent);
                }
            }
        }
    }

    public void initiateMember(ConnectionHandler newConnection) {
        newConnection.start();
        Member newMember = new Member(newConnection);
        this.membersMap.put(newConnection.getId(), newMember);
    }

    // It finalizes the process of creating the user
    public synchronized void addMember(String clientInfo, long threadId, String ipAddress) {
        // Get a reference to the Member stored in the hash map
        Member member = this.membersMap.get(threadId);

        // Update the member by parsed id and username
        String[] parsedClientInfo = this.parseClientInfo(clientInfo);
        member.assignUser(parsedClientInfo[0], parsedClientInfo[1], ipAddress);

        // Once the user is fully created, add him to the membersOrder list
        this.membersOrder.add(threadId);

        if (this.membersOrder.size() == 1) {
            this.sendCoordinatorInfo(threadId);
        }

        this.updateMembers();
    }

    public void removeAllMembers() {
        for (Member member : this.membersMap.values()) {
            member.closeConnection();
        }
        this.membersMap.clear();
        this.membersOrder.clear();
    }

    // It removes member that comes from inactive connection
    public synchronized void removeMember(long threadId) {
        Member member = this.membersMap.get(threadId);

        // Interrupt the thread
        member.closeConnection();

        // Remove user from LinkedList if that user was there
        int toRemove = this.membersOrder.indexOf(threadId);
        if (toRemove >= 0) {
            this.membersOrder.remove(toRemove);
        }

        // If index of removed user was 0, change coordinator
        if (toRemove == 0 && this.membersOrder.size() > 0) {
            this.sendCoordinatorInfo(this.membersOrder.getFirst());
        }

        // Remove member from members map
        this.membersMap.remove(threadId);

        // Send updated list of members
        this.updateMembers();
    }

    public synchronized void sendCoordinatorInfo(Long threadId) {
        Member member = this.membersMap.get(threadId);
        // Set property isCoordinator to true
        member.getUser().setCoordinator();
        // Send message to the user that he is now the coordinator
        member.getConnection().sendCoordinatorInfo();
    }

    // It checks whether the id is unique
    // Client info should have structure of: "uniqueId,username"
    // This method is synchronized because it can be used by multiple Threads at the same time
    public synchronized boolean isClientInfoValid(String clientInfo) {
        String[] parsedInfo = this.parseClientInfo(clientInfo);
        for (long threadId : membersOrder) {
            User user = membersMap.get(threadId).getUser();
            if (Objects.equals(user.getID(), parsedInfo[0])) {
                return false;
            }
        }
        return true;
    }

    public Vector<Message> getMessages() {
        return messages;
    }

    public LinkedList<Long> getMembersOrder() {
        return membersOrder;
    }

    public HashMap<Long, Member> getMembersMap() {
        return membersMap;
    }
}