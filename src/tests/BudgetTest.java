package tests;

import model.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
public class BudgetTest {



    private Budget budget;

    @BeforeEach
    public final void setup(){ budget = new Budget(BigDecimal.valueOf(500));}

    @Test
    public final void getBudgetTest()
    { assertEquals(BigDecimal.valueOf(500).setScale(2, RoundingMode.HALF_EVEN), budget.getBudget());}

    @Test
    public final void checkBudgetTest() { assertTrue(budget.checkBudget(BigDecimal.valueOf(500)));}

}
