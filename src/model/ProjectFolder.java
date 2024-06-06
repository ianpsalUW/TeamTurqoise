package model;

import javax.swing.*;
import java.io.File;
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
        File folderDir = new File(myDirectory);
        if (folderDir.exists()) {
            File projects = new File(myDirectory, "projects.txt");

        }
        return result;
    }

    @Override
    public String toString() {
        return myProjectFolderName;
    }
}
