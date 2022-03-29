package Server;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private Socket socket;
    private Scanner in;
    private ObjectOutputStream out;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        SharedState sharedState = SharedState.getInstance();

        // TODO: Spawn Separate thread for checking alive message

        try {
            // Create input stream and output stream
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            // Firstly, gather info about client
            while (true) {
                // Send info to the client that server is waiting for the information about client
                out.writeChars(Headers.CLIENT_INFO_AWAITING.toString());
                // Accept line from the connection
                String response = in.nextLine();

                // If user sends CLIENT_INFO header
                // Read line of code containing unique id, ip address, name of the user
                if (response.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                    String clientInfo = in.nextLine();
                    if (sharedState.isClientInfoValid(clientInfo)) {
                        // If client data are valid, send header informing about that
                        out.writeChars(Headers.CLIENT_INFO_VALID.toString());
                        sharedState.addMember(in.nextLine(), this.getId(), socket.getInetAddress().toString());
                    } else {
                        out.writeChars(Headers.CLIENT_INFO_INVALID.toString());

                        // Close the connection and terminate the thread
                        sharedState.removeMember(this.getId());
                        break;
                    }
                } else {
                    // If header is different than CLIENT_INFO_SENDING, send message to client that invalid data was sent
                    out.writeChars(Headers.CLIENT_INFO_INVALID.toString());

                    // Close the connection and terminate the thread
                    sharedState.removeMember(this.getId());
                    break;
                }
            }

            // Then wait for other information like message or disconnect ??
            while(!this.isInterrupted()) {
                String response = in.nextLine();

                if (response.equals(Headers.MESSAGE.toString())) {
                    response = in.nextLine();
                    sharedState.addMessage(response, this.getId());
                } else {
                    out.writeChars(Headers.INVALID_HEADER.toString());
                }
            }

        } catch (IOException exception) {
            // In case of error remove user from members map
            sharedState.removeMember(this.getId());
            exception.printStackTrace();
        }
    }


    public void sendMessage(@NotNull Headers messageHeader, String messageContent) {
        try {
            out.writeChars(messageHeader.toString());
            out.writeChars(messageContent);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMembersList(@NotNull Headers messageHeader, Collection<Member> members) {
        try {
            out.writeChars(messageHeader.toString());
            out.writeObject(members);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}