import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private Socket socket;
    private Server server;
    private Scanner in;
    private PrintWriter out;

    private final String AWAITING_CLIENT_INFO = "AWAITING_CLIENT_INFO";

    public ConnectionHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
          in = new Scanner(socket.getInputStream());
          out = new PrintWriter(socket.getOutputStream(), true);

          while (true) {
              out.println(AWAITING_CLIENT_INFO);
              String response = in.nextLine();
              // If user sends CLIENT_INFO header
              // Read line of code containing unique id, ip address, name of the user
              if (response.equals("CLIENT_INFO")) {
                  server.addMember(in.nextLine());
              }
          }

          // TODO: finish that part
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
            while (true) {
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

public class Server {
    // Main server thread
    private Thread mainServerThread;
    private boolean isServerRunning;
    // ip address of the server
    private String ipAddress;
    // port of the server
    private int port;
    // list of connected members
    private LinkedList<Client> members;
    // List of messages
    private Vector<String> messages;
    // Timeout in miliseconds used for determining the time
    // in which Client has to respond
    private int timeout;
    private Vector<ConnectionHandler> activeConnections;

    public Server() {
        this.port = 8081;
        this.ipAddress = "127.0.0.1";
        this.mainServerThread = new MainServerThread(port, this);
    }

    public void startServer() {
        this.mainServerThread.start();
        this.isServerRunning = true;
    }

    // It stops the server from running by interrupting the main Server thread
    // and connectionHandler thread
    public void stopServer() {
        this.mainServerThread.interrupt();
        if (this.mainServerThread.isInterrupted()) {
            for(ConnectionHandler activeConnection: this.activeConnections) {
                activeConnection.interrupt();
            }
            this.isServerRunning = false;
        }
    }

    // It sends the broadcast message to every other connected member
    // with updated list of the users
    public void updateMembers() {

    }

    // It broadcasts message to every member from the list
    public void broadcastMessage() {

    }

    // It adds members to the list of members
    public void addMember(String clientInfo) {
        // TODO: Parse info about the client from the user
//        this.members.add(newMember);
    }

    public void addNewConnection(ConnectionHandler newConnection) {
        this.activeConnections.add(newConnection);
    }

    public static void main(String[] args) {
        System.out.println("Initializing the server");
        Server server = new Server();
    }
}
