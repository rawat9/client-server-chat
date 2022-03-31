package Client;

import Server.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Arrays;


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
    private JList<String> members;
    private JButton leave;
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

        // Avatar
        JLabel avatar = new JLabel(new ImageIcon("src/main/java/assets/avatar.jpeg"));
        avatar.setBounds(10, 10, 30, 30);

        // Name
        JLabel name = new JLabel(this.client.getUsername());
        name.setFont(new Font("Fura Code", Font.BOLD, 16));
        name.setBounds(70, 3, 180, 40);
        topbar.setBackground(Color.WHITE);
        topbar.setBounds(0, 0, 365, 50);

        // Leave Button
        users = new JButton("Users");
        users.setForeground(Color.BLUE);
        users.setBounds(230, 8, 60, 30);
        users.addActionListener(e -> toggleUsers());

        // Leave Button
        leave = new JButton("Leave");
        leave.setForeground(Color.RED);
        leave.setBounds(290, 8, 60, 30);

        topbar.add(avatar);
        topbar.add(name);
        topbar.add(leave);
        topbar.add(users);

        // Allocate the UI components
        server = new JTextArea();
        server.setEditable(false);
        scrollBar = new JScrollPane(server);
        scrollBar.setBounds(2, 52, 360, 300);

        // Label
        JLabel to = new JLabel("To who:");
        to.setBounds(5, 5, 50, 20);

        // Combobox
        toWho = new JComboBox<>();
        toWho.addItem("Everyone");
        toWho.setSelectedItem("Everyone");
        toWho.setBounds(60, 5, 120, 20);
        toWho.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)  {
                receiver = String.valueOf(toWho.getSelectedItem());
            }
        });

        // Message Field
        message = new JTextField(30); // accepts upto 10 characters
        message.setBounds(5, 35, 280, 30);
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    client.sendMessage(message.getText(), receiver);
                }
            }
        });

        // Send Button
        send = new JButton("Send");
        send.setBounds(290, 35, 70, 30);

        // Bottom Panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 354, 365, 75);

        panel.add(message);
        panel.add(send);
        panel.add(to);
        panel.add(toWho);

        add(topbar);
        add(scrollBar);
        add(panel);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackground(Color.WHITE);
        setLocation(300,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

            DefaultMutableTreeNode ipAddress = new DefaultMutableTreeNode("ip address: " + user.getIpAddress());
            root.add(ipAddress);

            if (user.getIsCoordinator()) {
                DefaultMutableTreeNode isCoordinator = new DefaultMutableTreeNode("Coordinator");
                root.add(isCoordinator);
            }

            usersNode.add(root);
        }

        usersTree = new JTree(usersNode);
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
