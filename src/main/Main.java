package main;

import javax.swing.*;
import java.awt.*;

/**
 * A basic GUI with an About screen and a screen to
 * enter in your first name and email address.
 *
 * @version JDK 21.0
 * @author Jordan Festin, Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class Main {

    private static JButton setupButton;
    private static JButton aboutButton;
    private static UserDB userDB;
    private static About about;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic GUI v0.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.getContentPane().setBackground(new Color(64, 224, 208));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);

        userDB = new UserDB();

        frame.add(createMainPanel());
        addListeners();
    }

    public static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        setupButton = new JButton("Setup Account");
        setupButton.setPreferredSize(new Dimension(120, 50));
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(120, 50));

        mainPanel.add(setupButton);
        mainPanel.add(aboutButton);

        return mainPanel;
    }

    private void addListeners() {
        setupButton.addActionListener(theEvent -> {
            new LogInFrame(userDB);
        });

        aboutButton.addActionListener(theEvent -> {
            new AboutFrame();
        });
    }
}