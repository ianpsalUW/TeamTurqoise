package main;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.function.Consumer;

public class ImportFrame extends JFrame {

    private JTextField filePathTextField;
    private JTextField destinationTextField;
    private JTextField fileNameTextField;
    private JButton browseButton;
    private JButton saveButton;

    public ImportFrame() {
        setTitle("File Import");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        filePathTextField = new JTextField();
        filePathTextField.setEditable(false);

        destinationTextField = new JTextField();
        destinationTextField.setEditable(true);

        fileNameTextField = new JTextField();

        browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sourceFilePath = filePathTextField.getText();
                String destinationFolderPath = destinationTextField.getText();
                String newFileName = fileNameTextField.getText();

                if (!sourceFilePath.isEmpty() && !destinationFolderPath.isEmpty() && !newFileName.isEmpty()) {
                    try {
                        File sourceFile = new File(sourceFilePath);
                        File destinationFolder = new File(destinationFolderPath);
                        String destinationFilePath = destinationFolderPath + File.separator + newFileName;

                        // Copy the file
                        try (InputStream inputStream = new FileInputStream(sourceFile);
                             OutputStream outputStream = new FileOutputStream(destinationFilePath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                            JOptionPane.showMessageDialog(null, "File copied successfully.");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error copying file: " + ex.getMessage());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(new JLabel("Source File: "), BorderLayout.WEST);
        panel1.add(filePathTextField, BorderLayout.CENTER);
        panel1.add(browseButton, BorderLayout.EAST);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(new JLabel("Destination Folder: "), BorderLayout.WEST);
        panel2.add(destinationTextField, BorderLayout.CENTER);

        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(new JLabel("New File Name: "), BorderLayout.WEST);
        panel3.add(fileNameTextField, BorderLayout.CENTER);

        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel4.add(saveButton);

        getContentPane().add(panel1);
        getContentPane().add(panel2);
        getContentPane().add(panel3);
        getContentPane().add(panel4);

        // Allow file drag and drop
        new FileDrop(filePathTextField, new FileDrop.Listener(files -> {
            for (File file : files) {
                filePathTextField.setText(file.getAbsolutePath());
            }
        }));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImportFrame();
            }
        });
    }
}

class FileDrop {
    public static class Listener implements DropTargetListener, Consumer<File[]> {
        private Consumer<File[]> consumer;

        public Listener(Consumer<File[]> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = dtde.getTransferable();
            try {
                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    List<File> fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    consumer.accept(fileList.toArray(new File[fileList.size()]));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void accept(File[] files) {

        }
    }

    public FileDrop(JComponent component, Consumer<File[]> consumer) {
        new DropTarget(component, new Listener(consumer));
    }
}