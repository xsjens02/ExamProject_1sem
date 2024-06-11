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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    /**
     * Håndterer apply-knappens handling. Validerer indtastede tider og opdaterer institutionens åbningstider.
     * Viser en alert baseret på operationens succes eller fejl.
     */
    @FXML
    private void handleApply() {
        String open = startTimeField.getText();
        String close = endTimeField.getText();
        String interval = intervalField.getText();

        // Validering og andvenelses logik
        if (validateTimeFormat(open) && validateTimeFormat(close) && validateInterval(interval)) {
            LocalTime startLocalTime = LocalTime.parse(open, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endLocalTime = LocalTime.parse(close, DateTimeFormatter.ofPattern("HH:mm"));

            Time openTime = Time.valueOf(startLocalTime);
            Time closeTime = Time.valueOf(endLocalTime);

            SystemManager.getInstance().getInstitution().setOpenTime(openTime);
            SystemManager.getInstance().getInstitution().setCloseTime(closeTime);

            // Hvis valid, viser succes
            boolean result = institutionDAO.update(SystemManager.getInstance().getInstitution());
            if (result) {
                SystemManager.getInstance().updateManager();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
                showAlert("Success", "Registrering af ny tid succesfuldt");
            } else {
                showAlert("Error", "Fejl i registrering af ny tid");
            }
        } else {
            // Hvis validt, viser error
            showAlert("Error", "Fejl i registrering af ny tid");
        }
    }

    /**
     * Håndterer cancel-knappens handling. Lukker det aktuelle vindue.
     */
    @FXML
    private void handleCancel() {
        // Lukker vinduet ved at hente den nuværende scene og stage, og derefter lukke stage
        Stage stage = (Stage) startTimeField.getScene().getWindow();
        stage.close();
    }

    /**
     * Opsætter de initiale tidsværdier i tekstfelterne baseret på institutionens nuværende åbningstider og interval.
     */
    private void setupTime() {
        Time openTime = SystemManager.getInstance().getInstitution().getOpenTime();
        Time closeTime = SystemManager.getInstance().getInstitution().getCloseTime();
        int interval = SystemManager.getInstance().getInstitution().getBookingTimeInterval();

        startTimeField.setText(openTime.toLocalTime().toString());
        endTimeField.setText(closeTime.toLocalTime().toString());
        intervalField.setText(String.valueOf(interval));
    }

    /**
     * Validerer tidsformatet for at sikre, det macher HH:mm
     * @param time tidsregistrering der skal valideres
     * @return True hvis tidsformat er valid eller false
     */
    private boolean validateTimeFormat(String time) {
        // Validerer tidsformat HH:mm
        return time.matches("^([01]\\d|2[0-3]):([0-5]\\d)$");
    }


    /**
     * Validerer intervallet for at sikre, at det er et positivt heltal.
     *
     * @param interval intervalstrengen der skal valideres
     * @return true hvis intervallet er valid, ellers false
     */
    private boolean validateInterval(String interval) {
        try {
            int minutes = Integer.parseInt(interval);
            return minutes > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Den kan vise en alert med den givne titel og besked
     * @param title titlen på alerten
     * @param message Indholdet af alerten
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opdaterer tidsfelterne med de aktuelle åbningstider og interval
     */
    @Override
    public void onUpdate() {
        setupTime();
    }
}
