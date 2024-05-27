package tests;

import model.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class BudgetTest {

    private Budget budget;

    @BeforeEach
    public final void setup(){ budget = new Budget(500);}

    @Test
    public final void getBudgetTest() { assertEquals(500, budget.getBudget());}

    @Test
    public final void checkBudgetTest() { assertTrue(budget.checkBudget(500));}

}
