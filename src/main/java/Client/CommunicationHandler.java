package Client;

import Server.Headers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CommunicationHandler extends Thread {
    private Scanner inputStream;
    private ObjectOutputStream outputStream;
    private ClientController controller;
    private Socket socket;
    private String header;

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 8081);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new Scanner(socket.getInputStream());

            System.out.println("Waiting for the header");
            while (inputStream.hasNextLine()) {
                header = inputStream.nextLine();
                System.out.println("Header: " + header);
                if (header.equals(Headers.CLIENT_INFO_AWAITING.toString())) {
                    outputStream.writeChars(Headers.CLIENT_INFO_SENDING.toString());
                    System.out.println("Działą!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
