package Client;

import Server.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.awt.event.*;


public class ClientController extends JFrame {
    private static final int WINDOW_WIDTH = 365;
    private static final int WINDOW_HEIGHT = 460;

    // UI components
    private JTextField message;
    private JPanel panel;
    private JPanel topbar;
    private JComboBox<String> toWho;
    private JButton send;
    private JTextArea server;
    private JScrollPane scrollBar;
    private JButton users;
    private JTree usersTree;
    private Client client;
    private String receiver = "Everyone";

    public ClientController(Client client) {
        this.client = client;
        createComponents();
    }

    public void open() {
        setVisible(true);
    }

    public void createComponents() {
        setLayout(null);

        // Topbar
        topbar = new JPanel();
        topbar.setLayout(null);

        // Name
        JLabel name = new JLabel(this.client.getUsername());
        name.setFont(new Font("Fura Code", Font.BOLD, 16));
        name.setBounds(10, 3, 200, 40);
        topbar.setBackground(Color.WHITE);
        topbar.setBounds(0, 0, 365, 50);

        // Users Button
        users = new JButton("Users");
        users.setForeground(Color.BLUE);
        users.setBounds(290, 8, 60, 30);
        users.addActionListener(e -> toggleUsers());

        topbar.add(name);
        topbar.add(users);

        server = new JTextArea();
        server.setEditable(false);
        scrollBar = new JScrollPane(server);
        scrollBar.setBounds(2, 52, 360, 300);

        // Label
        JLabel to = new JLabel("Direct message to:");
        to.setBounds(8, 5, 150, 20);

        // Combobox
        toWho = new JComboBox(this.client.getActiveUsersList().toArray());
        toWho.addItem("Everyone");
        toWho.setSelectedItem("Everyone");
        toWho.setBounds(230, 5, 120, 20);
        toWho.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)  {
                receiver = String.valueOf(toWho.getSelectedItem());
            }
        });

        // Message Field
        message = new JTextField(30); // accepts upto 10 characters
        message.setBounds(5, 35, 350, 30);
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    client.sendMessage(message.getText(), receiver);
                }
            }
        });

        // Bottom Panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 354, 365, 75);

        panel.add(message);
        panel.add(to);
        panel.add(toWho);

        add(topbar);
        add(scrollBar);
        add(panel);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackground(Color.WHITE);
        setLocation(300,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void toggleUsers() {
        usersPanel().setVisible(true);
    }

    public JFrame usersPanel() {
        updateUsersList(client.getActiveUsersList());

        JFrame frame = new JFrame();

        JScrollPane scrollPane = new JScrollPane(usersTree);
        frame.add(scrollPane);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        return frame;
    }

    public void updateUsersList(ArrayList<User> usersList) {
        if (usersTree == null) {
            usersTree = new JTree();
        } else {
            DefaultTreeModel model = (DefaultTreeModel) usersTree.getModel();
            model.setRoot(null);
        }

        DefaultMutableTreeNode usersNode = new DefaultMutableTreeNode("Users");

        for (User user : usersList) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(user.getUsername());

            DefaultMutableTreeNode userId = new DefaultMutableTreeNode("id: " + user.getID());
            root.add(userId);

            DefaultMutableTreeNode ipAddress = new DefaultMutableTreeNode("ip_address: " + user.getIpAddress());
            root.add(ipAddress);

            System.out.println(user.getUsername() + " : " + user.getIsCoordinator());
            if (user.getIsCoordinator()) {
                DefaultMutableTreeNode isCoordinator = new DefaultMutableTreeNode("Coordinator");
                root.add(isCoordinator);
            }
            usersNode.add(root);
        }

        DefaultTreeModel model = (DefaultTreeModel) usersTree.getModel();
        model.setRoot(usersNode);
    }

    public void showCoordinatorDialog() {
        JOptionPane.showMessageDialog(this, "You are the coordinator");
    }

    public JTextArea getServer() {
        return server;
    }

    public JTextField getMessage() {
        return message;
    }

}
