package tests;

import static org.junit.jupiter.api.Assertions.*;

import main.User;
import main.UserDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDBTest {

    private UserDB usDB;

    @BeforeEach
    public final void setup(){ usDB = new UserDB();
        usDB.addUser(new User("Bill", "BillsEmail@Email.com"));
        usDB.addUser(new User("Lola", "LolaEmail@Email.com"));
    }

    @Test
    public final void addUserTest(){
        usDB.addUser(new User("Grandma", "Grandma@gmail.com"));
        assertEquals("Grandma", usDB.getUserByName("Grandma").getName());
    }

    @Test
    public final void getUserByNameTest(){
        assertEquals("Bill", usDB.getUserByName("Bill").getName());
    }

    @Test
    public final void getUserByEmailTest() { assertEquals("Lola", usDB.getUserByEmail("LolaEmail@Email.com").getName()); }

    @Test
    public final void UserDBToStringTest() { assertEquals("Bill BillsEmail@Email.com\nLola LolaEmail@Email.com", usDB.toString()); }


}