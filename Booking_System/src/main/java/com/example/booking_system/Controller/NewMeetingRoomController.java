package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.MeetingRoom;
import com.example.booking_system.ControllerService.Subject;
import com.example.booking_system.Model.SystemManager;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.DAO.EquipmentDAO_Impl;
import com.example.booking_system.ControllerService.ClearingService;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Persistence.DAO.MeetingRoomDAO_Impl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewMeetingRoomController implements Initializable {

    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    private final DAO<MeetingRoom> meetingRoomDAO = new MeetingRoomDAO_Impl();
    private final ObservableList<Equipment> roomEquipment = FXCollections.observableArrayList();
    private final List<Pair<TextField, Label>> requiredFields = new ArrayList<>();

    @FXML
    private VBox VBox;
    @FXML
    private ListView<Equipment> lwRoomEquipment, lwAllEquipment;
    @FXML
    private TextField txtRoomName, txtRoomCapacity;
    @FXML
    private Label lblNameError, lblCapacityError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requiredFields.add(new Pair<>(txtRoomName, lblNameError));
        requiredFields.add(new Pair<>(txtRoomCapacity, lblCapacityError));
        List<Equipment> equipmentList = equipmentDAO.readAll();
        lwAllEquipment.getItems().addAll(equipmentList);
        lwRoomEquipment.setItems(roomEquipment);
    }

    @FXML
    private void onRemoveClick() {
        Equipment selectedEquipment = lwRoomEquipment.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            roomEquipment.remove(selectedEquipment);
        }
    }
    @FXML
    private void onAddClick() {
        Equipment selectedEquipment = lwAllEquipment.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null && !roomEquipment.contains(selectedEquipment)) {
            roomEquipment.add(selectedEquipment);
        }
    }

    @FXML
    private void onCreateClick() {
        if (validateMeetingRoom()) {
            String roomName = txtRoomName.getText();
            int availableSeats = Integer.parseInt(txtRoomCapacity.getText());
            int institutionID = SystemManager.getInstance().getInstitution().getInstitutionID();

            boolean insert;

            if (!roomEquipment.isEmpty()) {
                insert = meetingRoomDAO.add(new MeetingRoom(roomName, institutionID, availableSeats, roomEquipment));
            } else {
                insert = meetingRoomDAO.add(new MeetingRoom(roomName, institutionID, availableSeats));
            }

            if (insert) {
                resetAll();
            }
            SystemManager.getInstance().updateManager();
            SystemManager.getInstance().notifySubscribers(Subject.Institution);
        }
    }
    @FXML
    private void onCancelClick() {
        SceneManager.closeScene(VBox.getScene());
    }

    private boolean validateMeetingRoom() {
        return ValidationService.validateFieldsEntered(requiredFields)
                && ValidationService.validFieldLength(txtRoomName, 30, lblNameError)
                && ValidationService.validateStringIsInt(txtRoomCapacity.getText());
    }

    private void resetAll() {
        if (!roomEquipment.isEmpty()) {
            roomEquipment.clear();
        }
        if (lwAllEquipment.getSelectionModel().getSelectedItem() != null) {
            lwAllEquipment.getSelectionModel().clearSelection();
        }
        ClearingService.clearFields(requiredFields);
    }
}