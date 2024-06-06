package main;

import model.*;
import view.AboutFrame;
import view.FolderProjectViewer;
import model.UserDB;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main JFrame which displays "login",
 * "open projects", and "about" features.
 *
 * @version JDK 21.0
 * @author Jordan Festin, Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class MainFrame {

    /**
     * Instance field of the GUI frame.
     */
    private final JFrame frame;

    /**
     * Instance field of the GUI's Login button.
     */
    private JButton loginButton;

    /**
     * Instance field of the GUI's About Page button.
     */
    private JButton aboutButton;

    /**
     * Instance field of the GUI's "Choose a Project" button.
     */
    private static JButton chooseProjectButton;

    /**
     * Instance field of the list of Project Folders.
     */
    private static List<ProjectFolder> folders;

    /**
     * Instance field of the user database.
     */
    private final UserDB userDB;

    /**
     * Instance field of the current user in the GUI.
     */
    private static User currentUser;

    /**
     * Instance field of the About object.
     */
    private final About about;

    /**
     * Static field for the program directory.
     */
    private static final String myDirectory =
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Project_Pro";


    /**
     * Default constructor which initializes GUI features.
     */
    public MainFrame() {
        about = new About();
        about.add("Jordan Festin - \"zzz\"");
        about.add("Bill Lactaoen - \"suffering\"");
        about.add("Christian Pineda - \"Hungry rn\"");
        about.add("Ian Salsich - \":)\"");

        frame = new JFrame("Project Pro v" + about.getVersion());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.getContentPane().setBackground(new Color(64, 224, 208));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        folders = readFileToFolders();

        userDB = new UserDB(myDirectory + File.separator + "common");

        frame.add(createMainPanel());
        addListeners();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            writeFoldersToFile();
            userDB.writeDBToFile();
            for (ProjectFolder folder : folders) {
                folder.writeProjects();
            }
            for (ProjectFolder folder : folders) {
                for (Project project : folder.getProjectList()) {
                    project.writeDocuments();
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

        loginButton = new JButton("Login Account");
        loginButton.setMinimumSize(new Dimension(450, 50));
        loginButton.setMaximumSize(new Dimension(450, 50));

        chooseProjectButton = new JButton("Choose a Project");
        chooseProjectButton.setMinimumSize(new Dimension(450, 50));
        chooseProjectButton.setMaximumSize(new Dimension(450, 50));
        chooseProjectButton.setEnabled(false);

        aboutButton = new JButton("About");
        aboutButton.setMinimumSize(new Dimension(450, 50));
        aboutButton.setMaximumSize(new Dimension(450, 50));

        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(chooseProjectButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 70)));
        mainPanel.add(aboutButton);

        return mainPanel;
    }

    /**
     * Adds actions to the Login, Choose Project, and About Page buttons.
     */
    private void addListeners() {
        loginButton.addActionListener(event -> new LogInFrame(userDB));

        chooseProjectButton.addActionListener(event -> new FolderProjectViewer(folders));

        aboutButton.addActionListener(theEvent -> new AboutFrame(currentUser, about));
    }

    /**
     * Changes the current user.
     *
     * @param theUser   the user to be changed
     */
    static void setCurrentUser(final User theUser) {
        currentUser = theUser;
        chooseProjectButton.setEnabled(true);
    }

    /**
     * Runs the application.
     *
     * @param args the args
     */
    public static void main(String[] args) {
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
            for (ProjectFolder folder : folders) {
                writer.write(folder.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write folders to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static List<ProjectFolder> readFileToFolders() {
        List<ProjectFolder> folderList = new ArrayList<>();
        String directory = myDirectory + File.separator + "common";
        File file = new File(directory, "folders.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                ProjectFolder currentFolder = null;
                while((line = reader.readLine()) != null) {
                    currentFolder = new ProjectFolder(line);
                        folderList.add(currentFolder);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read folders from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return folderList;
    }
}