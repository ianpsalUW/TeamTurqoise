package model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Spending {

    private double myTotal;

    private final List<Purchase> myPurchases;

    public Spending() { myPurchases = new ArrayList<>(); setTotal(0);}

    private void setTotal(double theTotal) { myTotal = theTotal; }

    public double getTotal() { return myTotal; }

    public void addPurchase(Purchase thePurchase) { myPurchases.add(thePurchase); }

    public void removePurchase(String theName) {
        Iterator itr = myPurchases.iterator();
        while (itr.hasNext()) {
            Purchase currentPurchase = (Purchase)itr.next();
            String currentName = currentPurchase.getName();
            if (currentName == theName) {
                itr.remove();
                return;
            }
        }
    }

    public void countTotal(){
        double total = 0;
        for (Purchase purchase : myPurchases) {
            total += purchase.getPrice();
        }

        myTotal = total;
    }
}
