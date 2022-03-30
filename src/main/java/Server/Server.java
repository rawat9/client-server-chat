package Server;

/**
 * Author: Oskar
 */

public class Server {
    // Main server thread
    private Thread mainServerThread;
    // ip address of the server
    private String ipAddress;
    // port of the server
    private int port;

    public Server() {
        // Initialize necessary values
        this.port = 6666;
        this.ipAddress = "127.0.0.1";
        this.mainServerThread = new MainServerThread(port);
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
                SharedState sharedState = SharedState.getInstance();
                sharedState.removeAllMembers();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing the server");
        Server server = new Server();
        server.startServer();
    }
}
