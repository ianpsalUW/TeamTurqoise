package tests;

import main.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User usTest;

    @BeforeEach
    public final void setup() { usTest = new User ("John", "JSmith@yahoo.com");}

    @Test
    public final void logInTest() {
        assertTrue(usTest.logIn("John", "JSmith@yahoo.com"));
    }

    @Test
    public final void getNameTest() {
        assertEquals("John", usTest.getName());
    }

    @Test
    public final void getEmailTest() {
        assertEquals("JSmith@yahoo.com", usTest.getEmail());
    }


}