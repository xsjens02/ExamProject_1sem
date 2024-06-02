package com.example.booking_system.ControllerService.Utilities;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import java.util.List;

public class ValidationService {

    /**
     * validates if a text area have input
     * @param textArea area to validate for input
     * @param errorLabel label to display error message
     * @return true if area has input, false if not
     */
    public static boolean validateAreaEntered(TextArea textArea, Label errorLabel) {
        if (textArea.getText().isEmpty()) {
            errorLabel.setText("skal udfyldes");
            textArea.setOnMouseClicked(e -> {
                errorLabel.setText("");
                textArea.setOnMouseClicked(null);
            });
            return false;
        }
        return true;
    }

    /**
     * validates if a text field have input
     * @param textField field to validate for input
     * @param errorLabel label to display error message
     * @return true if field has input, false if not
     */
    public static boolean validateFieldEntered(TextField textField, Label errorLabel) {
        if (textField.getText().isEmpty()) {
            errorLabel.setText("skal udfyldes");
            textField.setOnMouseClicked(e -> {
                errorLabel.setText("");
                textField.setOnMouseClicked(null);
            });
            return false;
        }
        return true;
    }

    /**
     * validates if multiple text fields have input
     * @param fieldLabelList pair array list of fields to check and their corresponding error labels
     * @return true if all has input, false if one has no input
     */
    public static boolean validateFieldsEntered(List<Pair<TextField, Label>> fieldLabelList) {
        boolean valid = true;
        for (Pair<TextField, Label> pair : fieldLabelList) {
            if (pair.getKey().getText().isEmpty()) {
                if (valid) {
                    valid = false;
                }
                pair.getValue().setText("skal udfyldes");
                pair.getKey().setOnMouseClicked(e -> {
                    pair.getValue().setText("");
                    pair.getKey().setOnMouseClicked(null);
                });
            }
        }
        return valid;
    }

    /**
     * validates if text field input is within a specific length
     * @param textField field to validate
     * @param maxLength specific length to validate if input is within
     * @param errorLabel label to display error message
     * @return true if within specific length, false if not
     */
    public static boolean validFieldLength(TextField textField, int maxLength, Label errorLabel) {
        String fieldValue = textField.getText();
        if (fieldValue.length() > maxLength) {
            errorLabel.setText("maks. " + maxLength + " tegn");
            textField.setOnMouseClicked(e -> {
                errorLabel.setText("");
                textField.setOnMouseClicked(null);
            });
            return false;
        }
        return true;
    }

    /**
     * validates if area input is within a specific length
     * @param textArea area to validate
     * @param maxLength specific length to validate if input is within
     * @param errorLabel label to display error message
     * @return true if within specific length, false if not
     */
    public static boolean validAreaLength(TextArea textArea, int maxLength, Label errorLabel) {
        String areaValue = textArea.getText();
        if (areaValue.length() > maxLength) {
            errorLabel.setText("maks. " + maxLength + " tegn");
            textArea.setOnMouseClicked(e -> {
                errorLabel.setText("");
                textArea.setOnMouseClicked(null);
            });
            return false;
        }
        return true;
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
