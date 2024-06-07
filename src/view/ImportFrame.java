package view;

import model.Document;
import model.ImportFile;
import model.Project;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This is the class to display the file import system.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class ImportFrame extends JFrame {

    /**
     * This is the field for the source file path text field.
     */

    private final JTextField myFilePathTextField;

    /**
     * This is the field for the destination file path text field.
     */

    private final JTextField myDestinationTextField;

    /**
     * This is the field for the project.
     */

    private final Project myProject;

    /**
     * This is the main constructor for the import frame.
     * @param theProject is the project using the ImportFrame.
     */

    public ImportFrame(final Project theProject) {
        myProject = theProject;
        setTitle("File Import");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        myFilePathTextField = new JTextField();
        myFilePathTextField.setEditable(false);

        myDestinationTextField = new JTextField(theProject.getMyDirectory() + File.separator + "documents");
        myDestinationTextField.setEditable(false);

        JButton myBrowseButton = new JButton("Browse");
        myBrowseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                myFilePathTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        JButton mySaveButton = new JButton("Save");
        mySaveButton.addActionListener(e -> {
            String sourceFilePath = myFilePathTextField.getText();
            String destinationFolderPath = myDestinationTextField.getText();
            if (!sourceFilePath.isEmpty() && !destinationFolderPath.isEmpty()) {
                ImportFile.copyFile(sourceFilePath, destinationFolderPath);
                File sfile = new File(sourceFilePath);
                File file = new File(destinationFolderPath + File.separator + sfile.getName());
                Document document = new Document(file.getPath(), file.getName());
                if (myProject.addDocument(document)) {
                    ImportFile.copyFile(sourceFilePath, destinationFolderPath);
                    JOptionPane.showMessageDialog(ImportFrame.this, "File copied successfully.");
                } else {
                    JOptionPane.showMessageDialog(ImportFrame.this, "Document already exists.");
                }
            } else {
                JOptionPane.showMessageDialog(ImportFrame.this, "Please fill in all fields.");
            }
        });

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(new JLabel("Source File: "), BorderLayout.WEST);
        panel1.add(myFilePathTextField, BorderLayout.CENTER);
        panel1.add(myBrowseButton, BorderLayout.EAST);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(new JLabel("Destination Folder: "), BorderLayout.WEST);
        panel2.add(myDestinationTextField, BorderLayout.CENTER);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.add(mySaveButton);

        getContentPane().add(panel1);
        getContentPane().add(panel2);
        getContentPane().add(panel3);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
