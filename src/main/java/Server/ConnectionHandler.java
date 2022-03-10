package Server;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
                out.println(Headers.CLIENT_INFO_AWAITING);
                String response = in.nextLine();

                // If user sends CLIENT_INFO header
                // Read line of code containing unique id, ip address, name of the user
                if (response.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                    String clientInfo = in.nextLine();
                    if (server.isClientInfoValid(clientInfo)) {
                        out.println(Headers.CLIENT_INFO_VALID.toString());
                        server.addMember(in.nextLine());
                    } else {
                        out.println(Headers.CLIENT_INFO_INVALID.toString());
                    }
                    break;
                }
            }

            while(true) {
                String response = in.nextLine();

                if (response.equals(Headers.MESSAGE.toString())) {
                    response = in.nextLine();
                    server.addMessage(response);
                }
            }

            // TODO: finish that part
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessage(@NotNull Headers messageHeader, String messageContent) {
        out.write(messageHeader.toString());
        out.write(messageContent);
    }
}