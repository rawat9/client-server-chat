import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread {
     private String name;
     private String address;
     private int port;
     private Socket socket;
     private boolean isConnected;
     private boolean active;
     private String toWho = "Everyone";
     private ObjectInputStream inputStream;
     private ObjectOutputStream outputStream;

     public Client(String name, String address, int port) {
          this.name = name;
          this.address = address;
          this.port = port;
     }

     @Override
     public void run() {
          try {
               contactServer();
               if (active) {
                    // call UserName GUI
               } else {
                    start();
               }
          }
          catch (Exception e) {
               e.printStackTrace();
          }
     }

     public void contactServer() throws IOException {
          try {
               socket = new Socket("127.0.0.1", 2000);
               inputStream = new ObjectInputStream(socket.getInputStream());
               outputStream = new ObjectOutputStream(socket.getOutputStream());
          } catch (UnknownHostException u) {
               u.printStackTrace();
          }
     }

     public void setCoordinator() {

     }

     public void isCoordinator() {

     }

     public void setActive() {
          active = true;
     }

     public void terminate() {
          active = false;
     }

     public boolean isActive() {
          return active;
     }

     public void sendMessage(String message) {
          try {
               outputStream.writeChars(message);
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
          Client c = new Client("anurag", "127.0.0.1", 5000);
     }
}