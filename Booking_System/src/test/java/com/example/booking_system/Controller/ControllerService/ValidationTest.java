package com.example.booking_system.Controller.ControllerService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationTest {

    @org.junit.jupiter.api.Test
    void validateStringIsIntValid() {
        boolean expected = true;
        boolean actual = ValidationService.validateStringIsInt("100");
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateStringIsIntInvalidDouble() {
        boolean expected = false;
        boolean actual = ValidationService.validateStringIsInt("1.0");
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateStringIsIntInvalidString() {
        boolean expected = false;
        boolean actual = ValidationService.validateStringIsInt("text");
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateStringIsDoubleValid() {
        boolean expected = true;
        boolean actual = ValidationService.validateStringIsDouble("1.0");
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateStringIsDoubleValidInt() {
        boolean expected = true;
        boolean actual = ValidationService.validateStringIsDouble("100");
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void validateStringIsDoubleInvalidString() {
        boolean expected = false;
        boolean actual = ValidationService.validateStringIsDouble("text");
        assertEquals(expected, actual);
    }
}