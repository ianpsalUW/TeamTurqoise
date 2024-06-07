package model;

import java.math.BigDecimal;

/**
 * A class that creates the purchase object.
 * Which contains the name, price and date of purchase.
 */

public class Purchase {

    /**
     * Instance field for the name of the purchase.
     */

    private String myName;

    /**
     * Instance field for the price of the purchase.
     */

    private BigDecimal myPrice;

    /**
     * instance field for the date of the purchase.
     */

    private String myDate;

    /**
     * Default constructor for purchase class, require the name, price and date.
     *
     * @param theName theName
     * @param thePrice thePrice
     * @param theDate theDate
     */
    public Purchase(final String theName, final BigDecimal thePrice, final String theDate) {
        myName = theName;
        myPrice = thePrice;
        myDate = theDate;
    }

    /**
     * Sets the name of the purchase.
     *
     * @param theName theName
     */
    public void setName(final String theName) { myName = theName; }

    /**
     * Sets the price of the purchase.
     *
     * @param thePrice thePrice
     */
    public void setPrice(final BigDecimal thePrice) { myPrice = thePrice; }

    /**
     * Sets the date of the purchase.
     *
     * @param theDate theDate
     */
    public void setDate(final String theDate) {myDate = theDate; }

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

    /**
     * returns the purchase as a String object.
     * @return String
     */
    @Override
    public String toString() {
        return myName +"\n"
                + myPrice + "\n"
                + myDate;
    }
}
