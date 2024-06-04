package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Controller.System.PubSub.Subject;
import com.example.booking_system.Controller.System.PubSub.Subscriber;
import com.example.booking_system.Persistence.DAO.InstitutionDAO_Impl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Time;

public class OpeningHoursAndTimeSlotsWindowController implements Subscriber {

    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField intervalField;

    private final InstitutionDAO_Impl institutionDAO = new InstitutionDAO_Impl();

    @FXML
    private void initialize() {
        SystemManager.getInstance().subscribe(Subject.Institution, this);
        setupTime();
    }

    @FXML
    private void handleApply() {
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String interval = intervalField.getText();

        // Validation and applying logic
        if (validateTimeFormat(startTime) && validateTimeFormat(endTime) && validateInterval(interval)) {
            // If valid, show success alert
            boolean result = institutionDAO.update(SystemManager.getInstance().getInstitution());
            if (result) {
                SystemManager.getInstance().updateManager();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
                showAlert("Success", "Registrering af ny tid succesfuldt");
            }
        } else {
            // If invalid, show error alert
            showAlert("Error", "Fejl i registrering af ny tid");
        }
    }

    @FXML
    private void handleCancel() {
        // Lukker vinduet ved at hente den nuværende scene og stage, og derefter lukke stage
        Stage stage = (Stage) startTimeField.getScene().getWindow();
        stage.close();
    }

    private void setupTime() {
        Time openTime = SystemManager.getInstance().getInstitution().getOpenTime();
        Time closeTime = SystemManager.getInstance().getInstitution().getCloseTime();
        int interval = SystemManager.getInstance().getInstitution().getBookingTimeInterval();

        startTimeField.setText(openTime.toString());
        endTimeField.setText(closeTime.toString());
        intervalField.setText(String.valueOf(interval));
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

    @Override
    public void onUpdate() {
        setupTime();
    }
}
