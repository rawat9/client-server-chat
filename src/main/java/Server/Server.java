package Server;

/**
 * Author: Oskar
 */

public class Server {
    // Main server thread
    private Thread mainServerThread;

    public Server(int port) {
        // Initialize necessary values
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
        Server server = new Server(Integer.parseInt(args[0]));
        server.startServer();
    }
}
