package com.example.booking_system.ControllerService;

import javafx.scene.control.TextField;

import java.util.List;

public class ValidationService {

    /**
     * validates if multiple text fields have input
     * @param textFields array of text fields to check for input
     * @return true if all has input, false if one or more has no input
     */
    public static boolean validateFieldsEntered(List<TextField> textFields) {
        boolean valid = true;
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.setText("skal udfyldes!");
                textField.setStyle("-fx-text-fill: white; -fx-background-color: grey");
                if (valid) {
                    valid = false;
                }
                textField.setOnMouseClicked(e -> {
                    textField.setStyle("-fx-text-fill: black; -fx-background-color: white");
                    textField.clear();
                });
            }
        }
        return valid;
    }

    /**
     * validates if text field input is within a specific length
     * @param textField text field to check
     * @param maxLength specific length to check input is within
     * @return true if within specific length, false if not
     */
    public static boolean validInputLength(TextField textField, int maxLength) {
        String fieldValue = textField.getText();
        return fieldValue.length() <= maxLength;
    }

    /**
     * validates if string can be parsed to integer
     * @param str string to parse
     * @return true if string can be parsed, false if not
     */
    public static boolean validateStringIsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * validates if string can be parsed to double
     * @param str string to parse
     * @return true if string can be parsed, false if not
     */
    public static boolean validateStringIsDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
