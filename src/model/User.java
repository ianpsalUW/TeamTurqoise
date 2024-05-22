package model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

/**
 * This is the class to designate a user. It stores a username and email address.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * This is the field for the name of the user.
     */

    private String myName;

    /**
     * This is the field for the email of the user.
     */

    private String myEmail;

    /**
     * This is the main constructor for a user object.
     * @param theName the username.
     * @param theEmail the user's email address.
     */

    public User (final String theName, final String theEmail) {
        setName(theName);
        setEmail(theEmail);
    }

    /**
     * This is a method to ensure that the provided username and email address match the user's.
     * @param theName the name to check.
     * @param theEmail the email to check.
     * @return true if both match. false otherwise.
     */

    public boolean logIn(final String theName, final String theEmail) {
        return theName.equals(myName) && theEmail.equals(myEmail);
    }

    /**
     * This is the setter method for the username.
     * @param theName the name to set the username to.
     */

    private void setName(final String theName) {
        myName = theName;
    }

    /**
     * This is the setter for the email address.
     * @param theEmail the email address to set.
     */

    private void setEmail(final String theEmail) {
        myEmail = theEmail;
    }

    /**
     * This is the getter method for the username.
     * @return the username.
     */

    public String getName() {
        return myName;
    }

    /**
     * This is the getter method for the email.
     * @return the email.
     */

    public String getEmail() {
        return myEmail;
    }

    @Override
    public String toString() { return myName + " " + myEmail; }

    public void exportUser() {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("C:\\Users\\stflaptop\\Desktop\\360Turqoise\\BasicGUI\\src\\profile.txt", true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(toString());
        } catch (Exception ignored) {}
        finally {
            if (oos != null) {
                try {oos.close();} catch (Exception ignored){}
            }
        }
    }

}
