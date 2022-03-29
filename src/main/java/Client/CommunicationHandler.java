package Client;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommunicationHandler extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientController controller;
    private String response = "";

    @Override
    public void run() {
        try {
            JTextArea server = controller.getServer();
            Message p;
            while ((p = (Message) inputStream.readObject()) != null) {

                if (p.getReceiverID().equals("Everyone")) {
                    response = response + p.toString() + "\n";
                    server.setText(response);
                }

                // TODO: 29/03/2022 UPDATE header enum 
                else if (p.getReceiverID().equals("Update")) {
                    response = response + p.toString() + "\n";
                    server.setText(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
