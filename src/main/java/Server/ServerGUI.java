package Server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;

    private JPanel header;
    private JPanel leftPanel;
    private JPanel logsPanel;
    private JTextField ipAddressInput;
    private JButton toggleServerButton;
    private GridBagLayout grid = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public ServerGUI() {
        setLayout(grid);
        setTitle("Server");

        header = new JPanel();
        header.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 8;

        ipAddressInput = new JTextField(30);
        header.add(ipAddressInput, gbc);

        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.gridwidth = 4;

        toggleServerButton = new JButton("Start server");
        header.add(toggleServerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 12;
        gbc.gridheight = 2;

        add(header, gbc);

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Connected users"));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        add(leftPanel, gbc);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setPreferredSize(getSize());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerGUI());
    }
}
