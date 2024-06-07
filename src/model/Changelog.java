package model;

import javax.swing.*;
import java.io.*;
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
    private final ArrayList<Log> myChangeLog;

    /**
     * Instance field for the file path
     */
    private final String myPath;

    /**
     * Default constructor, initializes array list.
     */
    public Changelog(final Project theProject) {
        myPath = theProject.getMyDirectory() + File.separator + "changelog";
        makeDir();
        myChangeLog = readLog();
    }

    /**
     * Adds a budget edited log entry to the changelog.
     */
    public void budgetEdited() {
        myChangeLog.add(new Log("Budget", "edited"));
    }

    /**
     * Adds a purchased item log entry to the changelog.
     *
     * @param theItemName theItemName
     */
    public void purchasedItem(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "purchased"));
    }

    /**
     * Adds a purchase edited log entry to the changelog.
     * @param theItemName theItemName
     */
    public void purchaseEdited(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "expenditure edited"));
    }

    /**
     * Adds a purchase removed log entry to the changelog.
     * @param theItemName theItemName
     */
    public void purchaseRemoved(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "expenditure removed"));
    }

    /**
     * Adds a document added log entry to the changelog.
     * @param theItemName theItemName
     */
    public void documentAdded(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "document added"));
    }

    /**
     * Adds a document removed log entry to the changelog.
     * @param theItemName theItemName
     */
    public void documentRemoved(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "document removed"));
    }

    /**
     * Adds a notes created log entry to the changelog.
     * @param theItemName theItemName
     */
    public void notesCreated(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "notes created"));
    }

    /**
     * Adds a notes edited log entry to the changelog.
     * @param theItemName theItemName
     */
    public void notesEdited(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "notes edited"));
    }

    /**
     * Adds a notes removed log entry to the changelog.
     * @param theItemName theItemName
     */
    public void notesRemoved(final String theItemName) {
        myChangeLog.add(new Log(theItemName, "notes removed"));
    }

    /**
     * Adds a project created log entry to the changelog.
     * @param theProjectName theProjectName
     */
    public void projectCreated(final String theProjectName) {
        myChangeLog.add(new Log(theProjectName, "project created"));
    }

    /**
     * retrieves the ArrayList of log entries.
     * @return ArrayList</Log>
     */
    public ArrayList<Log> getChangeLog() {
        return myChangeLog;
    }


    /**
     * This is the method to make the changelog directory for data permanence.
     */

    private void makeDir() {
        File directory = new File(myPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Failed to create application directory in Documents.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Writes a file called changelog.txt
     */

    public void writeLog() {
        File logPath = new File(myPath, "changelog.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logPath))) {
            for (Log log : myChangeLog) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write changelog to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Reads the changelog.txt file for changelog remembering
     *
     * @return an Arraylist of Log objects from changelog.txt
     */

    private ArrayList<Log> readLog() {
        ArrayList<Log> changeLog = new ArrayList<>();
        File logFile = new File(myPath, "changelog.txt");
        if (logFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String itemName, change, date, time;
                while ((itemName = reader.readLine()) != null) {
                    change = reader.readLine();
                    date = reader.readLine();
                    time = reader.readLine();
                    changeLog.add(new Log(itemName, change, date, time));
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read changelog from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return changeLog;
    }


}
