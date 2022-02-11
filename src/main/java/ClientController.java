import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientController extends JFrame {
    private JPanel contentPane;
    private JButton sendButton;
    private JList members;
    private JTextField messageField;
    private JComboBox<String> access;

    public ClientController() {
        setContentPane(contentPane);

        // For Demo reasons
        access.addItem("Everyone");
        access.addItem("Pedro");
        access.addItem("Oskar");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage();
            }
        });
    }

    public static void main(String[] args) {
        ClientController client = new ClientController();
        client.setTitle("Client");
        client.setSize(500, 500);
        client.setDefaultCloseOperation(EXIT_ON_CLOSE);
        client.setVisible(true);
    }
}
