package model;

/**
 * Document stores a file's name and directory path.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class Document {

    /**
     * The Document's directory path.
     */
    String myDirectory;

    /**
     * The Document's file name.
     */
    String myFileName;

    /**
     * Constructor that initializes the directory
     * path and file name given two strings.
     *
     * @param theDirectory a String containing a path
     * @param theFileName a String containing a name
     */
    public Document(final String theDirectory, final String theFileName) {
        myDirectory = theDirectory;
        myFileName = theFileName;
    }

    /**
     * Returns the directory path.
     *
     * @return a String
     */
    public String getDirectory() {
        return myDirectory;
    }

    /**
     * Returns the file name.
     *
     * @return a String
     */
    public String getFileName() {
        return myFileName;
    }
}
