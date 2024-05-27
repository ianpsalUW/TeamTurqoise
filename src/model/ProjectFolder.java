package model;

import java.util.ArrayList;

public class ProjectFolder {

    ArrayList<Project> myProjectList = new ArrayList<>();

    String myProjectFolderName = "Default name";

    public ProjectFolder() {

    }

    public ProjectFolder(String theProjectFolderName) {
        myProjectFolderName = theProjectFolderName;
    }

    public ArrayList<Project> getProjectList() {
        return myProjectList;
    }
}
