import javax.swing.*;
import java.net.Socket;

public class Connection extends Thread {
    // Socket
    Socket socket = null;
    JTextArea display;

    // Construct to be initialized
    public Connection(Socket socket, JTextArea display){
        this.socket = socket;
        this.display = display;
    }

}
