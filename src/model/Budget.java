package model;

import java.math.BigDecimal;

public class Budget {
    private BigDecimal myBudget;

    public Budget (final BigDecimal theBudget){
        setBudget(theBudget);
    }

    private void setBudget(BigDecimal theBudget){ myBudget = theBudget; }

    public BigDecimal getBudget(){ return myBudget; }

    public boolean checkBudget(BigDecimal theValue) { return (myBudget.compareTo(theValue) <= 0);}
}
