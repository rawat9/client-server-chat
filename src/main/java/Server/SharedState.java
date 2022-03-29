package Server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

// Singleton class which is responsible for storing the state of the application
public class SharedState {
    private static SharedState instance = null;

    private final Vector<String> messages;
    // This list is to maintain the order of the members
    private final LinkedList<String> membersOrder;
    // This hash map is to store members.
    // Members will be accessed frequently so map is the best ds for that reason
    private final HashMap<Long, Member> membersMap;

    private SharedState() {
        this.messages = new Vector<>();
        this.membersMap = new HashMap<>();
        this.membersOrder = new LinkedList<>();
    }

    private String[] parseClientInfo(String clientInfo) {
        return clientInfo.split(",");
    }

    // It broadcasts message to every member from the list apart from the sender
    private void broadcastMessage(String messageContent, long senderId) {
        for (Member member : this.membersMap.values()) {
            ConnectionHandler connection = member.getConnection();
            if (connection.getId() != senderId) {
                connection.sendMessage(Headers.MESSAGE, messageContent);
            }
        }
    }

    // It sends the broadcast message to every connected member
    // with updated list of the users
    private void updateMembers() {
        for (Member member: this.membersMap.values()) {
            ConnectionHandler connection = member.getConnection();
            connection.sendMembersList(Headers.USERS_LIST, this.membersMap.values());
        }
    }

    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    public void initiateMember(ConnectionHandler newConnection) {
        Member newMember = new Member(newConnection);
        this.membersMap.put(newConnection.getId(), newMember);
        System.out.println("New user initiated. Now he has to provide username and uid through the stream");
    }

    // It adds message to the list and broadcasts it to other users
    public synchronized void addMessage(String messageContent, long senderId) {
        this.messages.add(messageContent);

        // Broadcast newly added message to every other member
        this.broadcastMessage(messageContent, senderId);
    }

    // It finalizes the process of creating the user
    public synchronized void addMember(String clientInfo, long threadId, String ipAddress) {
        // Get a reference to the Member stored in the hash map
        Member member = this.membersMap.get(threadId);

        // Update the member by parsed id and username
        String[] parsedClientInfo = this.parseClientInfo(clientInfo);
        member.setId(parsedClientInfo[0]);
        member.setUsername(parsedClientInfo[1]);
        member.setIpAddress(ipAddress);

        // Once the user is fully created, add him to the membersOrder list
        this.membersOrder.add(member.getId());

        if (this.membersOrder.size() == 1) {
            this.sendCoordinatorInfo(threadId);
        }

        this.updateMembers();
    }

    public synchronized void sendCoordinatorInfo(Long threadId) {
        Member member = this.membersMap.get(threadId);
        // Set property isCoordinator to true
        member.setCoordinator();
        // Send message to the user that he is now the coordinator
        member.getConnection().sendMessage(Headers.COORDINATOR_INFO, "true");
    }

    // It checks whether the id is unique
    // Client info should have structure of: "uniqueId,username"
    // This method is synchronized because it can be used by multiple Threads at the same time
    public synchronized boolean isClientInfoValid(String clientInfo) {
        String[] parsedInfo = this.parseClientInfo(clientInfo);
        return !this.membersMap.containsKey(Long.parseLong(parsedInfo[0]));
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
        int toRemove = this.membersOrder.indexOf(member.getId());
        if (toRemove >= 0) {
            this.membersOrder.remove(toRemove);
        }

        // If index of removed user was 0, change coordinator
        if (toRemove == 0 && this.membersOrder.size() > 0) {
            String userId = this.membersOrder.getFirst();

            for(Member newCoordinator : this.membersMap.values()) {
                if (newCoordinator.getId().equals(userId)) {
                    this.sendCoordinatorInfo(newCoordinator.getConnection().getId());
                }
            }
        }

        // Remove member from members map
        this.membersMap.remove(threadId);

        // Send updated list of members
        this.updateMembers();
    }
}