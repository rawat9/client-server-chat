package Client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Client {
     private String ID;
     private String username;
     private String address;
     private int port;
     private CommunicationHandler communicationHandler;
     private ClientController window;
     private ConnectionController connectWindow;

     public Client() {
          openConnectionGUI();
     }

     public void openConnectionGUI() {
          connectWindow = new ConnectionController(this);
          connectWindow.open();
     }

     public void openChatGUI() {
          window = new ClientController(this);
          window.open();
     }

     public void establishConnection(String address, int port) {
          communicationHandler = new CommunicationHandler(address, port, this);
     }

     /**
      * Updates the messages on Chat Window GUI
      * @param message
      */
     public void updateMessage(Message message) {
          String msg = message.getContent();
          String text = "[" + message.getTimestamp() + "] " + " " + msg + "\n";
          window.getServer().append(text);
     }

     public void sendMessage(String message, String receiverID) {
          Message newMessage = new Message(this.ID, message, receiverID);
          communicationHandler.sendMessage(newMessage);
          window.getMessage().setText("");
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