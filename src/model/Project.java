package model;

import view.DocumentAddedListener;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Project {

    ArrayList<Document> myDocumentList;

    String myProjectName = "Default name";

    Budget myBudget;
    Spending mySpending;

    Notes myNotes;

    boolean myPrivacy = false;

    ArrayList<DocumentAddedListener> myDocumentAddedListeners;

    Changelog myChangelog;

    ProjectFolder myFolder;

    String myDirectory;


    public Project(String theProjectName, boolean thePrivacy, Budget theBudget, ProjectFolder theProjectFolder) {
        myFolder = theProjectFolder;
        myDirectory = myFolder.getMyDirectory() + File.separator + theProjectName;
        createDir();
        myDocumentList = readDocuments();
        myProjectName = theProjectName;
        myPrivacy = thePrivacy;
        myBudget = theBudget;
        mySpending = new Spending();
        myDocumentAddedListeners = new ArrayList<>();
        myChangelog = new Changelog();
    }

    public ArrayList<Document> getDocuments() {
        return myDocumentList;
    }

    public boolean addDocument(Document theDocument) {
        if (myDocumentList.contains(theDocument)) {
            return false;
        }
        myDocumentList.add(theDocument);
        notifyDocumentAddedListeners(theDocument);
        return true;
    }

    public Document removeDocument(Document theDocument) throws Exception {
        if (!myDocumentList.contains(theDocument)) {
            throw new Exception("Document not found.");
        } else {
            int resultIndex = myDocumentList.indexOf(theDocument);
            Document result = myDocumentList.get(resultIndex);
            myDocumentList.remove(theDocument);
            return result;
        }
    }

    public Budget getBudget() {
        return myBudget;
    }

    public Spending getSpending() {
        return mySpending;
    }

    public String getName() {
        return myProjectName;
    }

    public Changelog getChangelog() {
        return myChangelog;
    }

    public String getMyDirectory() {return myDirectory;}

    public void addDocumentAddedListener(DocumentAddedListener listener) {
        myDocumentAddedListeners.add(listener);
    }

    private void notifyDocumentAddedListeners(Document document) {
        for (DocumentAddedListener listener : myDocumentAddedListeners) {
            listener.documentAdded(document);
        }
    }

    public void writeDocuments() {
        File filePath = new File(myDirectory, "documents.txt");

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

    private ArrayList<Document> readDocuments() {
        File filePath = new File(myDirectory, "documents.txt");
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
    @Override
    public String toString(){
        return myProjectName + '\n'
                + myPrivacy + '\n'
                + myBudget + '\n'
                + myFolder;
    }
}
