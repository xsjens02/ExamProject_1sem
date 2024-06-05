package com.example.booking_system.Model.ModelService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private final LoginService loginService = new LoginService();

    @Test
    void hashPassword() {
        String expected = "-1105788501";
        String actual = loginService.hashPassword("jensen5678");
        assertEquals(expected, actual);
    }

    @Test
    void validateLoginValid() {
        boolean expected = true;
        boolean actual = loginService.validateLogin("jens1234", "jensen5678");
        assertEquals(expected, actual);
    }

    @Test
    void validateLoginInvalid() {
        boolean expected = false;
        boolean actual = loginService.validateLogin("jens1234", "jensen56789");
        assertEquals(expected, actual);
    }
}