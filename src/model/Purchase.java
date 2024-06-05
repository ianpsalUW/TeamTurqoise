package model;

import java.math.BigDecimal;

/**
 * A class that creates the purchase object.
 * Which contains the name, price and date of purchase.
 */

public class Purchase {
    private String myName;
    private BigDecimal myPrice;
    private String myDate;

    /**
     * Default constructor for purchase class, require the name, price and date.
     *
     * @param theName theName
     * @param thePrice thePrice
     * @param theDate theDate
     */
    public Purchase(String theName, BigDecimal thePrice, String theDate) {
        myName = theName;
        myPrice = thePrice;
        myDate = theDate;
    }

    /**
     * Sets the name of the purchase.
     *
     * @param theName theName
     */
    public void setName(String theName) { myName = theName; }

    /**
     * Sets the price of the purchase.
     *
     * @param thePrice thePrice
     */
    public void setPrice(BigDecimal thePrice) { myPrice = thePrice; }

    /**
     * Sets the date of the purchase.
     *
     * @param theDate theDate
     */
    public void setDate(String theDate) {myDate = theDate; }

    /**
     * retrieves the name of the purchase.
     *
     * @return String theDate
     */
    public String getName() { return myName; }

    /**
     * retrieves the price of the purchase.
     *
     * @return String
     */
    public BigDecimal getPrice() { return myPrice; }

    /**
     * retrieves the date of the purchase.
     *
     * @return String
     */
    public String getDate() { return myDate; }
}
