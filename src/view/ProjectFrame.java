package view;

import model.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectFrame extends JFrame {

    private final Project project;
    private final JButton documentButton;
    private final JButton budgetButton;
    private final JButton notesButton;
    private final JButton changelogButton;
    private final JButton backButton;

    public ProjectFrame(Project project) {
        this.project = project;

        setTitle(project.getName());
        setSize(700, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(10, 35)));

        documentButton = new JButton("Documents");
        documentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        documentButton.setMinimumSize(new Dimension(450, 50));
        documentButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(documentButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        budgetButton = new JButton("Budget");
        budgetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        budgetButton.setMinimumSize(new Dimension(450, 50));
        budgetButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(budgetButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        notesButton = new JButton("Notes");
        notesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        notesButton.setMinimumSize(new Dimension(450, 50));
        notesButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(notesButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        changelogButton = new JButton("Changelog");
        changelogButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changelogButton.setMinimumSize(new Dimension(450, 50));
        changelogButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(changelogButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));

        backButton = new JButton("Back to Title");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMinimumSize(new Dimension(150, 30));
        backButton.setMaximumSize(new Dimension(150, 30));
        mainPanel.add(backButton);

        add(mainPanel);
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addListeners() {
        budgetButton.addActionListener(event -> {
            dispose();
            new BudgetPage(project);
        });

        documentButton.addActionListener(event -> {
            new DocumentsView(project);
        });

        notesButton.addActionListener(event -> {
            new NotesPage(project);
        });

        changelogButton.addActionListener(event -> {
            new ChangelogFrame(project);
        });

        backButton.addActionListener(event -> dispose());
    }
}
