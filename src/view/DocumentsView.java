package view;

import model.Document;
import model.Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DocumentsView extends JFrame implements DocumentAddedListener{
    private final Project myProject;
    private final JPanel myDocumentsPanel;

    public DocumentsView(Project theProject) {
        myProject = theProject;
        myProject.addDocumentAddedListener(this);

        setTitle("Documents View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create documents panel
        myDocumentsPanel = new JPanel();
        myDocumentsPanel.setLayout(new BoxLayout(myDocumentsPanel, BoxLayout.Y_AXIS));

        // Display existing documents
        displayDocuments();

        // Add documents panel to scroll pane
        JScrollPane scrollPane = new JScrollPane(myDocumentsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add button to add documents
        JButton myAddDocumentButton = new JButton("Add Document");
        myAddDocumentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImportFrame();
            }
        });
        mainPanel.add(myAddDocumentButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void displayDocuments() {
        ArrayList<Document> documents = myProject.getDocuments();
        for (Document document : documents) {
            JButton documentButton = new JButton(document.getFileName());
            documentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DocumentFrame viewer = new DocumentFrame(document.getDirectory());
                    viewer.setVisible(true);
                }
            });
            myDocumentsPanel.add(documentButton);
        }
    }

    private void openImportFrame() {
        ImportFrame importFrame = new ImportFrame(myProject);
        importFrame.setVisible(true);
    }

    private void openDocument(Document document) {
        DocumentFrame viewer = new DocumentFrame(document.getDirectory());
        viewer.setVisible(true);
    }

    @Override
    public void documentAdded(Document document) {
        JButton documentButton = new JButton(document.getFileName());
        documentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDocument(document);
            }
        });
        myDocumentsPanel.add(documentButton);
        revalidate();
        repaint();
    }
}
