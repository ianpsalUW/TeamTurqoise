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

    // The constructor used for testing. Will not be used in the running application.
    public NotesPage() {
        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myTextArea = new JTextArea();
        myNotes.add(myTextArea);

        myNotes.setVisible(true);
    }

    public NotesPage(Project theProject) {
        this.myProject = theProject;

        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myTextArea = new JTextArea();
        myNotes.add(myTextArea);

        myNotes.setVisible(true);
    }
}
