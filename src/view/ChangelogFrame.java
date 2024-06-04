package view;

import model.Log;
import model.Project;

import javax.swing.*;
import java.awt.*;

public class ChangelogFrame extends JFrame {

    private final Project project;
    private final JPanel mainPanel;

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
