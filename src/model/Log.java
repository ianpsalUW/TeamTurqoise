package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that creates the Log object.
 * Contains the name of the item,
 * the change made to item,
 * and the date and time the item was altered.
 *
 * @version JDK 21.0
 * @author Jordan Festin
 */
public class Log {

    /**
     * Instance field that contains the name of the item.
     */
    private final String myItem;

    /**
     * Instance field that contains the change made to the item.
     */
    private final String myChange;

    /**
     * Instance field that contains the date item was altered.
     */
    private final String myDate;

    /**
     * Instance field that contains the time item was altered.
     */
    private final String myTime;

    /**
     * Default Constructor for the log object, requires name and theChange of theItem.
     *
     * @param theItem theItem
     * @param theChange theChange
     */
    public Log(final String theItem, final String theChange) {
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm a");
        myChange = theChange;
        myItem = theItem;
        myDate = dateForm.format(thisDate);
        myTime = timeForm.format(thisDate);

    }

    /**
     * Constructor for stored Log objetcs, requires name, change, date, and time.
     *
     * @param theItem is the item
     * @param theChange is the change
     * @param theDate is the date
     * @param theTime is the time
     */

    public Log(final String theItem, final String theChange, final String theDate, final String theTime) {
        myItem = theItem;
        myChange = theChange;
        myDate = theDate;
        myTime = theTime;
    }

    /**
     * retrieves the date the item was altered.
     *
     * @return String
     */
    public String getDate() {
        return myDate;
    }

    /**
     * retrieves the name of the item.
     *
     * @return String
     */
    public String getItem() {
        return myItem;
    }

    /**
     * retrieves the change made to the item.
     *
     * @return String
     */
    public String getChange() {
        return myChange;
    }

    /**
     * retrieves the time the item was altered.
     *
     * @return String
     */
    public String getTime() {
        return myTime;
    }

    /**
     * Returns the log as a String object.
     *
     * @return a String
     */
    @Override
    public String toString() {
        return myItem + "\n"
                + myChange + "\n"
                + myDate + "\n"
                +myTime;
    }

}

