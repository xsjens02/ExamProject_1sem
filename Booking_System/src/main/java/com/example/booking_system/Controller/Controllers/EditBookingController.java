package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.ControllerService.Managers.SceneManager;
import com.example.booking_system.Controller.ControllerService.Managers.SystemManager;
import com.example.booking_system.Controller.ControllerService.PubSub.Subject;
import com.example.booking_system.Controller.ControllerService.PubSub.Subscriber;
import com.example.booking_system.Controller.Utilities.ListenerService;
import com.example.booking_system.Controller.Utilities.ValidationService;
import com.example.booking_system.Model.Models.Booking;
import com.example.booking_system.Model.Models.User;
import com.example.booking_system.Persistence.DAO.BookingDAO_Impl;
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
    //region FXML annotations
    @FXML
    private VBox VBoxMain;
    @FXML
    private ListView<Booking> lwBookings;
    @FXML
    private TextField txtTitle, txtAmountGuest;
    @FXML
    private Label lblErrorTitle, lblErrorGuest;
    private final BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();
    //endregion
    //region initializer methods
    /**
     * initializes the controller class
     * @param url location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SystemManager.getInstance().subscribe(Subject.User, this);
        SystemManager.getInstance().subscribe(Subject.Institution, this);

        requiredFields.add(new Pair<>(txtTitle, lblErrorTitle));
        requiredFields.add(new Pair<>(txtAmountGuest, lblErrorGuest));

        lwBookings.getSelectionModel().selectedItemProperty().addListener(observable -> {
            Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                txtTitle.setText(selectedBooking.getBookingTitle());
                txtAmountGuest.setText(String.valueOf(selectedBooking.getAmountGuest()));
            }
        });

        ListenerService.setupNumericListener(txtAmountGuest);
    }

    /**
     * initializes the listView containing all user bookings
     */
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
    //endregion
    //region handler methods
    /**
     * handles deletion of a selected booking from system
     */
    @FXML
    void onDeleteClick() {
        Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            boolean result = bookingDAO.remove(selectedBooking.getBookingID());
            if (result) {
                setupBookingList();
                clearAll();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
            }
        }
    }

    /**
     * handles resetting view and closing of window
     */
    @FXML
    void onCancelClick() {
        clearAll();
        SceneManager.closeScene(VBoxMain.getScene());
    }

    /**
     * handles updating edited booking details in system
     */
    @FXML
    void onUpdateClick() {
        if (validateUpdate()) {
            Booking selectedBooking = lwBookings.getSelectionModel().getSelectedItem();
            selectedBooking.setBookingTitle(txtTitle.getText());
            boolean result = bookingDAO.update(selectedBooking);
            if (result) {
                setupBookingList();
                clearAll();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
            }
        }
    }
    //endregion
    //region view methods
    /**
     * clears view and set it to default state
     */
    private void clearAll() {
        txtTitle.clear();
        txtAmountGuest.clear();
    }
    //endregion
    //region validation methods
    /**
     * validates if all necessary data is in order to update a booking
     * @return if validation pass, false if not
     */
    private boolean validateUpdate() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle)
                && lwBookings.getSelectionModel().getSelectedItem() != null;
    }
    //endregion
    //region subscriber method
    /**
     * updates the booking ListView to reflect the current user's bookings
     */
    @Override
    public void onUpdate() {
        setupBookingList();
    }
    //endregion
}
