package model;

import java.util.ArrayList;

/**
 * A class which holds the current version
 * number and names of developers.
 *
 * @version JDK 21.0
 * @author Christian Pineda
 */

public class About {

    /**
     * Instance field which contains the current version number.
     */
    private static final double VERSION_NUMBER = 0.5;

    /**
     * Instance field which contains a list to store developer names.
     */
    private final ArrayList<String> myDevelopers;


    /**
     * Default constructor that initializes the About object's ArrayList.
     */
    public About() {
        myDevelopers = new ArrayList<>();
    }

    /**
     * Adds a string to the About object's ArrayList.
     *
     * @param theDev    the String to be added
     */
    public void add(final String theDev) {
        myDevelopers.add(theDev);
    }

    /**
     * Returns the current version number.
     *
     * @return the current version number
     */
    public double getVersion() {
        return VERSION_NUMBER;
    }

    /**
     * Takes every developer in the ArrayList into a singular
     * String separated with newline characters.
     *
     * @return a String containing every String in the ArrayList
     */
    public String devToString() {
        StringBuilder msg = new StringBuilder();
        for (String dev : myDevelopers) {
            msg.append(dev).append("\n");
        }

        return msg.toString();
    }
}
