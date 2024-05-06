package main;

public class User {

    private String myName;

    private String myEmail;

    public User (final String theName, final String theEmail) {
        setName(theName);
        setEmail(theEmail);
    }

    public boolean logIn(final String theName, final String theEmail) {
        return theName.equals(myName) && theEmail.equals(myEmail);
    }

    private void setName(final String theName) {
        myName = theName;
    }

    private void setEmail(final String theEmail) {
        myEmail = theEmail;
    }

    public String getName() {
        return myName;
    }

    public String getEmail() {
        return myEmail;
    }

}
