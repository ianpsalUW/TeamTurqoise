package view;

import model.Budget;
import model.Project;
import model.ProjectFolder;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * FolderProjectViewer displays all Projects and
 * ProjectFolders, allowing the adding of both.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class FolderProjectViewer {

    /**
     * Instance field of the class' main frame.
     */
    private final JFrame myFrame;

    /**
     * Instance field of the class' main panel.
     */
    private final JPanel myMainPanel;

    /**
     * Instance field of the list of ProjectFolder objects.
     */
    private final List<ProjectFolder> myFolders;

    /**
     * Constructor which initializes the main JFrame.
     *
     * @param theFolders the list of ProjectFolders
     */
    public FolderProjectViewer(final List<ProjectFolder> theFolders) {
        myFolders = theFolders;

        myFrame = new JFrame("Choose a Project");
        myFrame.setBackground(new Color(64, 224, 208));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(400,300);

        myMainPanel = new JPanel();
        myMainPanel.setLayout(new BoxLayout(myMainPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(myMainPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myFrame.add(scroll);

        displayFolders();

        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    /**
     * Displays all project folders.
     */
    private void displayFolders() {
        myMainPanel.removeAll();
        for (ProjectFolder folder : myFolders) {
            JButton folderButton = new JButton(folder.getName());
            folderButton.setBackground(new Color(241, 213, 146));
            folderButton.setMinimumSize(new Dimension(350, 40));
            folderButton.setMaximumSize(new Dimension(350, 40));
            folderButton.addActionListener(event -> displayProjects(folder));
            myMainPanel.add(folderButton);
        }

        myMainPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        JButton addFolder = new JButton("Add Folder");
        addFolder.setMinimumSize(new Dimension(150, 35));
        addFolder.setMaximumSize(new Dimension(150, 35));
        addFolder.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter name of new folder:");
            if (input != null) {
                myFolders.add(new ProjectFolder(input, new ArrayList<>()));
            }

            displayFolders();
        });
        myMainPanel.add(addFolder);

        myMainPanel.revalidate();
        myMainPanel.repaint();
    }

    /**
     * Displays all projects of a project theFolder
     *
     * @param theFolder the chosen project theFolder
     */
    private void displayProjects(final ProjectFolder theFolder) {
        myMainPanel.removeAll();
        for (Project project : theFolder.getProjectList()) {
            JButton projectButton = new JButton(project.getName());
            projectButton.setMinimumSize(new Dimension(350, 40));
            projectButton.setMaximumSize(new Dimension(350, 40));
            projectButton.addActionListener(event -> {
                new ProjectFrame(project);
                myFrame.dispose();
            });
            myMainPanel.add(projectButton);
        }

        myMainPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        JButton addProject = new JButton("Add Project");
        addProject.setMinimumSize(new Dimension(150, 35));
        addProject.setMaximumSize(new Dimension(150, 35));
        addProject.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter name of new project:");
            if (input != null) {
                Project newProject = new Project(input, false,
                        new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)), theFolder);
                theFolder.addProject(newProject);
                newProject.getChangelog().projectCreated(input);
            }

            displayProjects(theFolder);
        });
        myMainPanel.add(addProject);

        JButton backButton = new JButton("Back to Folders");
        backButton.setMinimumSize(new Dimension(150, 35));
        backButton.setMaximumSize(new Dimension(150, 35));
        backButton.addActionListener(event -> displayFolders());
        myMainPanel.add(backButton);

        myMainPanel.revalidate();
        myMainPanel.repaint();
    }
}
