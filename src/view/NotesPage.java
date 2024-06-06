package view;

import model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Allows the user to write notes on a simple notepage.
 * The user can save txt files and open them again later.
 */
public class NotesPage extends JFrame implements ActionListener {

    /**
     * The instance of the Project used to create a NotesPage object.
     */
    Project myProject;
    /**
     * The JFrame on which everything else is built.
     */
    JFrame myNotes;
    /**
     * The JTextArea where the user writes text.
     */
    JTextArea myTextArea;
    /**
     * A scroll pane. Goes vertically and horizontally.
     */
    JScrollPane myScrollPane;
    /**
     * A menu bar which contains a single item, "File".
     */
    JMenuBar myMenuBar;
    /**
     * The only JMenu. The user can save or load txt files.
     */
    JMenu myMenuFile;
    /**
     * The JMenu items, the user can create a new file, open an old file,
     * save, or exit.
     */
    JMenuItem myNew, myOpen, mySave, mySaveAs, myExit;
    /**
     * The NotesFile object that NotesPage interacts with whenever the user
     * selects JMenu items.
     */
    NotesFile myNotesFile = new NotesFile(this);

    /**
     * The constructor that is called when the user clicks on "Notes".
     * @param theProject The instance of the project instantiating a NotesPage.
     */
    public NotesPage(Project theProject) {
        this.myProject = theProject;

        //Window
        myNotes = new JFrame("Notes");
        myNotes.setSize(700, 500);
        myNotes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case "New": myNotesFile.newFile(); break;
            case "Open": myNotesFile.open(); break;
            case "Save": myNotesFile.save(); myProject.getChangelog().notesEdited(myNotesFile.myFileName); break;
            case "SaveAs": myNotesFile.saveAs(); myProject.getChangelog().notesCreated(myNotesFile.myFileName); break;
            case "Exit": myNotesFile.exit(); break;
        }
    }
}
