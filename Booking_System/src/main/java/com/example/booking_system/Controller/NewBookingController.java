package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.CateringDAO;
import com.example.booking_system.Persistence.CateringDAO_Impl;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewBookingController implements Initializable {

    @FXML
    private VBox VBoxMain, VBoxDateOption, VBoxAddOptions;
    @FXML
    private CheckBox checkAdHoc;
    @FXML
    private DatePicker dpBookingDate;
    @FXML
    private TextField txtTitle, txtAmountGuest;
    @FXML
    private Label lblErrorTitle, lblErrorGuest;
    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private TextArea txtRoomDetails;
    @FXML
    private ComboBox<Catering> comboCatering;
    @FXML
    private ComboBox<String> comboDepartment;

    @FXML
    private ComboBox<Integer> ComboStartHour, ComboStartMinute, ComboEndMinute, ComboEndHour;

    private final MeetingRoomDAO meetingRoomDAO = new MeetingRoomDAO_Impl();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requiredFields.add(new Pair<>(txtTitle, lblErrorTitle));
        requiredFields.add(new Pair<>(txtAmountGuest, lblErrorGuest));
        txtRoomDetails.setEditable(false);
        setupDisplay();
        setupCheckAdHocListener();
        setupTimeBoxes();
        setupDateAndTimeListeners();
        setupRoomListener();
        setupCatering();
    }

    @FXML
    void onBookClick() {
        if (validateBooking()) {
            String bookingTitle = txtTitle.getText();
            int userID = SystemManager.getInstance().getUser().getUserID();
            int roomID = lwMeetingRooms.getSelectionModel().getSelectedItem().getRoomID();
            boolean adHoc = dpBookingDate.getValue().equals(LocalDate.now());
            // double startTime = Double.parseDouble(txtStartTime.getText());
            // double endTime = Double.parseDouble(txtEndTime.getText());
            //double duration = endTime - startTime;


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

    private void setupTimeBoxes() {
        int openHour = (int) SystemManager.getInstance().getInstitution().getOpenTime();
        int closeHour = (int) SystemManager.getInstance().getInstitution().getCloseTime();
        for (int hour = openHour; hour <= closeHour ; hour++) {
            ComboStartHour.getItems().add(hour);
            ComboEndHour.getItems().add(hour);
        }

        int minuteInterval = SystemManager.getInstance().getInstitution().getBookingTimeInterval();
        for (int minute = 0; minute < 60; minute += minuteInterval) {
            ComboStartMinute.getItems().add(minute);
            ComboEndMinute.getItems().add(minute);
        }
    }
    private void setupDateAndTimeListeners() {
        dpBookingDate.valueProperty().addListener(observable -> checkDateAndTime());
        ComboStartHour.valueProperty().addListener(observable -> checkDateAndTime());
        ComboStartMinute.valueProperty().addListener(observable -> checkDateAndTime());
        ComboEndHour.valueProperty().addListener(observable -> checkDateAndTime());
        ComboEndMinute.valueProperty().addListener(observable -> checkDateAndTime());
    }

    private void setupCatering() {
        CateringDAO cateringDAO = new CateringDAO_Impl();
        List<Catering> cateringList = cateringDAO.readAll(SystemManager.getInstance().getInstitution().getInstitutionID());

        for (Catering catering : cateringList) {
            comboCatering.getItems().add(catering);
        }
    }

    private void checkDateAndTime() {
        if (validateDateAndTime()) {
            LocalTime startTime = LocalTime.of(ComboStartHour.getValue(), ComboStartMinute.getValue());
            LocalTime endTime = LocalTime.of(ComboEndHour.getValue(), ComboEndMinute.getValue());
            if (!endTime.isAfter(startTime)) {
                lwMeetingRooms.getItems().clear();
            } else {
                int institutionID = SystemManager.getInstance().getInstitution().getInstitutionID();
                Date choosenDate = Date.valueOf(dpBookingDate.getValue());
                double start = convertToDouble(startTime);
                double end = convertToDouble(endTime);
                List<MeetingRoom> meetingRoomList = meetingRoomDAO.readAllAvailableRooms(institutionID, choosenDate, start, end);
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

    private boolean validateDateAndTime() {
        return dpBookingDate.getValue() != null
                && ComboStartHour.getSelectionModel().getSelectedItem() != null
                && ComboStartMinute.getSelectionModel().getSelectedItem() != null
                && ComboEndHour.getSelectionModel().getSelectedItem() != null
                && ComboEndMinute.getSelectionModel().getSelectedItem() != null;
    }

    private boolean validateBooking() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && lwMeetingRooms.getSelectionModel().getSelectedItem() != null
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle)
                && ValidationService.validateStringIsInt(txtAmountGuest.getText())
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle)
                && comboCatering.getSelectionModel().getSelectedItem() != null;
    }

    private double convertToDouble(LocalTime time) {
        return time.getHour() + time.getMinute() / 60.0;
    }
}
