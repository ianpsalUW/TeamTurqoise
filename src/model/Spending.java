package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Spending {

    private BigDecimal myTotal;

    private final List<Purchase> myPurchases;

    public Spending() { myPurchases = new ArrayList<>(); setTotal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));}

    public void setTotal(BigDecimal theTotal) { myTotal = theTotal.setScale(2, RoundingMode.HALF_EVEN); }

    public BigDecimal getTotal() { return myTotal.setScale(2, RoundingMode.HALF_EVEN); }

    public List<Purchase> getPurchases() {
        return myPurchases;
    }

    public void addPurchase(Purchase thePurchase) {
        myPurchases.add(thePurchase);
        myTotal = myTotal.add(thePurchase.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void removePurchase(String theName) {
        Iterator itr = myPurchases.iterator();
        while (itr.hasNext()) {
            Purchase currentPurchase = (Purchase)itr.next();
            String currentName = currentPurchase.getName();
            if (Objects.equals(currentName, theName)) {
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
