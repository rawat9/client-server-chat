package Server; /**
 * Author: Oskar
 */


import java.util.*;

// TODO: make it a singleton
public class Server {
    // Main server thread
    private Thread mainServerThread;
    // ip address of the server
    private String ipAddress;
    // port of the server
    private int port;
    // list of connected members
    private LinkedList<Member> members;
    // List of messages
    private Vector<String> messages;
    // Timeout in miliseconds used for determining the time
    // in which Client has to respond
    private int timeout;

    public Server() {
        // Initialize necessary values
        this.timeout = 1000;
        this.port = 8081;
        this.ipAddress = "127.0.0.1";
        this.mainServerThread = new MainServerThread(port, this);
        this.messages = new Vector<>();
        this.members = new LinkedList<>();
    }

    // It sends the broadcast message to every connected member
    // with updated list of the users
    private void updateMembers() {
        for (Member member: this.members) {
            // TODO: Get list of users and parse it's names after comma
            String usersString = "";
            ConnectionHandler connection = member.getConnection();
            connection.sendMessage(Headers.USERS_LIST, usersString);

        }
    }

    // It broadcasts message to every member from the list
    private void broadcastMessage(String messageContent, UUID senderId) {
        for (Member member : this.members) {
            if (member.getId() != senderId) {
                ConnectionHandler connection = member.getConnection();
                connection.sendMessage(Headers.MESSAGE, messageContent);
            }
        }
    }

    // It starts the main server thread
    public void startServer() {
        if (!this.mainServerThread.isAlive()) {
            this.mainServerThread.start();
        }
    }

    // It stops the server from running by interrupting the main Server.Server thread
    // and connectionHandler thread
    public void stopServer() {
        if (!this.mainServerThread.isInterrupted()) {
            // First, interrupt server thread
            this.mainServerThread.interrupt();
            if (this.mainServerThread.isInterrupted()) {
                // Then interrupt every thread assigned to the member
                for (Member member : this.members) {
                    member.closeConnection();
                }
            }
        }
    }

    // It sends all the messages to newly connected user
    public void getAllMessages(Member member) {

    }

    public void addMessage(String messageContent) {
        this.messages.add(messageContent);
//        this.broadcastMessage(messageContent);
    }

    // It adds members to the list of members
    public void addMember(String clientInfo) {
        // TODO: Parse info about the client from the user
//        this.members.add(newMember);
        this.updateMembers();
    }

    public void addNewConnection(ConnectionHandler newConnection) {
        Member newMember = new Member(newConnection);
        this.members.add(newMember);

        System.out.println("new connection detected");
        System.out.println("Number of active connections: ");
        System.out.println(this.members.size());
    }

    // It checks whether the id is unique
    // TODO: Finish that method
    public boolean isClientInfoValid(String clientInfo) {
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Initializing the server");
        Server server = new Server();
        server.startServer();
    }
}
