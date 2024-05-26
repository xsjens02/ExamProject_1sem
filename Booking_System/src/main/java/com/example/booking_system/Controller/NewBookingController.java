package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.MeetingRoomDAO;
import com.example.booking_system.Persistence.MeetingRoomDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewBookingController implements Initializable {

    @FXML
    private VBox VBoxMain, VBoxDateOption, VBoxAddOptions;
    @FXML
    private CheckBox checkAdHoc;
    @FXML
    private DatePicker dpBookingDate;
    @FXML
    private TextField txtStartTime, txtEndTime, txtTitle, txtAmountGuest;
    @FXML
    private Label lblErrorStartTime, lblErrorEndTime, lblErrorTitle, lblErrorGuest;
    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private TextArea txtRoomDetails;
    @FXML
    private ComboBox<Catering> comboCatering;
    @FXML
    private ComboBox<String> comboDepartment;

    private final MeetingRoomDAO meetingRoomDAO = new MeetingRoomDAO_Impl();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requiredFields.add(new Pair<>(txtStartTime, lblErrorStartTime));
        requiredFields.add(new Pair<>(txtEndTime, lblErrorEndTime));
        requiredFields.add(new Pair<>(txtTitle, lblErrorTitle));
        requiredFields.add(new Pair<>(txtAmountGuest, lblErrorGuest));
        txtRoomDetails.setEditable(false);
        setupDisplay();
        setupCheckAdHocListener();
        setupDateAndTimeListeners();
        setupRoomListener();
    }

    @FXML
    void onBookClick() {
        if (validateBooking()) {
            System.out.println("Do booking");
        }
    }

    @FXML
    void onCancelClick() {
        SceneManager.closeScene(VBoxMain.getScene());
    }

    private void setupDisplay() {
        User currentUser = SystemManager.getInstance().getUser();
        if (currentUser.getRole() == Role.STUDENT) {
            VBoxDateOption.setVisible(false);
            VBoxAddOptions.setVisible(false);
        }
    }

    private void setupCheckAdHocListener() {
        checkAdHoc.selectedProperty().addListener(observable -> {
            if (checkAdHoc.isSelected()) {
                dpBookingDate.setValue(LocalDate.now());
                dpBookingDate.setDisable(true);
            } else {
                if (dpBookingDate.disabledProperty().get()) {
                    dpBookingDate.setDisable(false);
                }
            }
        });
    }
    private void setupDateAndTimeListeners() {
        dpBookingDate.valueProperty().addListener(observable -> {
            checkDateAndTime();
        });

        txtStartTime.textProperty().addListener(observable -> {
            checkDateAndTime();
        });

        txtEndTime.textProperty().addListener(observable -> {
            checkDateAndTime();
        });
    }

    private void checkDateAndTime() {
        boolean requiredData =  dpBookingDate.getValue() != null && !(txtStartTime.getText().isEmpty()) && !(txtEndTime.getText().isEmpty());
        if (requiredData) {
            if (ValidationService.validateStringIsDouble(txtStartTime.getText()) && ValidationService.validateStringIsDouble(txtEndTime.getText())) {
                int institutionID = SystemManager.getInstance().getInstitution().getInstitutionID();
                Date choosenDate = Date.valueOf(dpBookingDate.getValue());
                double startTime = Double.parseDouble(txtStartTime.getText());
                double endTime = Double.parseDouble(txtEndTime.getText());
                List<MeetingRoom> meetingRoomList = meetingRoomDAO.readAllAvailableRooms(institutionID, choosenDate, startTime, endTime);
                if (meetingRoomList != null) {
                    lwMeetingRooms.getItems().setAll(meetingRoomList);
                }
            }
        }
    }

    private void setupRoomListener() {
        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(observable -> {
            if (lwMeetingRooms.getSelectionModel().getSelectedItem() != null) {
                MeetingRoom selectedRoom = lwMeetingRooms.getSelectionModel().getSelectedItem();
                String description = selectedRoom.getRoomName() + "\n\nAntal pladser: " + selectedRoom.getAvailableSeats() + "\nUdstyr: ";
                txtRoomDetails.setText(description);
            }
        });
    }

    private boolean validateBooking() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && ValidationService.validateStringIsDouble(txtStartTime.getText())
                && ValidationService.validateStringIsDouble(txtEndTime.getText())
                && ValidationService.validateStringIsInt(txtAmountGuest.getText())
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle);
    }
}
