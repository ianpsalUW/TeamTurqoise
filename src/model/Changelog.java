package model;

import java.util.ArrayList;

/**
 * Class that contains an array list of log objects.
 * Keeping track of all changes made in Projects.
 *
 * @version JDK 21.0
 * @author Jordan Festin
 */
public class Changelog {

    /**
     * Instance field for the array list of log objects.
     */
    public ArrayList<Log> myChangeLog;

    /**
     * Default constructor, initializes array list.
     */
    public Changelog() { myChangeLog = new ArrayList<>();  }

    /**
     * Adds a budget edited log entry to the changelog.
     */
    public void budgetEdited() {
        myChangeLog.add(new Log("Budget", "edited"));
    }

    /**
     * Adds a purchased item log entry to the changelog.
     *
     * @param ItemName ItemName
     */
    public void purchasedItem(String ItemName) {
        myChangeLog.add(new Log(ItemName, "purchased"));
    }

    /**
     * Adds a purchase edited log entry to the changelog.
     * @param ItemName ItemName
     */
    public void purchaseEdited(String ItemName) {
        myChangeLog.add(new Log(ItemName, "expenditure edited"));
    }

    /**
     * Adds a purchase removed log entry to the changelog.
     * @param ItemName ItemName
     */
    public void purchaseRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "expenditure removed"));
    }

    /**
     * Adds a document added log entry to the changelog.
     * @param ItemName ItemName
     */
    public void documentAdded(String ItemName) {
        myChangeLog.add(new Log(ItemName, "document added"));
    }

    /**
     * Adds a document removed log entry to the changelog.
     * @param ItemName ItemName
     */
    public void documentRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "document removed"));
    }

    /**
     * Adds a notes created log entry to the changelog.
     * @param ItemName ItemName
     */
    public void notesCreated(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes created"));
    }

    /**
     * Adds a notes edited log entry to the changelog.
     * @param ItemName ItemName
     */
    public void notesEdited(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes edited"));
    }

    /**
     * Adds a notes removed log entry to the changelog.
     * @param ItemName ItemName
     */
    public void notesRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes removed"));
    }

    /**
     * Adds a project created log entry to the changelog.
     * @param projectName projectName
     */
    public void projectCreated(String projectName) {
        myChangeLog.add(new Log(projectName, "project created"));
    }

    /**
     * retrieves the ArrayList of log entries.
     * @return ArrayList</Log>
     */
    public ArrayList<Log> getChangeLog() {
        return myChangeLog;
    }




}
