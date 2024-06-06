package view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Provides the functionality for the NotesPage menu bar options.
 */
public class NotesFile {

    /**
     * The NotesPage object which is interacting with NotesFile.
     */
    NotesPage myNotesPage;
    /**
     * The name of the file being saved or opened.
     */
    String myFileName;
    /**
     * The address of the file being saved or opened.
     */
    String myFileAddress;

    /**
     * Constructor for the NotesFile.
     * @param theNotesPage The NotesPage object interacting the the NotesFile.
     */
    public NotesFile(NotesPage theNotesPage) {
        this.myNotesPage = theNotesPage;
    }

    /**
     * Creates a new txt file.
     */
    public void newFile() {
        myNotesPage.myTextArea.setText("");
        myNotesPage.myNotes.setTitle("New");
        myFileName = null;
        myFileAddress = null;
    }

    /**
     * Opens a preexisting txt file.
     */
    public void open() {
        FileDialog fd = new FileDialog(myNotesPage.myNotes, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            myFileName = fd.getFile();
            myFileAddress = fd.getDirectory();
            myNotesPage.myNotes.setTitle(myFileName);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(myFileAddress + myFileName));
            myNotesPage.myTextArea.setText("");
            String line = null;

            while((line = br.readLine()) != null) {
                myNotesPage.myTextArea.append(line + "\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("File not opened.");
        }
    }

    /**
     * Saves a txt file.
     */
    public void save() {
        if (myFileName == null) {
            saveAs();
        } else {
            try {
                FileWriter fw = new FileWriter(myFileAddress + myFileName);
                fw.write(myNotesPage.myTextArea.getText());
                myNotesPage.myNotes.setTitle(myFileName);
                fw.close();
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    /**
     * Saves a txt file.
     * This is used when saving a file for the first time.
     */
    public void saveAs() {
        FileDialog fd = new FileDialog(myNotesPage.myNotes, "Save", FileDialog.SAVE);
        fd.setVisible(true);

        if(fd.getFile() != null) {
            myFileName = fd.getFile();
            myFileAddress = fd.getDirectory();
            myNotesPage.myNotes.setTitle(myFileName);
        }

        try {
            FileWriter fw = new FileWriter(myFileAddress + myFileName);
            fw.write(myNotesPage.myTextArea.getText());
            fw.close();
        } catch (Exception e) {
            System.out.println("Error.");
        }
    }

    /**
     * Exits notes.
     */
    public void exit() {
        myNotesPage.dispose();
        new ProjectFrame(myNotesPage.myProject);
    }

}
