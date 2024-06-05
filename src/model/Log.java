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
    String myItem;

    /**
     * Instance field that contains the change made to the item.
     */
    String myChange;

    /**
     * Instance field that contains the date item was altered.
     */
    String myDate;

    /**
     * Instance field that contains the time item was altered.
     */
    String myTime;

    /**
     * Default Constructor for the log object, requires name and change of item.
     *
     * @param item item
     * @param change change
     */
    public Log(String item, String change) {
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/YY");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm a");
        myChange = change;
        myItem = item;
        myDate = dateForm.format(thisDate);
        myTime = timeForm.format(thisDate);

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

}

