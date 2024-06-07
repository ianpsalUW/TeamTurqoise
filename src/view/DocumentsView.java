package view;

import model.Document;
import model.Project;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This is the class to view the documents in a project.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */
public class DocumentsView extends JFrame implements DocumentAddedListener{
    private final Project myProject;
    private final JPanel myDocumentsPanel;

    /**
     * THis is the main constructor for a DocumentsView object.
     * @param theProject is the project to display the documents from.
     */
    public DocumentsView(final Project theProject) {
        myProject = theProject;
        myProject.addDocumentAddedListener(this);

        setTitle("Documents View");
        setSize(400, 300);
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
        myAddDocumentButton.addActionListener(e -> openImportFrame());
        mainPanel.add(myAddDocumentButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * This is the method to display the documents.
     */

    private void displayDocuments() {
        ArrayList<Document> documents = myProject.getDocuments();
        for (Document document : documents) {
            JButton documentButton = new JButton(document.getFileName());
            documentButton.addActionListener(e -> {
                DocumentFrame viewer = new DocumentFrame(document.getDirectory());
                viewer.setVisible(true);
            });
            myDocumentsPanel.add(documentButton);
        }
    }

    /**
     * This is the method to open the import frame.
     */

    private void openImportFrame() {
        ImportFrame importFrame = new ImportFrame(myProject);
        importFrame.setVisible(true);
    }

    /**
     * This is the method to display a document.
     * @param theDocument is the document to display.
     */
    private void openDocument(final Document theDocument) {
        DocumentFrame viewer = new DocumentFrame(theDocument.getDirectory());
        viewer.setVisible(true);
    }

    @Override
    public void documentAdded(final Document document) {
        JButton documentButton = new JButton(document.getFileName());
        documentButton.addActionListener(e -> openDocument(document));
        myDocumentsPanel.add(documentButton);
        myProject.getChangelog().documentAdded(document.getFileName());
        revalidate();
        repaint();
    }
}
