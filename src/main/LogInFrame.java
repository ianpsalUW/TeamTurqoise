package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInFrame extends JFrame implements ActionListener {
    private final JTextField myUsernameField;
    private final JTextField myEmailField;
    private final JButton myLoginButton;
    private final JButton myRegisterButton;
    private final UserDB myUserDatabase;

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

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}
