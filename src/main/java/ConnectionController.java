import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class ConnectionController extends JFrame implements ActionListener {

    Container contentPane = getContentPane();
    JLabel idLabel = new JLabel("ID");
    JLabel nameLabel = new JLabel("Name");
    JLabel addressLabel = new JLabel("IP Address");
    JLabel portLabel = new JLabel("Port");
    JLabel serverAddressLabel = new JLabel("Server Address");
    JLabel serverPortLabel = new JLabel("Server Port");

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField addressField = new JTextField();
    JTextField portField = new JTextField();
    JTextField serverAddressField = new JTextField();
    JTextField serverPortField = new JTextField();

    JButton joinButton = new JButton("JOIN");

    ConnectionController() {
        setTitle("Connection");
        setVisible(true);
        setBounds(10, 10, 370, 500);
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
        addressLabel.setBounds(50, 200, 100, 30);
        portLabel.setBounds(50, 250, 100, 30);
        serverAddressLabel.setBounds(50, 300, 100, 30);
        serverPortLabel.setBounds(50, 350, 100, 30);

        // TextFields
        idField.setBounds(150, 100, 150, 30);
        nameField.setBounds(150, 150, 150, 30);
        addressField.setBounds(150, 200, 150, 30);
        portField.setBounds(150, 250, 150, 30);
        serverAddressField.setBounds(150, 300, 150, 30);
        serverPortField.setBounds(150, 350, 150, 30);

        // Button
        joinButton.setBounds(70, 420, 220, 30);
    }

    /**
     * Add components to the container
     */
    public void addComponentsToContainer() {
        contentPane.add(idLabel);
        contentPane.add(nameLabel);
        contentPane.add(addressLabel);
        contentPane.add(portLabel);
        contentPane.add(serverAddressLabel);
        contentPane.add(serverPortLabel);

        contentPane.add(idField);
        contentPane.add(nameField);
        contentPane.add(addressField);
        contentPane.add(portField);
        contentPane.add(serverAddressField);
        contentPane.add(serverPortField);

        contentPane.add(joinButton);
    }

    public void addActionEvent() {
        joinButton.addActionListener(this);
    }

    public void setProperties() {
        String id = UUID.randomUUID().toString().substring(0, 4);
        idField.setText(id);
        idField.setBackground(Color.LIGHT_GRAY);
        idField.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Coding Part of LOGIN button
        if (e.getSource() == joinButton) {
            String name;
            String address;
            name = nameField.getText();
            address = addressField.getText();

            /* TODO: Validations */
            if (name.equalsIgnoreCase("") && address.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Yes");
            } else {
                JOptionPane.showMessageDialog(this, "No");
            }
        }
    }
}