package view;

import model.Project;

import javax.swing.*;
import java.awt.*;

public class NotesPage extends JFrame {

    // Just for testing
    public static void main(String[] args) {
        new NotesPage();
    }

    Project myProject;
    JFrame myNotes;
    JTextArea myTextArea;
    JMenuBar myMenuBar;
    JMenu myMenuFile;

    // The constructor used for testing. Will not be used in the running application.
    public NotesPage() {
        //Window
        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Text area
        myTextArea = new JTextArea();
        myNotes.add(myTextArea);

        //Menu bar
        myMenuBar = new JMenuBar();
        myNotes.setJMenuBar(myMenuBar);
        myMenuFile = new JMenu("File");
        myMenuBar.add(myMenuFile);

        myNotes.setVisible(true);
    }

    // The constructor used by the application.
    public NotesPage(Project theProject) {
        this.myProject = theProject;

        //Window
        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Text area
        myTextArea = new JTextArea();
        myNotes.add(myTextArea);

        //Menu bar
        myMenuBar = new JMenuBar();
        myNotes.setJMenuBar(myMenuBar);
        myMenuFile = new JMenu("File");
        myMenuBar.add(myMenuFile);

        myNotes.setVisible(true);
    }
}
