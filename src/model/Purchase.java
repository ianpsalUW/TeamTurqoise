package model;

public class Purchase {
    private String myName;
    private double myPrice;
    private String myDate;

    public Purchase(String theName, double thePrice, String theDate) {
        myName = theName;
        myPrice = thePrice;
        myDate = theDate;
    }

    private void setName(String theName) { myName = theName; }
    private void setPrice(long thePrice) { myPrice = thePrice; }
    private void setDate(String theDate) {myDate = theDate; }
    public String getName() { return myName; }
    public double getPrice() { return myPrice; }
    public String getDate() { return myDate; }
}
