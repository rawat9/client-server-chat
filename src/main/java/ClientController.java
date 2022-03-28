import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Arrays;


public class ClientController extends JFrame  {
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

    public ClientController() {
        setLayout(null);

        // Topbar
        topbar = new JPanel();
        topbar.setLayout(null);

        // Avatar
        JLabel avatar = new JLabel(new ImageIcon("src/main/java/assets/avatar.jpeg"));
        avatar.setBounds(10, 10, 30, 30);

        // Name
        JLabel name = new JLabel("Anurag");
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
        toWho.setSelectedItem("Everyone");
        toWho.addItem("Everyone");
        toWho.setBounds(60, 5, 120, 20);

        // Message Field
        message = new JTextField(30); // accepts upto 10 characters
        message.setBounds(5, 35, 280, 30);

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

        // Test Purposes
        DefaultListModel<String> list = new DefaultListModel<>();
        list.addElement("Pedro (Coordinator)");
        list.addElement("Oskar");
        list.addElement("Peter");
        list.addElement("James");

        members = new JList<>(list);
        members.setBounds(365, 0, 135, 380);

        add(topbar);
        add(scrollBar);
        add(panel);

        // Source object adds listener
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackground(Color.WHITE);
        setLocation(300,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void toggleUsers() {
        usersPanel().setVisible(true);
    }

    public JFrame usersPanel() {
        String[][] users = new String[][] {
                {"20ab", "Pedro", "127.0.0.1", "status: active", "isCoordinator: true"},
                {"y89c", "Anurag", "126.233.99.10", "status: active", "isCoordinator: false"},
                {"8dp4", "James", "126.101.99.10", "status: active", "isCoordinator: false"},
                {"02yq", "Oskar", "126.89.99.9", "status: inactive", "isCoordinator: false" },
                {"3700", "Jazz", "101.190.99.10", "status: inactive", "isCoordinator: false"}
        };

        JFrame frame = new JFrame();
        DefaultMutableTreeNode user = new DefaultMutableTreeNode("Users");

        for (String[] u : users) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(Arrays.stream(u).toList().get(1));
            for (String g: u) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(g);
                root.add(child);
            }
            user.add(root);
        }

        JTree tree = new JTree(user);
        JScrollPane scrollPane = new JScrollPane(tree);
        frame.add(scrollPane);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        return frame;
    }

    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
//        new ConnectionController();
        SwingUtilities.invokeLater(() -> new ClientController().setVisible(true));
    }
}
