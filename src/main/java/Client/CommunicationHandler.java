package Client;

import Server.Headers;
import Server.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationHandler extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private final Client client;

    CommunicationHandler(Client client) {
        super.start();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 8000);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Waiting for the header");
            while (true) {
                String header = inputStream.readUTF();
                System.out.println("Header: " + header);
                // TODO: React to header accordingly
                if (header.equals(Headers.CLIENT_INFO_AWAITING.toString())) {
                    this.sendHeader(Headers.CLIENT_INFO_SENDING);

                    String clientInfo = client.getID() + ":" + client.getUsername();
                    outputStream.writeUTF(clientInfo);
                    outputStream.flush();

                } else if (header.equals(Headers.CLIENT_INFO_VALID.toString())) {
                    // If client info is valid then open chat gui
                    client.openChatGUI();
                } else if (header.equals(Headers.USERS_LIST.toString())) {
                    ArrayList<User> users = (ArrayList<User>) inputStream.readObject();
                    for (User user : users) {
                        System.out.println("user: " + user.getUsername());
                    }
                    // TODO: update list of users in the gui
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHeader(Headers header) throws IOException {
        outputStream.writeUTF(header.toString());
        outputStream.flush();
    }

    public void sendMessage(Message message) {
        try {
            // Firstly send header
            outputStream.writeUTF(Headers.MESSAGE.toString());
            outputStream.flush();

            // Then content
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

     public void closeConnections() {
          try {
               inputStream.close();
               outputStream.close();
               this.socket.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }
}
