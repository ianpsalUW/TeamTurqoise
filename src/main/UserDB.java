package main;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private List<User> myUsers;

    public UserDB() {
        myUsers = new ArrayList<>();
    }

    public void addUser(User theUser) {
        myUsers.add(theUser);
    }

    public User getUserByName(String theName) {
        for (User user : myUsers) {
            if (user.getName().equals(theName)) {
                return user;
            }
        }
        return null; // User not found
    }

    public User getUserByEmail(String theEmail) {
        for (User user : myUsers) {
            if (user.getEmail().equals(theEmail)) {
                return user;
            }
        }
        return null; // User not found
    }

}
