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
    private Scanner in;
    private PrintWriter out;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Create input stream and output stream
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            SharedState sharedState = SharedState.getInstance();

            while (true) {
                // Send info to the client that server is waiting for the information about client
                out.println(Headers.CLIENT_INFO_AWAITING);
                // Accept line from the connection
                String response = in.nextLine();

                // If user sends CLIENT_INFO header
                // Read line of code containing unique id, ip address, name of the user
                if (response.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                    String clientInfo = in.nextLine();
                    if (sharedState.isClientInfoValid(clientInfo)) {
                        // If client data are valid, send header informing about that
                        out.println(Headers.CLIENT_INFO_VALID.toString());

                        sharedState.addMember(in.nextLine(), this.getId());
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
                    sharedState.addMessage(response, this.getId());
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