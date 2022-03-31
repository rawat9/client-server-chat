package Client;

import Server.Headers;

import java.io.*;
import java.net.Socket;

public class CommunicationHandler extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Client client;
    private Socket socket;
    private String header;
    private String userId;
    private String username;
    private String ipAddress;
    private int port;

    CommunicationHandler(String ipAddress, int port, String userId, String username, Client client) {
        this.ipAddress = ipAddress;
//        this.port = port;
        this.port = 6666;
        this.userId = userId;
        this.username = username;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ipAddress, port);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Waiting for the header");
            while (true) {
                header = inputStream.readUTF();
                System.out.println("Header: " + header);
                // TODO: React to header accordingly
                if (header.equals(Headers.CLIENT_INFO_AWAITING.toString())) {
                    outputStream.writeUTF(Headers.CLIENT_INFO_SENDING.toString());
                    outputStream.flush();

                    outputStream.writeUTF(this.userId+":"+this.username);
                    outputStream.flush();
                } else if (header.equals(Headers.CLIENT_INFO_VALID.toString())) {
                    this.client.openChatWindow();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            System.out.println("sending a message");
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
}
