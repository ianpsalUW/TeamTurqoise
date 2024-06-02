package tests;

import model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest {
    private Log myLogTest;

    @BeforeEach
    public final void setup(){ myLogTest = new Log("Test", "Being Tested");}

    @Test
    public void testGetItem() { assertEquals("Test", myLogTest.getItem());}

    @Test
    public void testGetChange() { assertEquals("Being Tested", myLogTest.getChange());}

    @Test
    public void testGetDate() { Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/YY");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm a");
        assertEquals(dateForm.format(thisDate), myLogTest.getDate());
    }

    @Test
    public void testGetTimee() { Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/YY");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm a");
        assertEquals(timeForm.format(thisDate), myLogTest.getTime());
    }


}
