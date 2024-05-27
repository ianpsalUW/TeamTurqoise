package tests;

import model.Purchase;
import model.Spending;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpendingTest {
    private Spending myPDB;

    @BeforeEach
    public void setup(){ myPDB = new Spending();
        myPDB.addPurchase(new Purchase("Burrito", 3.99, "5/27/2024"));
        myPDB.addPurchase(new Purchase("Pepsi", 3.99, "5/27/2024"));
        myPDB.addPurchase(new Purchase("Quesadilla", 5.99, "5/27/2024"));
    }

    @Test
    public void countTotalTest() {
        myPDB.countTotal();
        assertEquals(13.97, myPDB.getTotal());
    }

    @Test
    public void addPurchaseTest() {
        myPDB.addPurchase(new Purchase("Fries", 0.99, "5/27/2024"));
        myPDB.countTotal();
        assertEquals(14.96, myPDB.getTotal());
    }

    @Test
    public void removePurchaseTest() {
        myPDB.removePurchase("Burrito");
        myPDB.countTotal();
        assertEquals(9.98, myPDB.getTotal());
    }
}
