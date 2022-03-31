package Client;

import Server.Headers;
import Server.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationHandler extends Thread {
    private String address;
    private int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private final Client client;

    CommunicationHandler(String address, int port, Client client) {
        this.address = address;
        this.port = port;
        super.start();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            String address = this.address;
            int port = this.port;
            socket = new Socket(address, port);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Waiting for the header");
            while (true) {
                String header = inputStream.readUTF();
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
                    client.setActiveUsersList(users);
                } else if (header.equals(Headers.MESSAGE.toString())) {
                    Message message;
                    message = (Message) inputStream.readObject();
                    client.updateMessage(message);
                } else if (header.equals(Headers.COORDINATOR_INFO.toString())) {
                    client.showCoordinatorInfo();
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
