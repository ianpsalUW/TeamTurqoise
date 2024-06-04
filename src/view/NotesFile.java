package view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class NotesFile {

    NotesPage myNotesPage;
    String myFileName;
    String myFileAddress;

    public NotesFile(NotesPage theNotesPage) {
        this.myNotesPage = theNotesPage;
    }

    public void newFile() {
        myNotesPage.myTextArea.setText("");
        myNotesPage.myNotes.setTitle("New");
        myFileName = null;
        myFileAddress = null;
    }

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

    public void exit() {
        myNotesPage.dispose();
    }

}
