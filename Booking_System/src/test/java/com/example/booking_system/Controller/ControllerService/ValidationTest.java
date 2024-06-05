package com.example.booking_system.Controller.ControllerService;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationTest {

    @org.junit.jupiter.api.Test
    void validateFieldLengthValid() {
        try {
            boolean expected = true;
            TextField textField = new TextField();
            textField.setText("test");
            int maxLength = 5;
            Label label = new Label();
            boolean actual = ValidationService.validFieldLength(textField, maxLength, label);
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}