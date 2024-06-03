package com.example.booking_system.Controller.ControllerService;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;

public class ClearingService {

    /**
     * clears multiple text fields
     * @param fieldLabelList pair array list of fields and their corresponding error labels to clear
     */
    public static void clearFields(List<Pair<TextField, Label>> fieldLabelList) {
        for (Pair<TextField, Label> pair : fieldLabelList) {
            pair.getKey().clear();
        }
    }

    /**
     * clears a text area
     * @param textArea area to clear
     */
    public static void clearArea(TextArea textArea) {
        textArea.clear();
    }
}
