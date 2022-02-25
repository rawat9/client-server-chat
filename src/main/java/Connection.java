import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Thread {

    private final String socket;
    private final Integer port;
    // Socket
    private PrintWriter pw;
    private Socket ss;

    public Connection(String socket_details, Integer user_port){
        this.socket = socket_details;
        this.port = user_port;
    }

    // Contact the server
    public void contactServer(){
        try{
            ss = new Socket(this.socket,this.port);
            OutputStream os = ss.getOutputStream();
            pw = new PrintWriter(os, true);
            pw.println("server hi");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    // - I need to create the methods to i am able to contact
    //   the server

}
