package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that collects purchase objects into an Array List.
 *
 * @version JDK 21.0
 * @author Jordan Festin
 */
public class Spending {

    /**
     * Instance field for the total amount of money spent.
     */
    private BigDecimal myTotal;

    /**
     * Instance field for the array list that contains Purchase objects.
     */
    private final List<Purchase> myPurchases;

    /**
     * Default constructor initializes the array list and sets default total to zero.
     */
    public Spending() { myPurchases = new ArrayList<>(); setTotal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));}

    /**
     * sets total to given amount.
     *
     * @param theTotal theTotal
     */
    public void setTotal(BigDecimal theTotal) { myTotal = theTotal.setScale(2, RoundingMode.HALF_EVEN); }

    /**
     * Retrieves the total amount spent.
     *
     * @return BigDecimal
     */
    public BigDecimal getTotal() { return myTotal.setScale(2, RoundingMode.HALF_EVEN); }

    /**
     * Retrieves the array list of purchases.
     *
     * @return List</Purchase>
     */
    public List<Purchase> getPurchases() {
        return myPurchases;
    }

    /**
     * Adds a purchase to the arraylist.
     *
     * @param thePurchase thePurchase
     */
    public void addPurchase(Purchase thePurchase) {
        myPurchases.add(thePurchase);
        myTotal = myTotal.add(thePurchase.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * removes a purchase from the array list.
     *
     * @param theName theName
     */
    public void removePurchase(String theName) {
        Iterator<Purchase> itr = myPurchases.iterator();
        while (itr.hasNext()) {
            Purchase currentPurchase = (Purchase)itr.next();
            String currentName = currentPurchase.getName();
            if (Objects.equals(currentName, theName)) {
                itr.remove();
                return;
            }
        }
    }

    /**
     * Sums up the total price of all the purchased items in the list.
     */
    public void countTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (Purchase i : myPurchases) {

           total = total.add(i.getPrice());
        }

        myTotal = total;
    }
}
