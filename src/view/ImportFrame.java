package view;

import model.ImportFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ImportFrame extends JFrame {

    private final JTextField myFilePathTextField;
    private final JTextField myDestinationTextField;

    public ImportFrame() {
        setTitle("File Import");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        myFilePathTextField = new JTextField();
        myFilePathTextField.setEditable(false);

        myDestinationTextField = new JTextField();
        myDestinationTextField.setEditable(true);

        JButton myBrowseButton = getjButton();

        JButton mySaveButton = new JButton("Save");
        mySaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sourceFilePath = myFilePathTextField.getText();
                String destinationFolderPath = myDestinationTextField.getText();

                if (!sourceFilePath.isEmpty() && !destinationFolderPath.isEmpty()) {
                    try {
                        ImportFile.copyFile(sourceFilePath, destinationFolderPath);
                        JOptionPane.showMessageDialog(null, "File copied successfully.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error copying file: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
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

    private JButton getjButton() {
        JButton myBrowseButton = new JButton("Browse");
        myBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    myFilePathTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        return myBrowseButton;
    }
}
