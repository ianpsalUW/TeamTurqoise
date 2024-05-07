package tests;

import main.About;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AboutTest {

    private About about;

    @BeforeEach
    public final void setup() {
        about = new About();
    }

    @Test
    public final void addTest() {
        about.add("Ian Salsich");
        about.add("Christian Pineda");
        about.add("Bill Lactaoen");
        about.add("Jordan Festin");

        assertEquals("Ian Salsich\nChristian Pineda\nBill Lactaoen\nJordan Festin\n", about.devToString());
    }

    @Test
    public final void getVersionTest() {
        assertEquals(0.1, about.getVersion());
    }

}