package model;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectFolder {

    List<Project> myProjectList;

    String myProjectFolderName = "Default name";

    private String myDirectory =
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Project_Pro";

    public ProjectFolder(final String theProjectFolderName) {
        myProjectFolderName = theProjectFolderName;
        myDirectory += File.separator + myProjectFolderName;
        myProjectList = readProjects();
        createDirectory();
    }
    public ProjectFolder(List<Project> projects) {
        myProjectList = projects;
        myDirectory += File.separator + myProjectFolderName;
        createDirectory();
    }

    public ProjectFolder(String theProjectFolderName, List<Project> projects) {
        myProjectFolderName = theProjectFolderName;
        myProjectList = projects;
        myDirectory += File.separator + myProjectFolderName;
        createDirectory();
    }

    public List<Project> getProjectList() {
        return myProjectList;
    }

    public String getName() {
        return myProjectFolderName;
    }

    public void addProject(Project project) {
        myProjectList.add(project);
    }

    public List<Project> getMyProjectList() {
        return myProjectList;
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

    public String getMyDirectory() {
        return myDirectory;
    }

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

    @Override
    public String toString() {
        return myProjectFolderName;
    }
}
