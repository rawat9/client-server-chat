package Server;

import Client.Message;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.Collection;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        SharedState sharedState = SharedState.getInstance();

        // TODO: Spawn Separate thread for checking alive message

        try {
            // Create input stream and output stream
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Send info to the client that server is waiting for the information about client
            out.writeUTF(Headers.CLIENT_INFO_AWAITING.toString());
            out.flush();

            // Accept line from the connection
            String header = in.readUTF();
            System.out.println("HEADER_RECEIVED: " + header);

            // If user sends CLIENT_INFO header
            // Read line of code containing unique id, ip address, name of the user
            if (header.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                String clientInfo = in.readUTF();

                if (sharedState.isClientInfoValid(clientInfo)) {
                    // If client data are valid, send header informing about that
                    out.writeChars(Headers.CLIENT_INFO_VALID.toString());
                    out.flush();

                    sharedState.addMember(in.readUTF(), this.getId(), socket.getInetAddress().toString());
                } else {
                    out.writeChars(Headers.CLIENT_INFO_INVALID.toString());
                    out.flush();

                    // Close the connection and terminate the thread
                    sharedState.removeMember(this.getId());
                }
            } else {
                // If header is different than CLIENT_INFO_SENDING, send message to client that invalid data was sent
                out.writeChars(Headers.CLIENT_INFO_INVALID.toString());

                // Close the connection and terminate the thread
                sharedState.removeMember(this.getId());
            }

            // Then wait for other information like message or disconnect ??
            while (!this.isInterrupted()) {
                String response = in.readUTF();

                if (response.equals(Headers.MESSAGE.toString())) {
                    response = in.readUTF();
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


    public void sendMessage(@NotNull Headers messageHeader, Message message) {
        try {
            out.writeUTF(messageHeader.toString());
            out.flush();

            out.writeObject(message);
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMembersList(@NotNull Headers messageHeader, Collection<Member> members) {
        try {
            out.writeUTF(messageHeader.toString());
            out.flush();

            out.writeObject(members);
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}