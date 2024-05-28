package main;

import model.*;
import view.AboutFrame;
import view.FolderProjectViewer;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A basic GUI with an About screen and a screen to
 * enter in your first name and email address.
 *
 * @version JDK 21.0
 * @author Jordan Festin, Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class MainFrame {

    private final JFrame frame;
    /**
     * Instance field of the GUI's Login/Register button.
     */
    private JButton loginButton;

    /**
     * Instance field of the GUI's About Page button.
     */
    private JButton aboutButton;

    private JButton chooseProjectButton;
    private final List<ProjectFolder> folders;

    /**
     * Instance field of the user database.
     */
    private final UserDB userDB;

    /**
     * Instance field of the current user in the GUI.
     */
    private static User currentUser;

    /**
     * Instance field of the About object.
     */
    private About about;


    public MainFrame() {
        frame = new JFrame("Project Pro v0.3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.getContentPane().setBackground(new Color(64, 224, 208));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        folders = new ArrayList<>();
        folders.add(new ProjectFolder("Test Folder 1",
                Arrays.asList(new Project("Test Project 1.1", false,
                                new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))),
                        new Project("Test Project 1.2", false,
                                new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))))));
        folders.add(new ProjectFolder("Test Folder 2",
                Arrays.asList(new Project("Project 2.1", false,
                new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))),
                        new Project("Test Project 2.2", false,
                new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))))));

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
    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));

        loginButton = new JButton("Login Account");
        loginButton.setMinimumSize(new Dimension(450, 50));
        loginButton.setMaximumSize(new Dimension(450, 50));

        chooseProjectButton = new JButton("Choose a Project");
        chooseProjectButton.setMinimumSize(new Dimension(450, 50));
        chooseProjectButton.setMaximumSize(new Dimension(450, 50));
        //chooseProjectButton.setEnabled(false);

        aboutButton = new JButton("About");
        aboutButton.setMinimumSize(new Dimension(450, 50));
        aboutButton.setMaximumSize(new Dimension(450, 50));

        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(chooseProjectButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(aboutButton);

        return mainPanel;
    }

    /**
     * Adds actions to the Login/Register and About Page buttons.
     */
    private void addListeners() {
        loginButton.addActionListener(event -> new LogInFrame(userDB));

        chooseProjectButton.addActionListener(event -> new FolderProjectViewer(folders));

        aboutButton.addActionListener(theEvent -> {
            if (currentUser != null) {
                new AboutFrame(currentUser, about);
            }
        });
    }

    /**
     * Changes the current user.
     *
     * @param theUser   the user to be changed
     */
    static void setCurrentUser(final User theUser) {
        currentUser = theUser;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}