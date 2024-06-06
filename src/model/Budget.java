package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class that contains the budget of the project
 *
 * @version JDK 21.0
 * @author Jordan Festin
 */
public class Budget {

    /**
     * Instance field that contains the value of the budget
     */
    private BigDecimal myBudget;

    /**
     * Default constructor for budget, requires the value of the budget.
     *
     * @param theBudget theBudget
     */
    public Budget (final BigDecimal theBudget){
        setBudget(theBudget.setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * Sets value of the budget.
     *
     * @param theBudget theBudget
     */
    public void setBudget(BigDecimal theBudget){ myBudget = theBudget.setScale(2, RoundingMode.HALF_EVEN); }

    /**
     * receives the value of the budget
     *
     * @return BigDecimal
     */
    public BigDecimal getBudget(){ return myBudget.setScale(2, RoundingMode.HALF_EVEN); }

    /**
     * checks if the budget is less than or equal to another value.
     * returns true if the other value is within the budget.
     *
     * @param theValue theValue
     * @return boolean
     */
    public boolean checkBudget(BigDecimal theValue) { return (myBudget.compareTo(theValue) <= 0);}

    /**
     * Returns the budget as a String object.
     *
     * @return String
     */
    @Override
    public String toString(){
        return myBudget.toString();
    }
}
