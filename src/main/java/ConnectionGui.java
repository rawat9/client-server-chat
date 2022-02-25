import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectionGui extends JFrame {

    // User Labels & Server Labels
    JLabel userIdLabel , userNameLabel, personalPortLabel, personalAddressLabel;
    JLabel serverPortLabel, serverIpAddress;

    // User TextFields & Server TextFields
    JTextField userIdTextField, userNameField, personalPortField, personalAddressField;
    JTextField serverPortField, serverIpAddressField;

    // If you wanna Instantiate to see how it looks just run on the instantiate on the main method
    // with an empty constructor
    ConnectionGui(){
        // JFrame instatiation
        this.setTitle("Client Connection");
        // Title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Allowing the user to exit
        this.setSize(500,500);
        // Prevent the frame from being resized
        this.setResizable(false);


        /*         Labels and TextFields               */

        // User ID label and TextField
        userIdLabel = new JLabel("User ID:");

        this.add(userIdLabel);

        userIdTextField = new JTextField();
        this.add(userIdTextField);

        //UserName Label & TextField
        userNameLabel = new JLabel("User Name: ");
        this.add(userNameLabel);

        userNameField = new JTextField();
        this.add(userNameField);

        // Personal Port and TextField
        personalPortLabel = new JLabel("Personal Port");
        this.add(personalPortLabel);

        personalPortField = new JTextField();
        this.add(personalPortField);

        // Personal Address and TextField
        personalAddressLabel = new JLabel("Personal Address:  ");
        this.add(personalAddressLabel);

        personalAddressField = new JTextField();
        this.add(personalAddressField);

        // Server Address and TextField
        serverIpAddress = new JLabel("Server Port: ");
        this.add(serverIpAddress);

        serverIpAddressField = new JTextField();
        this.add(serverIpAddressField);

        //Server Port and TextField
        serverPortLabel = new JLabel("Server Port");
        this.add(serverPortLabel);

        serverPortField = new JTextField();
        this.add(serverPortField);

        this.setLayout(new GridLayout(3,2));
        // To make the GUI visible to the user.
        this.setVisible(true);
    }
}
