package model;

import view.DocumentAddedListener;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Project contains the documents, budget,
 * and changelog associated with it.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class Project {

    /**
     * The Project's list of documents.
     */
    private final ArrayList<Document> myDocumentList;

    /**
     * The Project's name.
     */
    private String myProjectName = "Default name";

    /**
     * The Project's budget.
     */
    private final Budget myBudget;

    /**
     * The Project's list of expenditures.
     */
    private final Spending mySpending;

    /**
     * The Project's privacy attribute.
     */
    private boolean myPrivacy = false;

    /**
     * The Project's listener for documents being added.
     */
    private final ArrayList<DocumentAddedListener> myDocumentAddedListeners;

    /**
     * The Project's changelog.
     */
    private final Changelog myChangelog;

    /**
     * The Project's folder on which it is located at.
     */
    private final ProjectFolder myFolder;

    /**
     * The Project's directory on which it is located at.
     */
    private final String myDirectory;


    /**
     * Constructor which initializes the Project object
     * given a name, the privacy, a Budget object,
     * and its project folder.
     *
     * @param theProjectName a String
     * @param thePrivacy a Boolean
     * @param theBudget a Budget object
     * @param theProjectFolder a ProjectFolder object
     */
    public Project(final String theProjectName, final boolean thePrivacy, final Budget theBudget,
                   final ProjectFolder theProjectFolder) {
        myFolder = theProjectFolder;
        myDirectory = myFolder.getMyDirectory() + File.separator + theProjectName;
        createDir();
        myDocumentList = readDocuments();
        myProjectName = theProjectName;
        myPrivacy = thePrivacy;
        myBudget = theBudget;
        mySpending = new Spending(this);
        myDocumentAddedListeners = new ArrayList<>();
        myChangelog = new Changelog(this);
    }

    /**
     * Returns the Project's list of documents.
     *
     * @return an ArrayList
     */
    public ArrayList<Document> getDocuments() {
        return myDocumentList;
    }

    /**
     * Adds a document.
     *
     * @param theDocument the Document being added
     * @return a Boolean
     */
    public boolean addDocument(final Document theDocument) {
        if (myDocumentList.contains(theDocument)) {
            return false;
        }
        myDocumentList.add(theDocument);
        notifyDocumentAddedListeners(theDocument);
        return true;
    }

    /**
     * Removes a document.
     *
     * @param theDocument the Document being removed
     * @return the Document being removed
     */
    public Document removeDocument(final Document theDocument) throws Exception {
        if (!myDocumentList.contains(theDocument)) {
            throw new Exception("Document not found.");
        } else {
            int resultIndex = myDocumentList.indexOf(theDocument);
            Document result = myDocumentList.get(resultIndex);
            myDocumentList.remove(theDocument);
            return result;
        }
    }

    /**
     * Returns the Project's budget.
     *
     * @return a Budget object
     */
    public Budget getBudget() {
        return myBudget;
    }

    /**
     * Returns the Project's list of expenditures.
     *
     * @return a Spending object
     */
    public Spending getSpending() {
        return mySpending;
    }

    /**
     * Returns the Project's name.
     *
     * @return a String
     */
    public String getName() {
        return myProjectName;
    }

    /**
     * Returns the Project's changelog.
     *
     * @return a Changelog object
     */
    public Changelog getChangelog() {
        return myChangelog;
    }

    /**
     * Returns the Project's directory.
     *
     * @return a String
     */
    public String getMyDirectory() {return myDirectory;}

    /**
     * Adds a DocumentAddedListener.
     *
     * @param listener the listener
     */
    public void addDocumentAddedListener(final DocumentAddedListener listener) {
        myDocumentAddedListeners.add(listener);
    }

    /**
     * Notifies all DocumentAddedListeners.
     *
     * @param theDocument a Document object.
     */
    private void notifyDocumentAddedListeners(final Document theDocument) {
        for (DocumentAddedListener listener : myDocumentAddedListeners) {
            listener.documentAdded(theDocument);
        }
    }

    /**
     * Writes documents to a file.
     */
    public void writeDocuments() {
        String docDir = myDirectory + File.separator + "documents";
        File filePath = new File(docDir, "documents.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Document document : myDocumentList) {
                writer.write(document.getFileName());
                writer.newLine();
                writer.write(document.getDirectory());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write documents to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads documents from a file.
     *
     * @return an ArrayList of Document objects
     */
    private ArrayList<Document> readDocuments() {
        String docDir = myDirectory + File.separator + "documents";
        File filePath = new File(docDir, "documents.txt");

        ArrayList<Document> result = new ArrayList<>();
        if(filePath.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String name, path;
                while ((path = reader.readLine()) != null && (name = reader.readLine()) != null) {
                    result.add(new Document(name, path));
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read documents from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    /**
     * Creates a directory.
     */
    private void createDir() {
        File directory = new File(myDirectory);
        File docDir = new File(myDirectory, "documents");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                if (!docDir.exists()) {
                    if (!docDir.mkdirs()) {
                        JOptionPane.showMessageDialog(null, "Failed to create common directory in Documents.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create application directory in Documents.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Returns the Project as a String object.
     *
     * @return a String
     */
    @Override
    public String toString(){
        return myProjectName + '\n'
                + myPrivacy + '\n'
                + myBudget + '\n'
                + myFolder;
    }
}
