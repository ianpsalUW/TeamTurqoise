package model;

import java.math.BigDecimal;

public class Purchase {
    private String myName;
    private BigDecimal myPrice;
    private String myDate;

    public Purchase(String theName, BigDecimal thePrice, String theDate) {
        myName = theName;
        myPrice = thePrice;
        myDate = theDate;
    }

    public void setName(String theName) { myName = theName; }
    public void setPrice(BigDecimal thePrice) { myPrice = thePrice; }
    public void setDate(String theDate) {myDate = theDate; }
    public String getName() { return myName; }
    public BigDecimal getPrice() { return myPrice; }
    public String getDate() { return myDate; }
}
