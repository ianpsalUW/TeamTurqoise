package model;

import java.util.ArrayList;

public class Changelog {

    public ArrayList<Log> myChangeLog;
    public Changelog() { myChangeLog = new ArrayList<>();  }

    public void budgetEdited() {
        myChangeLog.add(new Log("Budget", "edited"));
    }

    public void purchasedItem(String ItemName) {
        myChangeLog.add(new Log(ItemName, "purchased"));
    }

    public void purchaseEdited(String ItemName) {
        myChangeLog.add(new Log(ItemName, "expenditure edited"));
    }

    public void purchaseRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "expenditure removed"));
    }

    public void documentAdded(String ItemName) {
        myChangeLog.add(new Log(ItemName, "document added"));
    }

    public void documentRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "document removed"));
    }

    public void notesCreated(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes created"));
    }

    public void notesEdited(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes edited"));
    }

    public void notesRemoved(String ItemName) {
        myChangeLog.add(new Log(ItemName, "notes removed"));
    }

    public void projectCreated(String projectName) {
        myChangeLog.add(new Log(projectName, "project created"));
    }

    public ArrayList<Log> getChangeLog() {
        return myChangeLog;
    }




}
