package model;

public class Budget {
    private long myBudget;

    public Budget (final long theBudget){
        setBudget(theBudget);
    }

    private void setBudget(long theBudget){ myBudget = theBudget; }

    public long getBudget(){ return myBudget; }

    public boolean checkBudget(long theValue) { return (myBudget <= theValue);}
}
