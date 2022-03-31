package Client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;


public class Client {
     private String ID;
     private String username;
     private String address;
     private int port;
     private Socket socket;
     private boolean isConnected = false;
     private String receiver = "Everyone";
     private SimpleDateFormat timestamp = new SimpleDateFormat("HH:mm:ss");
     private CommunicationHandler ch;
     private ClientController window;
     private ConnectionController connectWindow;

     public Client() {
          try {
               contactServer();
               if (isConnected) {
                    openChat();
               }
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public void openChat() {
          try {
               window = new ClientController(this);
               ch.setConnected(true);
               window.open();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     public Client(String ID, String username, String address, int port) {
          this.ID = ID;
          this.username = username;
          this.address = address;
          this.port = port;
     }

     public void contactServer() throws IOException {
          connect();
          ch = new CommunicationHandler(this);
     }

     public void connect() {
          try {
               connectWindow = new ConnectionController(this);
               connectWindow.open();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     public void setCoordinator() {

     }

     public void isCoordinator() {

     }

     public void sendMessage(String message, String receiverID) {
          Message newMessage = new Message(this.ID, message, receiverID);
          ch.sendMessage(newMessage);
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getAddress() {
          return address;
     }

     public int getPort() {
          return this.port;
     }

     public String getID() {
          return ID;
     }

     public void setID(String ID) {
          this.ID = ID;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public void setPort(int port) {
          this.port = port;
     }


     public static void main(String[] args) {
          Client c = new Client();
     }
}