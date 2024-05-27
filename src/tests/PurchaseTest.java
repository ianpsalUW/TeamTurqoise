package tests;
import model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PurchaseTest {
    private Purchase purchase;

    @BeforeEach
    public final void setup() { purchase = new Purchase("Burrito", 3.99, "5/27/24");}

    @Test
    public final void getNameTest() { assertEquals("Burrito", purchase.getName());}

    @Test
    public final void getPriceTest() { assertEquals(3.99, purchase.getPrice());}

    @Test
    public final void getDateTest() { assertEquals("5/27/24", purchase.getDate());}


}
