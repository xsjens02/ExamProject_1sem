package com.example.booking_system.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpeningHoursAndTimeSlotsWindowController {

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextField intervalField;

    @FXML
    private void initialize() {
        // Initializing default values
        startTimeField.setText("08:00");
        endTimeField.setText("16:00");
        intervalField.setText("15");


    }

    @FXML
    private void handleApply() {
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String interval = intervalField.getText();

        // Validation and applying logic
        if (validateTimeFormat(startTime) && validateTimeFormat(endTime) && validateInterval(interval)) {
            // If valid, show success alert
            showAlert("Success", "Times and interval set successfully.");
        } else {
            // If invalid, show error alert
            showAlert("Error", "Please enter valid times and interval.");
        }
    }

    @FXML
    private void handleCancel() {
        // Lukker vinduet ved at hente den nuværende scene og stage, og derefter lukke stage
        Stage stage = (Stage) startTimeField.getScene().getWindow();
        stage.close();
    }

    private boolean validateTimeFormat(String time) {
        // validate time format HH:mm
        return time.matches("^([01]\\d|2[0-3]):([0-5]\\d)$");
    }

    private boolean validateInterval(String interval) {
        try {
            int minutes = Integer.parseInt(interval);
            return minutes > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
