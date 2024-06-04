package model;

import view.DocumentAddedListener;

import java.util.ArrayList;

public class Project {

    ArrayList<Document> myDocumentList = new ArrayList<>();

    String myProjectName = "Default name";

    Budget myBudget;
    Spending mySpending;

    Notes myNotes;

    boolean myPrivacy = false;

    ArrayList<DocumentAddedListener> myDocumentAddedListeners;

    Changelog myChangelog;

    public Project() {

    }

    public Project(String theProjectName, boolean thePrivacy, Budget theBudget) {
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

    public void addDocument(Document theDocument) {
        myDocumentList.add(theDocument);
        notifyDocumentAddedListeners(theDocument);
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

    public void addDocumentAddedListener(DocumentAddedListener listener) {
        myDocumentAddedListeners.add(listener);
    }

    private void notifyDocumentAddedListeners(Document document) {
        for (DocumentAddedListener listener : myDocumentAddedListeners) {
            listener.documentAdded(document);
        }
    }
}
