package model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Spending {

    private BigDecimal myTotal;

    private final List<Purchase> myPurchases;

    public Spending() { myPurchases = new ArrayList<>(); setTotal(BigDecimal.valueOf(0));}

    private void setTotal(BigDecimal theTotal) { myTotal = theTotal; }

    public BigDecimal getTotal() { return myTotal; }

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
        BigDecimal total = BigDecimal.ZERO;
        for (Purchase i : myPurchases) {

           total = total.add(i.getPrice());
        }

        myTotal = total;
    }
}
