package Client;

import Server.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Client {
     private String ID;
     private String username;
     private String address;
     private int port;
     private CommunicationHandler communicationHandler;
     private ClientController window;
     private ConnectionController connectWindow;
     private ArrayList<User> activeUsersList;

     public Client() {
          openConnectionGUI();
     }

     public void openConnectionGUI() {
          connectWindow = new ConnectionController(this);
          connectWindow.open();
     }

     public void openChatGUI() {
          activeUsersList = new ArrayList<>();
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
     public void updateMessage(@NotNull Message message) {
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

     public void setActiveUsersList(ArrayList<User> activeUsersList) {
          this.activeUsersList = activeUsersList;
          window.updateUsersList();
     }

     public ArrayList<User> getActiveUsersList() {
          return this.activeUsersList;
     }

     public static void main(String[] args) {
          Client c = new Client();
     }
}