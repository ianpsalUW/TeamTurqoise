package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class to create and display a log in window.
 * It takes a username and email to either log in or create a new user.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class LogInFrame extends JFrame implements ActionListener {

    /**
    This is the field for the username text field.
     */

    private final JTextField myUsernameField;

    /**
     * This is the field for the email text field.
     */

    private final JTextField myEmailField;

    /**
     * This is the field for the log in button.
     */

    private final JButton myLoginButton;

    /**
     * This is the field for the register account button.
     */

    private final JButton myRegisterButton;

    /**
     * This is the log in for the user database being used.
     */

    private final UserDB myUserDatabase;

    /**
     * This is the main constructor for the log in frame. It creates and displays the window.
     * @param theUserDatabase is the user database that the program is running.
     */

    public LogInFrame(UserDB theUserDatabase) {
        this.myUserDatabase = theUserDatabase;

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        myUsernameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        myEmailField = new JTextField();
        myLoginButton = new JButton("Login");
        myRegisterButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(myUsernameField);
        panel.add(emailLabel);
        panel.add(myEmailField);
        panel.add(myLoginButton);
        panel.add(myRegisterButton);

        myLoginButton.addActionListener(this);
        myRegisterButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myLoginButton) {
            String username = myUsernameField.getText();
            String email = myEmailField.getText();

            User user = myUserDatabase.getUserByName(username);
            if (user != null && user.logIn(username, email)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                Main.setCurrentUser(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Email!");
            }
        } else if (e.getSource() == myRegisterButton) {
            String username = myUsernameField.getText();
            String email = myEmailField.getText();


            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid Email Format");
                return;
            }

            if (myUserDatabase.getUserByName(username) != null && myUserDatabase.getUserByEmail(email) != null) {
                JOptionPane.showMessageDialog(this, "User already exists");
                return;
            }

            User newUser = new User(username, email);
            myUserDatabase.addUser(newUser);
            JOptionPane.showMessageDialog(this, "User Registered Successfully");
        }
    }

    /**
     * This is a method to ensure that the email is of the email address pattern.
     * @param email is the email to check.
     * @return true if it follows the pattern, false otherwise.
     */

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}
