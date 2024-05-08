package main;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class to store users into a database to use later. Currently has nowhere to store the data when the
 * program closes so all data will be lost.
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public class UserDB {

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

}
