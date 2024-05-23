package com.example.booking_system.ControllerService;

import javafx.scene.control.TextField;

import java.util.List;

public class ResetService {
    /**
     * clears multiple text fields
     * @param textFields array of text fields to clear
     */
    public static void resetFields(List<TextField> textFields) {
        for (TextField textField : textFields) {
            textField.clear();
        }
    }
}
