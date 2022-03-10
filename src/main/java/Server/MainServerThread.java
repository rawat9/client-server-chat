/**
 * Author: Oskar Domingos
 * Class responsible for main thread of the server which accepts new connections
 */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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