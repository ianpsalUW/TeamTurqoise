package model;

import javax.swing.*;
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

    private String myFilePath = "";

    /**
     * This is the main constructor for a UserDB object.
     */

    public UserDB(String theFilePath) {
        myFilePath += theFilePath + File.separator + "users.txt";
        myUsers = getUsersByFile();
    }

    /**
     * This is the method to add a user to the DB.
     * @param theUser the user to add.
     */

    public void addUser(User theUser) {
        if (getUserByName(theUser.getName()) != null || getUserByEmail(theUser.getEmail()) != null) {
            JOptionPane.showMessageDialog(null, "User already exists.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            myUsers.add(theUser);
        }
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

    /**
     * Returns the UserDB as a String object.
     *
     * @return String
     */
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

    /**
     * Exports the UserDB given a file location.
     *
     * @param fileLocation a String
     */
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

    /**
     * Returns a list of Users from a file.
     *
     * @return a List of User objects.
     */
    private List<User> getUsersByFile() {
        List<User> result = new ArrayList<>();
        File outputFile = new File(myFilePath);
        if (outputFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                String username;
                String email;
                User currentUser = null;
                while((username = reader.readLine()) != null) {
                    if((email = reader.readLine()) != null) {
                        currentUser = new User(username, email);
                        result.add(currentUser);
                    }

                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read folders from file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    /**
     * Writes the database to a file.
     */
    public void writeDBToFile() {
        File outputFile = new File(myFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (User user : myUsers) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write user database to file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
