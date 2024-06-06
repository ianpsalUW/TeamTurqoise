package tests;

import model.User;
import model.UserDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDBTest {

    private UserDB usDB;

    @BeforeEach
    public final void setup(){ usDB = new UserDB("dog");
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
    public final void UserDBToStringTest() { assertEquals("Bill\nBillsEmail@Email.com\nLola\nLolaEmail@Email.com", usDB.toString()); }


}