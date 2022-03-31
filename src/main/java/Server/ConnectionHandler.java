package Server;

import Client.Message;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

// New thread that handles the communication between server and one client
// New instance is created every time new member joins the server
class ConnectionHandler extends Thread {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final SharedState sharedState;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        this.sharedState = SharedState.getInstance();
    }

    public void run() {
        // TODO: Spawn Separate thread for checking alive message

        try {
            // Firstly initiate connection - get info about user
            this.initiateConnection();

            System.out.println("Connection initiated");
            // Receive any message
            while (!this.isInterrupted()) {
                String header = in.readUTF();
                System.out.println("HEADER_RECEIVED: " + header);

                if (header.equals(Headers.MESSAGE.toString())) {
                    Message message = (Message) in.readObject();
                    sharedState.addMessage(message, this.getId());
                } else {
                    out.writeChars(Headers.INVALID_HEADER.toString());
                }
            }
        } catch (IOException exception) {
            // In case of exception remove user from members map
            // This indicates that user disconnected
            sharedState.removeMember(this.getId());
            System.out.println("User Disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initiateConnection() throws IOException {
        // Create input stream and output stream
        out = new ObjectOutputStream(this.socket.getOutputStream());
        in = new ObjectInputStream(this.socket.getInputStream());

        // Send info to the client that server is waiting for the information about client
        this.sendHeader(Headers.CLIENT_INFO_AWAITING);

        while (true) {
            // Accept line from the connection
            String header = in.readUTF();
            System.out.println("HEADER_RECEIVED: " + header);

            // If user sends CLIENT_INFO header
            // Read line of code containing unique id, ip address, name of the user
            if (header.equals(Headers.CLIENT_INFO_SENDING.toString())) {
                String clientInfo = in.readUTF();

                if (sharedState.isClientInfoValid(clientInfo)) {
                    // If client data are valid, send header informing about that
                    this.sendHeader(Headers.CLIENT_INFO_VALID);
                    sharedState.addMember(clientInfo, this.getId(), socket.getInetAddress().toString());
                    System.out.println("member added");
                    return;
                } else {
                    this.sendHeader(Headers.CLIENT_INFO_INVALID);
                }
            } else {
                // If header is different than CLIENT_INFO_SENDING, send message to client that invalid data was sent
                this.sendHeader(Headers.CLIENT_INFO_INVALID);
            }
        }
    }

    private void sendHeader(@NotNull Headers header) {
        try {
            out.writeUTF(header.toString());
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessage(@NotNull Headers messageHeader, Message message) {
        try {
            this.sendHeader(messageHeader);

            out.writeObject(message);
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendCoordinatorInfo() {
        this.sendHeader(Headers.COORDINATOR_INFO);
    }

    public void sendMembersList(Collection<Member> members) {
        try {
            this.sendHeader(Headers.USERS_LIST);

            ArrayList<User> users = new ArrayList<>();
            for (Member member : members) {
                users.add(member.getUser());
            }

            out.writeObject(users);
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}