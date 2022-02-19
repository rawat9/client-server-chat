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

        // Panels to contain the labels and TextFields
        //   For Oskar and Anurag only we can delete this comment after
        //   But Idk how to pull this off so i tested as much as i could this Friday
        //   ________________________
        //  | Panell 1  | PanelR 2  |
        //  |___________|___________|
        //  |           |           |
        //  | MPanelL 3 | MPanelR 4 |
        //  |___________|___________|
        //  |      Submit Button    |
        //  |_______________________|

        JPanel topPanelLeft = new JPanel();
        topPanelLeft.setBackground(Color.GRAY);
        topPanelLeft.setBounds(0,0,250,100);
        topPanelLeft.setLayout(null);
        this.add(topPanelLeft);

        JPanel topPanelRight = new JPanel();
        topPanelRight.setBackground(Color.pink);
        topPanelRight.setBounds(250,0,250,100);
        topPanelRight.setLayout(null);
        this.add(topPanelRight);

        JPanel middlePanelLeft = new JPanel();
        middlePanelLeft.setBackground(Color.ORANGE);
        middlePanelLeft.setBounds(0,150,250,100);
        middlePanelLeft.setLayout(null);
        this.add(middlePanelLeft);

        JPanel middlePanelRight = new JPanel();
        middlePanelRight.setBounds(250,150,250,100);
        middlePanelRight.setBackground(Color.yellow);
        middlePanelRight.setLayout(null);
        this.add(middlePanelRight);

        JPanel bottomPanelLeft = new JPanel();
        bottomPanelLeft.setBounds(0,300,250,100);
        bottomPanelLeft.setBackground(Color.CYAN);
        bottomPanelLeft.setLayout(null);
        this.add(bottomPanelLeft);

        JPanel bottomPanelRight = new JPanel();
        bottomPanelRight.setBounds(250,300,250,100);
        bottomPanelRight.setBackground(Color.blue);
        bottomPanelRight.setLayout(null);
        this.add(bottomPanelRight);

        JPanel buttonPanelMiddle = new JPanel();
        buttonPanelMiddle.setBounds(0,400,500,100);
        buttonPanelMiddle.setLayout(null);
        this.add(buttonPanelMiddle);



        /*         Labels and TextFields               */

        // User ID label and TextField
        userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(40,5,100,30);
        topPanelLeft.add(userIdLabel);

        userIdTextField = new JTextField();
        userIdTextField.setBounds(40,40,150,40);
        topPanelLeft.add(userIdTextField);

        //UserName Label & TextField
        userNameLabel = new JLabel("User Name: ");
        userNameLabel.setBounds(0,5,100,30);
        topPanelRight.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setBounds(0,40,150,40);
        topPanelRight.add(userNameField);

        // Personal Port and TextField
        personalPortLabel = new JLabel("Personal Port");
        personalPortLabel.setBounds(40,5,100,30);
        middlePanelLeft.add(personalPortLabel);

        personalPortField = new JTextField();
        personalPortField.setBounds(40,40,150,40);
        middlePanelLeft.add(personalPortField);

        // Personal Address and TextField
        personalAddressLabel = new JLabel("Personal Address:  ");
        personalAddressLabel.setBounds(0,5,250,30);
        middlePanelRight.add(personalAddressLabel);

        personalAddressField = new JTextField();
        personalAddressField.setBounds(0,40,150,40);
        middlePanelRight.add(personalAddressField);

        // Server Address and TextField
        serverIpAddress = new JLabel("Server Port: ");
        serverIpAddress.setBounds(40,5,250,30);
        bottomPanelLeft.add(serverIpAddress);

        serverIpAddressField = new JTextField();
        serverIpAddressField.setBounds(40,40,150,40);
        bottomPanelLeft.add(serverIpAddressField);

        //Server Port and TextField
        serverPortLabel = new JLabel("Server Port");
        serverPortLabel.setBounds(0,5,250,30);
        bottomPanelRight.add(serverPortLabel);

        serverPortField = new JTextField();
        serverPortField.setBounds(0,40,150,40);
        bottomPanelRight.add(serverPortField);

        // To make the GUI visible to the user.
        this.setVisible(true);
    }

}
