package view;

import model.Project;

import javax.swing.*;
import java.awt.*;

/**
 * ProjectFrame displays the documents, budget,
 * notes, and changelog of a given myProject.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class ProjectFrame extends JFrame {

    /**
     * Instance field of the current project being examined.
     */
    private final Project myProject;

    /**
     * Instance field of the GUI's documents button.
     */
    private final JButton documentButton;

    /**
     * Instance field of the GUI's budget button.
     */
    private final JButton myBudgetButton;

    /**
     * Instance field of the GUI's notes button.
     */
    private final JButton myNotesButton;

    /**
     * Instance field of the GUI's changelog button.
     */
    private final JButton myChangelogButton;

    /**
     * Instance field of the back button.
     */
    private final JButton myBackButton;


    /**
     * Constructor which initializes the main GUI.
     *
     * @param theProject the theProject being examined
     */
    public ProjectFrame(final Project theProject) {
        myProject = theProject;

        setTitle(theProject.getName());
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

        myBudgetButton = new JButton("Budget");
        myBudgetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myBudgetButton.setMinimumSize(new Dimension(450, 50));
        myBudgetButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(myBudgetButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        myNotesButton = new JButton("Notes");
        myNotesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myNotesButton.setMinimumSize(new Dimension(450, 50));
        myNotesButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(myNotesButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 40)));

        myChangelogButton = new JButton("Changelog");
        myChangelogButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myChangelogButton.setMinimumSize(new Dimension(450, 50));
        myChangelogButton.setMaximumSize(new Dimension(450, 50));
        mainPanel.add(myChangelogButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));

        myBackButton = new JButton("Back to Title");
        myBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myBackButton.setMinimumSize(new Dimension(150, 30));
        myBackButton.setMaximumSize(new Dimension(150, 30));
        mainPanel.add(myBackButton);

        add(mainPanel);
        addListeners();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Adds action listeners to the GUI's JButtons.
     */
    private void addListeners() {
        myBudgetButton.addActionListener(event -> {
            dispose();
            new BudgetPage(myProject);
        });

        documentButton.addActionListener(event -> new DocumentsView(myProject));

        myNotesButton.addActionListener(event -> new NotesPage(myProject));

        myChangelogButton.addActionListener(event -> new ChangelogFrame(myProject));

        myBackButton.addActionListener(event -> dispose());
    }
}
