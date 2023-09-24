package tests;

import models.User;
import org.junit.jupiter.api.Test;
import services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    UserService service = new UserService();

    @Test
    void testStore() {
        User user = new User();
        user.setTelegramId(788889911);
        user.setName("Anvar");
        user.setSurname("Zaripboyev");
        user.setUsername("anvar_dasturchi");

        service.store(user);

        assertEquals(1, user.getId());
    }
}