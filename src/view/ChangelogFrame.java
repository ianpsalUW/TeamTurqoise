package view;

import model.Log;
import model.Project;

import javax.swing.*;
import java.awt.*;

/**
 * ChangelogFrame displays all
 * changes made on a project.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class ChangelogFrame extends JFrame {

    /**
     * Instance field of the current project being examined.
     */
    private final Project project;

    /**
     * Instance field of the changelog's main panel.
     */
    private final JPanel mainPanel;


    /**
     * Constructor which initializes the main JFrame.
     *
     * @param project the project being examined
     */
    public ChangelogFrame(Project project) {
        this.project = project;

        setTitle(project.getName() + " - Changelog");
        setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(mainPanel);
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
     * Displays all changes made on the project.
     */
    private void displayLogs() {
        mainPanel.removeAll();
        if (project.getChangelog().getChangeLog() != null) {
            for (Log log : project.getChangelog().getChangeLog()) {
                JLabel label = new JLabel(" - " + log.getItem() + " " + log.getChange()
                        + " (" + log.getDate() + ", " + log.getTime() + ")");
                label.setFont(new Font("Dialog", Font.BOLD, 14));
                mainPanel.add(label);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
