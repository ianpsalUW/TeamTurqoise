package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    String myItem;
    String myChange;
    String myDate;
    String myTime;

    public Log(String item, String change) {
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/YY");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm a");
        myChange = change;
        myItem = item;
        myDate = dateForm.format(thisDate);
        myTime = timeForm.format(thisDate);

    }
    public String getDate() {
        return myDate;
    }

    public String getItem() {
        return myItem;
    }

    public String getChange() {
        return myChange;
    }

    public String getTime() {
        return myTime;
    }

}

