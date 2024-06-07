package model;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectFolder contains all Projects within a folder.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen, Ian Salsich
 */

public class ProjectFolder {

    /**
     * The ProjectFolder's list of Project objects.
     */
    private final List<Project> myProjectList;

    /**
     * The ProjectFolder's name.
     */
    private String myProjectFolderName = "Default name";

    /**
     * The ProjectFolder's directory.
     */
    private String myDirectory =
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Project_Pro";

    /**
     * Constructor which initializes a ProjectFolder given a name.
     *
     * @param theProjectFolderName a String
     */
    public ProjectFolder(final String theProjectFolderName) {
        myProjectFolderName = theProjectFolderName;
        myDirectory += File.separator + myProjectFolderName;
        myProjectList = readProjects();
        createDirectory();
    }

    /**
     * Constructor which initializes a ProjectFolder given a list of Project objects.
     * @param projects a List of Project objects
     */
    public ProjectFolder(final List<Project> projects) {
        myProjectList = projects;
        myDirectory += File.separator + myProjectFolderName;
        createDirectory();
    }

    /**
     * Constructor which initializes a ProjectFolder given
     * a name and a list of Project objects.
     *
     * @param theProjectFolderName a String
     * @param projects a List of Project objects
     */
    public ProjectFolder(final String theProjectFolderName, final List<Project> projects) {
        myProjectFolderName = theProjectFolderName;
        myProjectList = projects;
        myDirectory += File.separator + myProjectFolderName;
        createDirectory();
    }

    /**
     * Returns the ProjectFolder's list of projects.
     *
     * @return a List of Project objects
     */
    public List<Project> getProjectList() {
        return myProjectList;
    }

    /**
     * Returns the ProjectFolder's name.
     *
     * @return a String
     */
    public String getName() {
        return myProjectFolderName;
    }

    /**
     * Adds a project.
     *
     * @param theProject a Project object
     */
    public void addProject(final Project theProject) {
        myProjectList.add(theProject);
    }

    /**
     * Creates the directory for the project folder.
     */
    private void createDirectory() {
        File folderDir = new File(myDirectory);
        if (!folderDir.exists()) {
            if (!folderDir.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Failed to create project directory: " + myDirectory,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Returns the ProjectFolder's directory.
     *
     * @return a String
     */
    public String getMyDirectory() {
        return myDirectory;
    }

    /**
     * Reads projects from a file.
     *
     * @return a List of Project objects
     */
    private List<Project> readProjects() {
        List<Project> result = new ArrayList<>();
        File projectsFile = new File(myDirectory, "projects.txt");

        if (projectsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(projectsFile))) {
                String projectName;
                while ((projectName = reader.readLine()) != null) {
                    String privacyStr = reader.readLine();
                    String budgetStr = reader.readLine();

                    // Validate read data
                    if (privacyStr == null || budgetStr == null) {
                        JOptionPane.showMessageDialog(null, "Incomplete project data found in file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break; // Stop processing to avoid further errors
                    }

                    boolean privacy;
                    try {
                        privacy = Boolean.parseBoolean(privacyStr);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid privacy format in project file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip this project and continue with the next
                    }

                    BigDecimal budget;
                    try {
                        budget = new BigDecimal(budgetStr);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid budget format in project file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        continue; // Skip this project and continue with the next
                    }

                    Project project = new Project(projectName, privacy, new Budget(budget), this);
                    result.add(project);
                    reader.readLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read projects from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    /**
     * Writes projects onto a file.
     */
    public void writeProjects() {
        File projectsFile = new File(myDirectory, "projects.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectsFile))) {
            for (Project project : myProjectList) {
                writer.write(project.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write projects to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns the ProjectFolder as a String object.
     *
     * @return a String
     */
    @Override
    public String toString() {
        return myProjectFolderName;
    }
}
