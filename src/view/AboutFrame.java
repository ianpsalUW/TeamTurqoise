package view;

import model.User;
import model.About;

import javax.swing.*;
import java.awt.*;

/**
 * A class which displays an About page containing the current
 * user and the app developers.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class AboutFrame extends JFrame {

    /**
     * Instance field containing the information of the current user.
     */
    private final User myUser;
    /**
     * Instance field containing an About object.
     */
    private final About myAbout;


    /**
     * Constructor which initializes a JFrame,
     * the User object, and the About object.
     *
     * @param theUser   the current user
     * @param theAbt    an About object
     */
    public AboutFrame(User theUser, About theAbt) {
        super("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(300, 220));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        myUser = theUser;
        myAbout = theAbt;
        add(presentMessage());
    }

    /**
     * Displays a JTextPane which shows the name and email of the current
     * user, the list of developers, and the current version number.
     *
     * @return a JTextPane to act as the About page
     */
    private JTextPane presentMessage() {
        JTextPane text = new JTextPane();
        text.setEditable(false);
        text.setBackground(new Color(229, 217, 191));

        String msg = String.format("""
                This App Is Registered To:
                %s (%s)

                This App Provided By:
                %s
                Version: %.1f""", myUser.getName(), myUser.getEmail(),
                myAbout.devToString(), myAbout.getVersion());
        text.setText(msg);

        return text;
    }
}
