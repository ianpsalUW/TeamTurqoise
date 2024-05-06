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
    private static User currentUser;
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

        about = new About();
        about.add("Christian Pineda - \"Tired rn\"");
        about.add("Ian Salsich - \":)\"" +
                "Bill Lactaoen - \"suffering\"");
        about.add("Jordan Festin - \"zzz\"");

        frame.add(createMainPanel());
        addListeners();
    }

    public static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        setupButton = new JButton("Login Account");
        setupButton.setPreferredSize(new Dimension(120, 50));
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(120, 50));

        mainPanel.add(setupButton);
        mainPanel.add(aboutButton);

        return mainPanel;
    }

    private static void addListeners() {
        setupButton.addActionListener(theEvent -> new LogInFrame(userDB));

        aboutButton.addActionListener(theEvent -> new AboutFrame(currentUser, about));
    }

    static void setCurrentUser(final User theUser) {
        currentUser = theUser;
    }
}