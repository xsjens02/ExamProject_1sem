package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.Subscriber;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditBookingController implements Initializable, Subscriber {

    @FXML
    private VBox VBoxMain;
    @FXML
    private ListView<Booking> lwBookings;
    @FXML
    private TextField txtTitle, txtAmountGuest;
    @FXML
    private Label lblErrorTitle, lblErrorGuest;

    private final BookingDAO bookingDAO = new BookingDAO_Impl();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
        SystemManager.getInstance().subscribe(Subject.User, this);
        SystemManager.getInstance().subscribe(Subject.Institution, this);

        requiredFields.add(new Pair<>(txtTitle, lblErrorTitle));
        requiredFields.add(new Pair<>(txtAmountGuest, lblErrorGuest));

        setupBookingList();

        lwBookings.getSelectionModel().selectedItemProperty().addListener(observable -> {
            Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                txtTitle.setText(selectedBooking.getBookingTitle());
                txtAmountGuest.setText(String.valueOf(selectedBooking.getAmountGuest()));
            }
        });

        txtAmountGuest.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAmountGuest.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    @FXML
    void onDeleteClick() {
        Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            boolean result = bookingDAO.remove(selectedBooking.getBookingID());
            if (result) {
                setupBookingList();
                clearAll();
            }
        }
    }

    @FXML
    void onCancelClick() {
        clearAll();
        SceneManager.closeScene(VBoxMain.getScene());
    }

    @FXML
    void onUpdateClick() {
        if (validateUpdate()) {
            Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
            selectedBooking.setBookingTitle(txtTitle.getText());
            boolean result = bookingDAO.update(selectedBooking);
            if (result) {
                setupBookingList();
                clearAll();
            }
        }
    }

    private void setupBookingList() {
        User currentUser = SystemManager.getInstance().getUser();
        if (currentUser != null) {
            lwBookings.getItems().clear();
            List<Booking> bookingList = bookingDAO.readAllFromUser(currentUser);
            if (bookingList != null) {
                lwBookings.getItems().setAll(bookingList);
            }
        }
    }

    private boolean validateUpdate() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle)
                && lwBookings.getSelectionModel().getSelectedItem() != null;
    }

    private void clearAll() {
        txtTitle.clear();
        txtAmountGuest.clear();
    }

    @Override
    public void onUpdate() {
        setupBookingList();
    }
}
