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

    /**
     * Instance field of the GUI's Login/Register button.
     */
    private static JButton setupButton;

    /**
     * Instance field of the GUI's About Page button.
     */
    private static JButton aboutButton;

    private static JButton exportButton;

    private static JButton importButton;

    /**
     * Instance field of the user database.
     */
    private static UserDB userDB;

    /**
     * Instance field of the current user in the GUI.
     */
    private static User currentUser;

    /**
     * Instance field of the About object.
     */
    private static About about;


    /**
     * Creates the main GUI application and sets up
     * the developers' names in the About object.
     *
     * @param args  the args
     */
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
        about.add("Jordan Festin - \"zzz\"");
        about.add("Bill Lactaoen - \"suffering\"");
        about.add("Christian Pineda - \"Hungry rn\"");
        about.add("Ian Salsich - \":)\"");

        frame.add(createMainPanel());
        addListeners();
    }

    /**
     * Creates the panel containing the buttons
     * for Login/Register and About Page.
     *
     * @return a JPanel with Login/Register and About buttons
     */
    public static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        setupButton = new JButton("Login Account");
        setupButton.setPreferredSize(new Dimension(120, 50));
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(120, 50));
        exportButton = new JButton("Export");
        exportButton.setPreferredSize(new Dimension(120, 50));
        importButton = new JButton("Import");
        importButton.setPreferredSize(new Dimension(120, 50));

        mainPanel.add(setupButton);
        mainPanel.add(aboutButton);
        mainPanel.add(exportButton);
        mainPanel.add(importButton);

        return mainPanel;
    }

    /**
     * Adds actions to the Login/Register and About Page buttons.
     */
    private static void addListeners() {
        setupButton.addActionListener(theEvent -> new LogInFrame(userDB));

        aboutButton.addActionListener(theEvent -> {
            if (currentUser != null) {
                new AboutFrame(currentUser, about);
            }
        });

        exportButton.addActionListener(theEvent -> new ExportFrame(userDB));

        importButton.addActionListener(theEvent -> new ImportFrame());
    }

    /**
     * Changes the current user.
     *
     * @param theUser   the user to be changed
     */
    static void setCurrentUser(final User theUser) {
        currentUser = theUser;
    }
}