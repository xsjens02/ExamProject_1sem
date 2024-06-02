package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.Subject;
import com.example.booking_system.ControllerService.Subscriber;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.DAO.BookingDAO_Impl;
import com.example.booking_system.Persistence.DAO.CateringDAO_Impl;
import com.example.booking_system.Persistence.DAO.DepartmentDAO_Impl;
import com.example.booking_system.Persistence.DAO.MeetingRoomDAO_Impl;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class NewBookingController implements Initializable, Subscriber {
    //region FXML annotation
    @FXML
    private VBox VBoxMain, VBoxMultipleDateOption;
    @FXML
    private HBox HBoxDateOption;
    @FXML
    private CheckBox checkAdHoc, checkMultiple;
    @FXML
    private DatePicker dpBookingDate;
    @FXML
    private TextField txtDateMultiplier, txtTitle, txtAmountGuest;
    @FXML
    private Label lblErrorTitle, lblErrorGuest;
    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private ListView<LocalDate> lwChosenDates;
    @FXML
    private TextArea txtRoomDetails;
    @FXML
    private ComboBox<Catering> comboCatering;
    @FXML
    private ComboBox<Department> comboDepartment;
    @FXML
    private ComboBox<String> comboStartTime, comboEndTime;
    //endregion
    //region instance variables
    private final MeetingRoomDAO_Impl meetingRoomDAO = new MeetingRoomDAO_Impl();
    private final BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();
    private final Map<Property<?>, InvalidationListener> listenerMap = new HashMap<>();
    //endregion
    //region initializers
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtRoomDetails.setEditable(false);
        initFieldList();
        initListenerMap();
        initConstantListeners();
        setupDisplay();
        SystemManager.getInstance().subscribe(Subject.User, this);
        SystemManager.getInstance().subscribe(Subject.Institution, this);
    }

    private void initFieldList() {
        requiredFields.add(new Pair<>(txtTitle, lblErrorTitle));
        requiredFields.add(new Pair<>(txtAmountGuest, lblErrorGuest));
    }

    private void initListenerMap() {
        List<Property<?>> properties = List.of(
                checkAdHoc.selectedProperty(),
                checkMultiple.selectedProperty(),
                dpBookingDate.valueProperty(),
                lwChosenDates.itemsProperty(),
                comboStartTime.valueProperty(),
                comboEndTime.valueProperty()
        );

        for (Property<?> property : properties) {
            listenerMap.put(property, null);
        }
    }

    private void initConstantListeners() {
        setupNumericListener(txtDateMultiplier);
        setupNumericListener(txtAmountGuest);

        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(observable -> {
            MeetingRoom selectedRoom = lwMeetingRooms.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                selectedRoom.setupDescription();
                txtRoomDetails.setText(selectedRoom.getRoomDescription());
            } else {
                txtRoomDetails.clear();
            }
        });
    }
    //endregion
    //region handler methods
    @FXML
    void onPlusClick() {
        LocalDate chosenDate = dpBookingDate.getValue();
        if (chosenDate != null) {
            if (!txtDateMultiplier.getText().isEmpty()) {
                if (ValidationService.validateStringIsInt(txtDateMultiplier.getText())) {
                    int multiplier = Integer.parseInt(txtDateMultiplier.getText());
                    for (int i = 0; i < multiplier; i++) {
                        if (i == 0) {
                            lwChosenDates.getItems().add(chosenDate);
                        } else {
                            lwChosenDates.getItems().add(chosenDate.plusWeeks(i));
                        }
                        sortDateList();
                        txtDateMultiplier.clear();
                    }
                }
            } else {
                lwChosenDates.getItems().add(chosenDate);
                sortDateList();
            }
        }
    }

    @FXML
    void onMinusClick() {
        LocalDate chosenDate = lwChosenDates.getSelectionModel().getSelectedItem();
        if (chosenDate != null) {
            lwChosenDates.getItems().remove(chosenDate);
        }
    }

    @FXML
    void onCancelClick() {
        clearAll();
        SceneManager.closeScene(VBoxMain.getScene());
    }

    @FXML
    void onBookClick() {
        if (validateBooking()) {
            String bookingTitle = txtTitle.getText();
            int userID = SystemManager.getInstance().getUser().getUserID();
            String responsible = SystemManager.getInstance().getUser().getFirstName();
            int roomID = lwMeetingRooms.getSelectionModel().getSelectedItem().getRoomID();
            boolean adHoc = dpBookingDate.getValue().equals(LocalDate.now());
            LocalTime startTime = LocalTime.parse(comboStartTime.getValue());
            LocalTime endTime = LocalTime.parse(comboEndTime.getValue());
            double start = convertToDouble(startTime);
            double end = convertToDouble(endTime);
            double duration = end - start;

            boolean result = false;
            if (checkMultiple.isSelected()) {
                List<LocalDate> dates = lwChosenDates.getItems();
                if (!dates.isEmpty()) {
                    if (validateCatering()) {
                        for (LocalDate date : dates) {
                            Date sqlDate = Date.valueOf(date);
                            int menuID = comboCatering.getSelectionModel().getSelectedItem().getMenuID();
                            int departmentID = comboDepartment.getSelectionModel().getSelectedItem().getDepartmentID();
                            result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration, menuID, departmentID));
                        }
                    } else {
                        for (LocalDate date : dates) {
                            Date sqlDate = Date.valueOf(date);
                            result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration));
                        }
                    }
                }
            } else if (checkAdHoc.isSelected()) {
                LocalDate date = LocalDate.now();
                Date sqlDate = Date.valueOf(date);
                if (validateCatering()) {
                    int menuID = comboCatering.getSelectionModel().getSelectedItem().getMenuID();
                    int departmentID = comboDepartment.getSelectionModel().getSelectedItem().getDepartmentID();
                    result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration, menuID, departmentID));
                } else {
                    result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration));
                }
            } else {
                LocalDate date = dpBookingDate.getValue();
                Date sqlDate = Date.valueOf(date);
                if (validateCatering()) {
                    int menuID = comboCatering.getSelectionModel().getSelectedItem().getMenuID();
                    int departmentID = comboDepartment.getSelectionModel().getSelectedItem().getDepartmentID();
                    result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration, menuID, departmentID));
                } else {
                    result = bookingDAO.add(new Booking(bookingTitle, userID, responsible, roomID, adHoc, sqlDate, start, end, duration));
                }
            }
            if (result) {
                clearAll();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
            }
        }
    }
    //endregion
    //region setup methods
    private void setupDisplay() {
        setupTimeBoxes();
        setupAdhocListener();
        setupMultipleListener();
        User currentUser = SystemManager.getInstance().getUser();
        if (currentUser.getRole() == Role.STUDENT) {
            disableAdhoc(true);
            disableDate(true);
            checkMultiple.setVisible(false);
            displayMoreDateOption(false);
            disableCatering();
        } else if (currentUser.getRole() == Role.TEACHER || currentUser.getRole() == Role.ADMIN) {
            disableAdhoc(false);
            disableDate(false);
            checkMultiple.setVisible(true);
            displayMoreDateOption(false);
            setupDateAndTimeListeners(true);
            setupCatering();
        }
    }

    private void setupTimeBoxes() {
        comboStartTime.getItems().clear();
        comboEndTime.getItems().clear();

        double openTime = SystemManager.getInstance().getInstitution().getOpenTime();
        double closeTime = SystemManager.getInstance().getInstitution().getCloseTime();
        int minuteInterval = SystemManager.getInstance().getInstitution().getBookingTimeInterval();

        int openHour = (int) openTime;
        int openMinute = (int) ((openTime - openHour) * 60);
        int closeHour = (int) closeTime;
        int closeMinute = (int) ((closeTime - closeHour) * 60);

        LocalTime startTime = LocalTime.of(openHour, openMinute);
        LocalTime endTime = LocalTime.of(closeHour, closeMinute);

        while (!startTime.isAfter(endTime)) {
            String formattedTime = startTime.toString();
            comboStartTime.getItems().add(formattedTime);
            comboEndTime.getItems().add(formattedTime);
            startTime = startTime.plusMinutes(minuteInterval);
        }
    }

    private void setupCatering() {
        comboCatering.getItems().clear();
        comboDepartment.getItems().clear();

        DepartmentDAO_Impl departmentDAO = new DepartmentDAO_Impl();
        List<Department> departmentList = departmentDAO.readAllFromUser(SystemManager.getInstance().getUser());

        if (departmentList != null) {
            for (Department department : departmentList) {
                comboDepartment.getItems().add(department);
            }
            CateringDAO_Impl cateringDAO = new CateringDAO_Impl();
            List<Catering> cateringList = cateringDAO.readAllFromInstitution(SystemManager.getInstance().getInstitution().getInstitutionID());

            if (cateringList != null) {
                for (Catering catering : cateringList) {
                    comboCatering.getItems().add(catering);
                }
            }
        } else {
            disableCatering();
        }
    }

    private void setupAdhocListener() {
        setupListener(checkAdHoc.selectedProperty(), observable -> {
            clearTime();
            lwMeetingRooms.getItems().clear();
            if (checkAdHoc.isSelected() && checkMultiple.isSelected()) {
                checkMultiple.setSelected(false);
            }
            if (checkAdHoc.isSelected()) {
                disableDate(true);
                displayMoreDateOption(false);
                setupDateAndTimeListeners(true);
            } else {
                disableDate(false);
            }
        });
    }

    private void setupMultipleListener() {
        setupListener(checkMultiple.selectedProperty(), observable -> {
            clearTime();
            lwMeetingRooms.getItems().clear();
            if (checkMultiple.isSelected() && checkAdHoc.isSelected()) {
                checkAdHoc.setSelected(false);
            }
            if(checkMultiple.isSelected()) {
                displayMoreDateOption(true);
                setupDateAndTimeListeners(false);
                removeCurrentListener(dpBookingDate.valueProperty());
            } else if (!checkMultiple.isSelected()) {
                lwChosenDates.getItems().clear();
                displayMoreDateOption(false);
                setupDateAndTimeListeners(true);
            }
        });
    }

    private void setupDateAndTimeListeners(boolean singleDate) {
        if (singleDate) {
            List<Property<?>> properties = List.of(
                    dpBookingDate.valueProperty(),
                    comboStartTime.valueProperty(),
                    comboEndTime.valueProperty()
            );
            setupListeners(properties, observable -> checkDateAndTime(true));
            removeCurrentListener(lwChosenDates.itemsProperty());
            removeCurrentListener(txtDateMultiplier.textProperty());
        } else {
            List<Property<?>> properties = List.of(
                    lwChosenDates.itemsProperty(),
                    comboStartTime.valueProperty(),
                    comboEndTime.valueProperty()
            );
            setupListeners(properties, observable -> checkDateAndTime(false));
            removeCurrentListener(dpBookingDate.valueProperty());
        }
    }

    private void setupListener(Property<?> property, InvalidationListener listener) {
        removeCurrentListener(property);
        property.addListener(listener);
        listenerMap.put(property, listener);
    }


    private void setupListeners(List<Property<?>> properties, InvalidationListener listener) {
        removeCurrentListener(properties);
        for (Property<?> property : properties) {
            property.addListener(listener);
            listenerMap.put(property, listener);
        }
    }

    private void removeCurrentListener(Property<?> property) {
        InvalidationListener listener = listenerMap.get(property);
        if (listener != null) {
            property.removeListener(listener);
            listenerMap.put(property, null);
        }
    }

    private void removeCurrentListener(List<Property<?>> properties) {
        for (Property<?> property : properties) {
            InvalidationListener listener = listenerMap.get(property);
            if (listener != null) {
                property.removeListener(listener);
                listenerMap.put(property, null);
            }
        }
    }

    private void setupNumericListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
    //endregion
    //region view methods
    private void disableAdhoc(boolean disable) {
        checkAdHoc.setSelected(disable);
        checkAdHoc.setDisable(disable);
    }

    private void disableDate(boolean disable) {
        if (disable) {
            dpBookingDate.setValue(LocalDate.now());
        }
        dpBookingDate.setDisable(disable);
    }

    private void displayMoreDateOption(boolean display) {
        HBoxDateOption.setVisible(display);
        VBoxMultipleDateOption.setVisible(display);
    }

    private void disableCatering() {
        if (comboCatering.getItems() != null) {
            comboCatering.getItems().clear();
        }
        if (comboDepartment.getItems() != null) {
            comboDepartment.getItems().clear();
        }
    }

    private void clearAll() {
        txtDateMultiplier.clear();
        lwChosenDates.getItems().clear();
        clearTime();
        lwMeetingRooms.getItems().clear();
        txtRoomDetails.clear();
        txtTitle.clear();
        txtAmountGuest.clear();
        lwMeetingRooms.getItems().clear();
        txtRoomDetails.clear();
        comboCatering.getSelectionModel().clearSelection();
        comboDepartment.getSelectionModel().clearSelection();
        User currentUser = SystemManager.getInstance().getUser();
        if (currentUser.getRole() == Role.TEACHER || currentUser.getRole() == Role.ADMIN) {
            checkAdHoc.setSelected(false);
            checkMultiple.setSelected(false);
            dpBookingDate.setValue(null);
            setupDateAndTimeListeners(true);
        }
    }

    private void clearTime() {
        comboStartTime.getSelectionModel().clearSelection();
        comboEndTime.getSelectionModel().clearSelection();
    }
    //endregion
    //region validation methods
    private boolean validateTime() {
        return comboStartTime.getSelectionModel().getSelectedItem() != null
                && comboEndTime.getSelectionModel().getSelectedItem() != null;
    }

    private boolean validateCatering() {
        return comboCatering.getSelectionModel().getSelectedItem() != null
                && comboDepartment.getSelectionModel().getSelectedItem() != null;
    }

    private boolean validateBooking() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && lwMeetingRooms.getSelectionModel().getSelectedItem() != null
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle)
                && ValidationService.validateStringIsInt(txtAmountGuest.getText())
                && ValidationService.validFieldLength(txtTitle, 30, lblErrorTitle);
    }
    //endregion
    //region functional method
    private void checkDateAndTime(boolean singleDate) {
        if (validateTime()) {

            LocalTime startTime = LocalTime.parse(comboStartTime.getValue());
            LocalTime endTime = LocalTime.parse(comboEndTime.getValue());

            if (!endTime.isAfter(startTime)) {
                lwMeetingRooms.getItems().clear();
                return;
            }

            int institutionID = SystemManager.getInstance().getInstitution().getInstitutionID();
            double start = convertToDouble(startTime);
            double end = convertToDouble(endTime);

            List<MeetingRoom> meetingRoomList = null;

            if (singleDate && dpBookingDate.getValue() != null) {
                Date choosenDate = Date.valueOf(dpBookingDate.getValue());
                meetingRoomList = meetingRoomDAO.readAllAvailableOnDate(institutionID, choosenDate, start, end);
            } else if (!singleDate && lwChosenDates.getItems() != null) {
                List<LocalDate> chosenDates = lwChosenDates.getItems();
                List<Date> searchDates = new ArrayList<>();
                for (LocalDate date : chosenDates) {
                    searchDates.add(Date.valueOf(date));
                }
                meetingRoomList = meetingRoomDAO.readAllAvailableWithinDates(institutionID, searchDates, start, end);
            }

            if (meetingRoomList != null) {
                lwMeetingRooms.getItems().setAll(meetingRoomList);
            }
        }
    }
    //endregion
    //region helper methods
    private void sortDateList() {
        ObservableList<LocalDate> dates = lwChosenDates.getItems();
        FXCollections.sort(dates);
    }

    private double convertToDouble(LocalTime time) {
        return time.getHour() + time.getMinute() / 60.0;
    }
    //endregion

    @Override
    public void onUpdate() {
        if (SystemManager.getInstance().getUser() != null) {
            setupDisplay();
        }
    }
}
