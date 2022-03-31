package Client;

import Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;


public class ConnectionController extends JFrame implements ActionListener {

    Container contentPane = getContentPane();
    JLabel idLabel = new JLabel("ID");
    JLabel nameLabel = new JLabel("Name");
    JLabel serverAddressLabel = new JLabel("Address");
    JLabel serverPortLabel = new JLabel("Port");

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField serverAddressField = new JTextField();
    JTextField serverPortField = new JTextField();

    JButton joinButton = new JButton("JOIN");

    private Client client;

    ConnectionController(Client client) {
        this.client = client;
    }

    public void open() {
        setTitle("Connection");
        setVisible(true);
        setBounds(10, 10, 370, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setProperties();
    }

    public void setLayoutManager() {
        contentPane.setLayout(null);
    }

    public void setLocationAndSize() {
        // Labels
        idLabel.setBounds(50, 100, 100, 30);
        nameLabel.setBounds(50, 150, 100, 30);
        serverAddressLabel.setBounds(50, 200, 100, 30);
        serverPortLabel.setBounds(50, 250, 100, 30);

        // TextFields
        idField.setBounds(150, 100, 150, 30);
        nameField.setBounds(150, 150, 150, 30);
        serverAddressField.setBounds(150, 200, 150, 30);
        serverPortField.setBounds(150, 250, 150, 30);

        // Button
        joinButton.setBounds(70, 300, 220, 30);
    }

    /**
     * Add components to the container
     */
    public void addComponentsToContainer() {
        contentPane.add(idLabel);
        contentPane.add(nameLabel);
        contentPane.add(serverAddressLabel);
        contentPane.add(serverPortLabel);

        contentPane.add(idField);
        contentPane.add(nameField);
        contentPane.add(serverAddressField);
        contentPane.add(serverPortField);

        contentPane.add(joinButton);
    }

    public void addActionEvent() {
        joinButton.addActionListener(this);
    }

    /**
     * Set ID, Server IP & Port - read only
     */
    public void setProperties() {
        String id = UUID.randomUUID().toString().substring(0, 4);
        idField.setText(id);
        idField.setBackground(Color.LIGHT_GRAY);
        idField.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == joinButton) {
            String id = idField.getText();
            String username = nameField.getText();
            String address = serverAddressField.getText();
            String port = serverPortField.getText();

            /* TODO: Validations */
            if (port.equalsIgnoreCase("8000") && address.equalsIgnoreCase("127.0.0.1")) {
                client.setID(id);
                client.setUsername(username);
                client.setAddress(address);
                client.setPort(Integer.parseInt(port));
                this.dispose();
                client.establishConnection(address, Integer.parseInt(port));

            } else {
                JOptionPane.showMessageDialog(this, "No");
            }
        }
    }
}