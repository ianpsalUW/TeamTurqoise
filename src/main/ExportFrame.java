package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportFrame extends JFrame implements ActionListener {

    private final JTextField myFileLocationField;

    private final JTextField myFileNameField;

    private final JButton myExportButton;

    public final UserDB myUserDateBase;

    public ExportFrame(UserDB theUserDateBase) {
        this.myUserDateBase = theUserDateBase;

        setTitle("Export");
        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        JLabel FileLocationLabel = new JLabel("File Path Location:");
        myFileLocationField = new JTextField();
        JLabel FileNameLabel = new JLabel("File Name:");
        myFileNameField = new JTextField();
        myExportButton = new JButton("Export");

        panel.add(FileLocationLabel);
        panel.add(myFileLocationField);
        panel.add(FileNameLabel);
        panel.add(myFileNameField);
        panel.add(myExportButton);

        myExportButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileLocation = myFileLocationField.getText();
        String fileName = myFileNameField.getText();
        String tempFilePath = fileLocation + "\\" + fileName + ".txt";
        Path p = Paths.get(fileLocation);
        if (e.getSource() == myExportButton) {
            if (Files.exists(p)) {
                myUserDateBase.exportUserDB(tempFilePath);
                JOptionPane.showMessageDialog(this, "Export Successful.");
            }
            if (Files.notExists(p)) {
                JOptionPane.showMessageDialog(this, "This file path does not exist.");
            }
            if (Files.exists(Paths.get(tempFilePath))){
                JOptionPane.showMessageDialog(this, "This file already exists.");
            }
        }


    }
}
