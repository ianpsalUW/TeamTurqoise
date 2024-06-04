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
    private final JFrame frame;

    /**
     * Instance field of the class' main panel.
     */
    private final JPanel mainPanel;

    /**
     * Instance field of the list of ProjectFolder objects.
     */
    private final List<ProjectFolder> folders;

    /**
     * Constructor which initializes the main JFrame.
     *
     * @param folders the list of ProjectFolders
     */
    public FolderProjectViewer(List<ProjectFolder> folders) {
        this.folders = folders;

        frame = new JFrame("Choose a Project");
        frame.setBackground(new Color(64, 224, 208));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scroll);

        displayFolders();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Displays all project folders.
     */
    private void displayFolders() {
        mainPanel.removeAll();
        for (ProjectFolder folder : folders) {
            JButton folderButton = new JButton(folder.getName());
            folderButton.setBackground(new Color(241, 213, 146));
            folderButton.setMinimumSize(new Dimension(350, 40));
            folderButton.setMaximumSize(new Dimension(350, 40));
            folderButton.addActionListener(event -> displayProjects(folder));
            mainPanel.add(folderButton);
        }

        mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        JButton addFolder = new JButton("Add Folder");
        addFolder.setMinimumSize(new Dimension(150, 35));
        addFolder.setMaximumSize(new Dimension(150, 35));
        addFolder.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter name of new folder:");
            if (input != null) {
                folders.add(new ProjectFolder(input, new ArrayList<>()));
            }

            displayFolders();
        });
        mainPanel.add(addFolder);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Displays all projects of a project folder
     *
     * @param folder the chosen project folder
     */
    private void displayProjects(ProjectFolder folder) {
        mainPanel.removeAll();
        for (Project project : folder.getProjectList()) {
            JButton projectButton = new JButton(project.getName());
            projectButton.setMinimumSize(new Dimension(350, 40));
            projectButton.setMaximumSize(new Dimension(350, 40));
            projectButton.addActionListener(event -> {
                new ProjectFrame(project);
                frame.dispose();
            });
            mainPanel.add(projectButton);
        }

        mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        JButton addProject = new JButton("Add Project");
        addProject.setMinimumSize(new Dimension(150, 35));
        addProject.setMaximumSize(new Dimension(150, 35));
        addProject.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("Enter name of new project:");
            if (input != null) {
                Project newProject = new Project(input, false,
                        new Budget(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)));
                folder.addProject(newProject);
                newProject.getChangelog().projectCreated(input);
            }

            displayProjects(folder);
        });
        mainPanel.add(addProject);

        JButton backButton = new JButton("Back to Folders");
        backButton.setMinimumSize(new Dimension(150, 35));
        backButton.setMaximumSize(new Dimension(150, 35));
        backButton.addActionListener(event -> displayFolders());
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
