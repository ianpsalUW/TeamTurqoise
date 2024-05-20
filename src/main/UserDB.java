package main;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.*;


/**
 * This is the class to store users into a database to use later. Currently has nowhere to store the data when the
 * program closes so all data will be lost.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class UserDB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * This is the private field for the stored users.
     */

    private final List<User> myUsers;

    /**
     * This is the main constructor for a UserDB object.
     */

    public UserDB() {
        myUsers = new ArrayList<>();
    }

    /**
     * This is the method to add a user to the DB.
     * @param theUser the user to add.
     */

    public void addUser(User theUser) {
        myUsers.add(theUser);
    }

    /**
     * This is the method to get a user in the DB by the username.
     * @param theName the name to match the user to.
     * @return the user object with the corresponding name or null if not found.
     */

    public User getUserByName(String theName) {
        for (User user : myUsers) {
            if (user.getName().equals(theName)) {
                return user;
            }
        }
        return null; // User not found
    }

    /**
     * This is the method to find a user in the DB by the email.
     * @param theEmail the email to match the user to.
     * @return the user object with the corresponding email or null if not found.
     */
    public User getUserByEmail(String theEmail) {
        for (User user : myUsers) {
            if (user.getEmail().equals(theEmail)) {
                return user;
            }
        }
        return null; // User not found
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (User user : myUsers) {
            sb.append(prefix).append(user.toString());
            prefix = "\n";
        }
        return sb.toString();
    }

    public void exportUserDB() {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("C:\\Users\\stflaptop\\Desktop\\360Turqoise\\BasicGUI\\src\\profile.txt", true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (Exception ignored) {}
        finally {
            if (oos != null) {
                try {oos.close();} catch (Exception ignored){}
            }
        }
    }

    public void exportUserDB(String fileLocation) {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(fileLocation, true);
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
