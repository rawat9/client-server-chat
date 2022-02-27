/**
 * Author: Oskar
 */

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private Socket socket;
    private Server server;
    private Scanner in;
    private PrintWriter out;

    public ConnectionHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
          in = new Scanner(socket.getInputStream());
          out = new PrintWriter(socket.getOutputStream(), true);

          while (true) {
              out.println(Utils.Headers.CLIENT_INFO_AWAITING);
              String response = in.nextLine();

              // If user sends CLIENT_INFO header
              // Read line of code containing unique id, ip address, name of the user
              if (response.equals(Utils.Headers.CLIENT_INFO_SENDING.toString())) {
                  String clientInfo = in.nextLine();
                  if (server.isClientInfoValid(clientInfo)) {
                      out.println(Utils.Headers.CLIENT_INFO_VALID.toString());
                      server.addMember(in.nextLine());
                  } else {
                      out.println(Utils.Headers.CLIENT_INFO_INVALID.toString());
                  }
                  break;
              }
          }

          while(true) {
            String response = in.nextLine();

            if (response.equals(Utils.Headers.MESSAGE.toString())) {
                response = in.nextLine();
                server.addMessage(response);
            }
          }

          // TODO: finish that part
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessage(@NotNull Utils.Headers messageHeader, String messageContent) {
        out.write(messageHeader.toString());
        out.write(messageContent);
    }
}

// This class extends thread and is used for establishing connections with the sever
class MainServerThread extends Thread {
    private final int port;
    private final Server server;
    public MainServerThread(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    public void run() {
        try {
            ServerSocket listener = new ServerSocket(port);
            while(true) {
                // Wait for every client connection
                Socket connection = listener.accept();
                // Once a client establishes the connection create a new thread
                // Devoted to communication between that client and the server
                server.addNewConnection(new ConnectionHandler(connection, server));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

// TODO: make it a singleton
public class Server {
    // Main server thread
    private Thread mainServerThread;
    // ip address of the server
    private String ipAddress;
    // port of the server
    private int port;
    // list of connected members
    private LinkedList<Client> members;
    private HashMap<Long, ConnectionHandler> activeConnections;
    // List of messages
    private Vector<String> messages;
    // Timeout in miliseconds used for determining the time
    // in which Client has to respond
    private int timeout;

    public Server() {
        this.port = 8081;
        this.ipAddress = "127.0.0.1";
        this.mainServerThread = new MainServerThread(port, this);
        this.activeConnections = new HashMap<Long, ConnectionHandler>();
    }

    // It sends the broadcast message to every connected member
    // with updated list of the users
    private void updateMembers() {
        for (ConnectionHandler activeConnection: activeConnections.values()) {
            // TODO: Get list of users and parse it to JSON
            String usersString = "";
            activeConnection.sendMessage(Utils.Headers.USERS_LIST, usersString);
        }
    }

    // It broadcasts message to every member from the list
    private void broadcastMessage(String messageContent, UUID senderId) {
        for (ConnectionHandler activeConnection : activeConnections.values()) {
//            if (activeConnection.getId())
            activeConnection.sendMessage(Utils.Headers.MESSAGE, messageContent);
        }
    }

    // It starts the main server thread
    public void startServer() {
        if (!this.mainServerThread.isAlive()) {
            this.mainServerThread.start();
        }
    }

    // It stops the server from running by interrupting the main Server thread
    // and connectionHandler thread
    public void stopServer() {
        if (!this.mainServerThread.isInterrupted()) {
            this.mainServerThread.interrupt();
            if (this.mainServerThread.isInterrupted()) {
                for (ConnectionHandler activeConnection : this.activeConnections.values()) {
                    activeConnection.interrupt();
                }
            }
        }
    }

    public void getAllMessages() {

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
        System.out.println("new connection detected");
        this.activeConnections.put(newConnection.getId(), newConnection);
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
