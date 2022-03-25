package Server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.Vector;

// Singleton class which is responsible for storing the state of the application
public class SharedState {
    private static SharedState instance = null;

    private Vector<String> messages;
    // This list is to maintain the order of the members
    private LinkedList<UUID> membersOrder;
    // This hash map is to store members.
    // Members will be accessed frequently so map is the best ds for that reason
    private HashMap<Long, Member> membersMap;

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

    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    public void initiateMember(ConnectionHandler newConnection) {
        Member newMember = new Member(newConnection);
        this.membersMap.put(newConnection.getId(), newMember);
    }

    public synchronized void addMessage(String messageContent, long senderId) {
        this.messages.add(messageContent);

        // Broadcast newly added message to every other member
        this.broadcastMessage(messageContent, senderId);
    }

    // It finalizes the process of creating the user
    public synchronized void addMember(String clientInfo, long connectionId) {
        // TODO: Get returns the reference right?
        Member member = this.membersMap.get(connectionId);

        // Update the member by parsed id and username
        String[] parsedClientInfo = this.parseClientInfo(clientInfo);
        member.setId(parsedClientInfo[0]);
        member.setUsername(parsedClientInfo[1]);

        // Once the user is fully created, add him to the membersOrder list
        this.membersOrder.add(member.getId());

        // TODO: broadcast new array of users along with their ids
    }

    // It checks whether the id is unique
    // Client info should have structure of: "uniqueId,username"
    // This method is synchronized because it can be used by multiple Threads at the same time
    public synchronized boolean isClientInfoValid(String clientInfo) {
        String[] parsedInfo = this.parseClientInfo(clientInfo);
        return !this.membersMap.containsKey(Long.parseLong(parsedInfo[0]));
    }

    // It sends the broadcast message to every connected member
    // with updated list of the users
    private void updateMembers() {
        for (Member member: this.membersMap.values()) {
            // TODO: Get list of users and parse it's names after comma
            String usersString = "";
            ConnectionHandler connection = member.getConnection();
            connection.sendMessage(Headers.USERS_LIST, usersString);
        }
    }

    public void removeAllMembers() {
        for (Member member : this.membersMap.values()) {
            member.closeConnection();
        }
        this.membersMap.clear();
        this.membersOrder.clear();
    }

    // It removes member that comes from inactive connection
    public synchronized void removeMember(long connectionId) {
        Member member = this.membersMap.get(connectionId);

        this.membersMap.remove(connectionId);

        int toRemove = this.membersOrder.indexOf(member.getId());
        if (toRemove >= 0) {
            this.membersOrder.remove(toRemove);
        }
    }

}
