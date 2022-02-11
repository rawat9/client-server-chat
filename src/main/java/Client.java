/**
 * Client Class
 * Author: Anurag
 */

public class Client {
     private int id;
     private String name;
     private String address;
     private int port;
     boolean active = true;

     public Client(int id, String name, String address, int port) {
          this.id = id;
          this.name = name;
          this.address = address;
          this.port = port;
     }

     public void setCoordinator() {

     }

     public void isCoordinator() {

     }

     public void setActive() {
          active = true;
     }

     public boolean isActive() {
          return active;
     }

     public static void sendMessage() {

     }

     public String getAddress() {
          return address;
     }

     public int getId() {
          return this.id;
     }

     public String getName() {
          return this.name;
     }

     public int getPort() {
          return this.port;
     }
}
