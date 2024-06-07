package view;

import model.Log;
import model.Project;

import javax.swing.*;
import java.awt.*;

/**
 * ChangelogFrame displays all
 * changes made on a myProject.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class ChangelogFrame extends JFrame {

    /**
     * Instance field of the current project being examined.
     */
    private final Project myProject;

    /**
     * Instance field of the changelog's main panel.
     */
    private final JPanel myMainPanel;


    /**
     * Constructor which initializes the main JFrame.
     *
     * @param theProject the myProject being examined
     */
    public ChangelogFrame(final Project theProject) {
        myProject = theProject;

        setTitle(theProject.getName() + " - Changelog");
        setSize(400, 300);

        myMainPanel = new JPanel();
        myMainPanel.setLayout(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(myMainPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroll);

        displayLogs();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Displays all changes made on the myProject.
     */
    private void displayLogs() {
        myMainPanel.removeAll();
        if (myProject.getChangelog().getChangeLog() != null) {
            for (Log log : myProject.getChangelog().getChangeLog()) {
                JLabel label = new JLabel(" - " + log.getItem() + " " + log.getChange()
                        + " (" + log.getDate() + ", " + log.getTime() + ")");
                label.setFont(new Font("Dialog", Font.BOLD, 14));
                myMainPanel.add(label);
            }
        }

        myMainPanel.revalidate();
        myMainPanel.repaint();
    }
}
