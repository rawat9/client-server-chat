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
     private ObjectInputStream inputStream;
     private ObjectOutputStream outputStream;
     private SimpleDateFormat timestamp = new SimpleDateFormat("HH:mm:ss");
     private ConnectionController connectWindow;
     private ClientController window;

     public Client() {
          try {
               contactServer();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

//     public Client(String ID, String username, String address, int port) {
//          this.ID = ID;
//          this.username = username;
//          this.address = address;
//          this.port = port;
//     }


     public void contactServer() throws IOException {
          try {
               connect();
               socket = new Socket(this.address, this.port);
               inputStream = new ObjectInputStream(socket.getInputStream());
               outputStream = new ObjectOutputStream(socket.getOutputStream());
               isConnected = true;

               new CommunicationHandler().start();

          } catch (ConnectException e) {
               System.err.println("Cannot connect");
          } catch (UnknownHostException u) {
               System.err.println("Don't know about address: " + address);
          }
     }

     public void connect() {
          try {
               connectWindow = new ConnectionController();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     public void setCoordinator() {

     }

     public void isCoordinator() {

     }

//     public void terminate() {
//          isConnected = false;
//     }

     public void sendMessage(String message, String receiverID) {
          try {
               outputStream.writeObject(new Message(this.ID, message, receiverID));
          } catch (IOException e) {
               e.printStackTrace();
          }
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

     public void closeConnections() {
          try {
               inputStream.close();
               outputStream.close();
               this.socket.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public static void main(String[] args) {
          Client c = new Client();
     }
}