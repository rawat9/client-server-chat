package Client;

import Server.Headers;

import java.io.*;
import java.net.Socket;

public class CommunicationHandler extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientController controller;
    private Socket socket;
    private String header;

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 6666);

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
                    System.out.println("Działą!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
