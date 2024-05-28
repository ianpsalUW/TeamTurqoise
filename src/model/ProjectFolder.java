package model;

import java.util.List;

public class ProjectFolder {

    List<Project> myProjectList;

    String myProjectFolderName = "Default name";

    public ProjectFolder(List<Project> projects) {
        myProjectList = projects;
    }

    public ProjectFolder(String theProjectFolderName, List<Project> projects) {
        myProjectFolderName = theProjectFolderName;
        myProjectList = projects;
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
}
