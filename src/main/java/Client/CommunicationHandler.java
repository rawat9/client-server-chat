package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationHandler extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientController controller;
    private Socket socket;
    private char header;

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 8081);
            inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                header = inputStream.readChar();
                System.out.println(header);
//                if (message.equals(Headers.MESSAGE)) {
//                    outputStream.writeObject("hi");
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
