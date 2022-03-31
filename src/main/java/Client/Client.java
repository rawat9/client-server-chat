package Client;

import Server.User;

public class Client {
//     private SimpleDateFormat timestamp = new SimpleDateFormat("HH:mm:ss");
     private CommunicationHandler ch;
     private ClientController chatWindow;
     private ConnectionController connectWindow;
     private User user;

     public Client() {
          // Start Connection GUI
          connectWindow = new ConnectionController(this);
          connectWindow.setVisible(true);
     }

     // Establish connection with the server
     public void establishServerConnection(String ipAddress, int port, String id, String username) {
          user = new User(id, username);
          chatWindow = new ClientController(this);

          ch = new CommunicationHandler(ipAddress, port, id, username, this);
          ch.start();
     }

     public void openChatWindow() {
          chatWindow.setVisible(true);
     }

     public void sendMessage(String content, String receiverID) {
          Message message = new Message(user.getID(), content, receiverID);
          ch.sendMessage(message);
     }

     public static void main(String[] args) {
          Client c = new Client();
     }
}