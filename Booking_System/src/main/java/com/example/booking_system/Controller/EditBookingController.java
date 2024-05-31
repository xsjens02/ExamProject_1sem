package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditBookingController {

    @FXML
    private VBox VBoxMain;

    @FXML
    private ComboBox<?> comboCatering;

    @FXML
    private ComboBox<?> comboDepartment;

    @FXML
    private ComboBox<?> comboEndTime;

    @FXML
    private ComboBox<?> comboStartTime;

    @FXML
    private DatePicker dpBookingDate;

    @FXML
    private Label lblErrorGuest;

    @FXML
    private Label lblErrorTitle;

    @FXML
    private ListView<?> lwBookings;

    @FXML
    private TextField txtAmountGuest;

    @FXML
    private TextField txtTitle;

    @FXML
    void onCancelClick(ActionEvent event) {

    }

    @FXML
    void onDeleteClick(ActionEvent event) {

    }

    @FXML
    void onUpdateClick(ActionEvent event) {

    }

}
