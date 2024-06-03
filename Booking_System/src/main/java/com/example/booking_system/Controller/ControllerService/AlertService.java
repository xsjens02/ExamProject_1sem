package com.example.booking_system.Controller.ControllerService;

import javafx.scene.control.Alert;

public class AlertService {
    /**
     * displays an information alert dialog with the specified title and message
     * @param title of the alert dialog
     * @param message to be displayed in the alert dialog
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
