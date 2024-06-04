package view;

import model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotesPage extends JFrame implements ActionListener {

    Project myProject;
    JFrame myNotes;
    JTextArea myTextArea;
    JScrollPane myScrollPane;
    JMenuBar myMenuBar;
    JMenu myMenuFile;
    JMenuItem myNew, myOpen, mySave, mySaveAs, myExit;
    NotesFile myNotesFile = new NotesFile(this);

    // The constructor used by the application
    public NotesPage(Project theProject) {
        this.myProject = theProject;

        //Window
        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Text area
        myTextArea = new JTextArea();
        myNotes.add(myTextArea);

        //Scroll pane
        myScrollPane = new JScrollPane(myTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myScrollPane.setBorder(BorderFactory.createEmptyBorder());
        myNotes.add(myScrollPane);

        //Menu bar
        myMenuBar = new JMenuBar();
        myNotes.setJMenuBar(myMenuBar);
        myMenuFile = new JMenu("File");
        myMenuBar.add(myMenuFile);

        //File menu
        myNew = new JMenuItem("New");
        myNew.addActionListener(this);
        myNew.setActionCommand("New");
        myMenuFile.add(myNew);

        myOpen = new JMenuItem("Open");
        myOpen.addActionListener(this);
        myOpen.setActionCommand("Open");
        myMenuFile.add(myOpen);

        mySave = new JMenuItem("Save");
        mySave.addActionListener(this);
        mySave.setActionCommand("Save");
        myMenuFile.add(mySave);

        mySaveAs = new JMenuItem("Save As");
        mySaveAs.addActionListener(this);
        mySaveAs.setActionCommand("SaveAs");
        myMenuFile.add(mySaveAs);

        myExit = new JMenuItem("Exit");
        myExit.addActionListener(this);
        myExit.setActionCommand("Exit");
        myMenuFile.add(myExit);

        myNotes.setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case "New": myNotesFile.newFile(); break;
            case "Open": myNotesFile.open(); break;
            case "Save": myNotesFile.save(); break;
            case "SaveAs": myNotesFile.saveAs(); break;
            case "Exit": myNotesFile.exit(); break;
        }
    }
}
