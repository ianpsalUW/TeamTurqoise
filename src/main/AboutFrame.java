package main;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

    private final User myUser;
    private final About myAbout;

    public AboutFrame(User theAcct, About theAbt) {
        super("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(300, 220));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        myUser = theAcct;
        myAbout = theAbt;
        add(presentMessage());
    }

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
