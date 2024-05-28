package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Budget {
    private BigDecimal myBudget;

    public Budget (final BigDecimal theBudget){
        setBudget(theBudget.setScale(2, RoundingMode.HALF_EVEN));
    }

    public void setBudget(BigDecimal theBudget){ myBudget = theBudget.setScale(2, RoundingMode.HALF_EVEN); }

    public BigDecimal getBudget(){ return myBudget.setScale(2, RoundingMode.HALF_EVEN); }

    public boolean checkBudget(BigDecimal theValue) { return (myBudget.compareTo(theValue) <= 0);}
}
