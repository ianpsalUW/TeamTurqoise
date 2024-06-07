package main;

import model.*;
import view.AboutFrame;
import view.FolderProjectViewer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main JFrame which displays "login",
 * "open projects", and "myAbout" features.
 *
 * @version JDK 21.0
 * @author Jordan Festin, Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class MainFrame {

    /**
     * Instance field of the GUI's Login button.
     */
    private JButton myLoginButton;

    /**
     * Instance field of the GUI's About Page button.
     */
    private JButton myAboutButton;

    /**
     * Instance field of the GUI's "Choose a Project" button.
     */
    private static JButton myChooseProjectButton;

    /**
     * Instance field of the list of Project Folders.
     */
    private static List<ProjectFolder> myFolders;

    /**
     * Instance field of the user database.
     */
    private final UserDB myUserDB;

    /**
     * Instance field of the current user in the GUI.
     */
    private static User myCurrentUser;

    /**
     * Instance field of the About object.
     */
    private final About myAbout;

    /**
     * Static field for the program directory.
     */
    private static final String myDirectory =
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Project_Pro";


    /**
     * Default constructor which initializes GUI features.
     */
    public MainFrame() {
        myAbout = new About();
        myAbout.add("Jordan Festin - \"zzz\"");
        myAbout.add("Bill Lactaoen - \"suffering\"");
        myAbout.add("Christian Pineda - \"Hungry rn\"");
        myAbout.add("Ian Salsich - \":)\"");

        JFrame myFrame = new JFrame("Project Pro v" + myAbout.getVersion());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(700, 500);
        myFrame.getContentPane().setBackground(new Color(64, 224, 208));
        myFrame.getContentPane().setLayout(new BoxLayout(myFrame.getContentPane(), BoxLayout.Y_AXIS));
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        myFrame.setResizable(false);

        myFolders = readFileToFolders();

        myUserDB = new UserDB(myDirectory + File.separator + "common");

        myFrame.add(createMainPanel());
        addListeners();

        // Run all the writetofile methods upon application close/
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            writeFoldersToFile();
            myUserDB.writeDBToFile();
            for (ProjectFolder folder : myFolders) {
                folder.writeProjects();
                for (Project project : folder.getProjectList()) {
                    project.writeDocuments();
                    project.getSpending().writePurchases();
                    project.getChangelog().writeLog();
                }
            }
        }));
    }

    /**
     * Creates the panel containing the buttons
     * for Login, Project Viewer, and About Page.
     *
     * @return a JPanel with Login/Register, Project Viewer, and About buttons
     */
    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));

        myLoginButton = new JButton("Login Account");
        myLoginButton.setMinimumSize(new Dimension(450, 50));
        myLoginButton.setMaximumSize(new Dimension(450, 50));

        myChooseProjectButton = new JButton("Choose a Project");
        myChooseProjectButton.setMinimumSize(new Dimension(450, 50));
        myChooseProjectButton.setMaximumSize(new Dimension(450, 50));
        myChooseProjectButton.setEnabled(false);

        myAboutButton = new JButton("About");
        myAboutButton.setMinimumSize(new Dimension(450, 50));
        myAboutButton.setMaximumSize(new Dimension(450, 50));

        mainPanel.add(myLoginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(myChooseProjectButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(myAboutButton);

        return mainPanel;
    }

    /**
     * Adds actions to the Login, Choose Project, and About Page buttons.
     */
    private void addListeners() {
        myLoginButton.addActionListener(event -> new LogInFrame(myUserDB));

        myChooseProjectButton.addActionListener(event -> new FolderProjectViewer(myFolders));

        myAboutButton.addActionListener(theEvent -> new AboutFrame(myCurrentUser, myAbout));
    }

    /**
     * Changes the current user.
     *
     * @param theUser   the user to be changed
     */
    static void setCurrentUser(final User theUser) {
        myCurrentUser = theUser;
        myChooseProjectButton.setEnabled(true);
    }

    /**
     * Runs the application.
     *
     * @param theArgs the theArgs
     */
    public static void main(final String[] theArgs) {
        if (makeDirectory()) {
            SwingUtilities.invokeLater(MainFrame::new);
        }
    }

    /**
     * Makes the files for the application to save data locally.
     *
     * @return true if the directory was made successfully or was already made. False otherwise.
     */

    private static boolean makeDirectory() {
        File appDirectory = new File(myDirectory);
        File commonDirectory = new File(myDirectory, "common");
        if (!appDirectory.exists()) {
            if (appDirectory.mkdirs()) {
                if (!commonDirectory.exists()) {
                    if (commonDirectory.mkdirs()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to create common directory in Documents.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create application directory in Documents.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Saves the contents of myFolders into a text file.
     */

    private static void writeFoldersToFile() {
        String directory = myDirectory + File.separator + "common";
        File foldersDir = new File(directory, "folders.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(foldersDir))) {
            for (ProjectFolder folder : myFolders) {
                writer.write(folder.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write myFolders to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads the folders.txt file.
     *
     * @return the List created.
     */

    private static List<ProjectFolder> readFileToFolders() {
        List<ProjectFolder> folderList = new ArrayList<>();
        String directory = myDirectory + File.separator + "common";
        File file = new File(directory, "folders.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                ProjectFolder currentFolder;
                while((line = reader.readLine()) != null) {
                    currentFolder = new ProjectFolder(line);
                        folderList.add(currentFolder);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read myFolders from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return folderList;
    }
}