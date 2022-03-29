package Server;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private final Socket socket;
    private ObjectInputStream in;
    private PrintWriter out;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        SharedState sharedState = SharedState.getInstance();

        // TODO: Spawn Separate thread for checking alive message

        try {
            // Create input stream and output stream
            out = new PrintWriter(this.socket.getOutputStream(), true);
            in = new ObjectInputStream(socket.getInputStream());

            // Firstly, gather info about client
            out.print(Headers.CLIENT_INFO_AWAITING);
            System.out.println("data sent");

            while (true) {
                String response = in.readUTF();
                if (response.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                    System.out.println("Client info sent");
                    break;
                }
                // Send info to the client that server is waiting for the information about client
                // Accept line from the connection
//                String response = in.readUTF();
//                System.out.println(response);
//
//                // If user sends CLIENT_INFO header
//                // Read line of code containing unique id, ip address, name of the user
//                if (response.equals(Headers.CLIENT_INFO_SENDING.toString())) {
//                    String clientInfo = in.readUTF();
//                    if (sharedState.isClientInfoValid(clientInfo)) {
//                        // If client data are valid, send header informing about that
//                        out.writeChars(Headers.CLIENT_INFO_VALID.toString());
//                        sharedState.addMember(in.readUTF(), this.getId(), socket.getInetAddress().toString());
//                    } else {
//                        out.writeChars(Headers.CLIENT_INFO_INVALID.toString());
//
//                        // Close the connection and terminate the thread
//                        sharedState.removeMember(this.getId());
//                        break;
//                    }
//                } else {
//                    // If header is different than CLIENT_INFO_SENDING, send message to client that invalid data was sent
//                    out.writeChars(Headers.CLIENT_INFO_INVALID.toString());
//
//                    // Close the connection and terminate the thread
//                    sharedState.removeMember(this.getId());
//                    break;
//                }
            }

            // Then wait for other information like message or disconnect ??
//            while(!this.isInterrupted()) {
//                String response = in.readUTF();
//
//                if (response.equals(Headers.MESSAGE.toString())) {
//                    response = in.readUTF();
//                    sharedState.addMessage(response, this.getId());
//                } else {
//                    out.writeChars(Headers.INVALID_HEADER.toString());
//                }
//            }

        } catch (IOException exception) {
            // In case of error remove user from members map
            sharedState.removeMember(this.getId());
            exception.printStackTrace();
        }
    }


    public void sendMessage(@NotNull Headers messageHeader, String messageContent) {
//        try {
//            out.println(messageHeader.toString());
//            out.println(messageContent);
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void sendMembersList(@NotNull Headers messageHeader, Collection<Member> members) {
//        try {
//            out.println(messageHeader.toString());
//            out.println(members);
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }
}