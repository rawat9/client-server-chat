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
    private Client client;
    private Boolean isConnected = false;

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
            isConnected = true;

            System.out.println("Waiting for the header");
            while (true) {
                header = inputStream.readUTF();
                System.out.println("Header: " + header);
                // TODO: React to header accordingly
                if (header.equals(Headers.CLIENT_INFO_AWAITING.toString())) {
                    outputStream.writeUTF(Headers.CLIENT_INFO_SENDING.toString());
                    outputStream.flush();
                    System.out.println("!!");
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

     public void closeConnections() {
          try {
               inputStream.close();
               outputStream.close();
               this.socket.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }
}
