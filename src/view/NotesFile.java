package view;

import java.awt.*;

public class NotesFile {

    NotesPage myNotesPage;

    public NotesFile(NotesPage theNotesPage) {
        this.myNotesPage = theNotesPage;
    }

    public void newFile() {
        myNotesPage.myTextArea.setText("");
        myNotesPage.myNotes.setTitle("New");
    }

    public void open() {
        FileDialog fd = new FileDialog(myNotesPage.myNotes, "Open", FileDialog.LOAD);
        fd.setVisible(true);
    }

}
