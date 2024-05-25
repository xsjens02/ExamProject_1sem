package com.example.booking_system.Controller;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.MeetingRoom;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.EquipmentDAO_Impl;
import com.example.booking_system.Persistence.MeetingRoomDAO_Impl;
import com.example.booking_system.ControllerService.ResetService;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.ControllerService.InstitutionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewMeetingRoomController implements Initializable {

    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    private final DAO<MeetingRoom> meetingRoomDAO = new MeetingRoomDAO_Impl();
    private final ObservableList<Equipment> roomEquipment = FXCollections.observableArrayList();

    private List<TextField> textFields;

    @FXML
    private ListView<Equipment> lwRoomEquipment, lwAllEquipment;
    @FXML
    private TextField txtRoomName, txtRoomCapacity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFields = new ArrayList<>(Arrays.asList(txtRoomName, txtRoomCapacity));
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
        if (ValidationService.validateFieldsEntered(textFields) && ValidationService.validateStringIsInt(txtRoomCapacity.getText())) {
            if (ValidationService.validFieldLength(txtRoomName, 30)) {
                String roomName = txtRoomName.getText();
                int availableSeats = Integer.parseInt(txtRoomCapacity.getText());
                int institutionID = InstitutionService.getInstance().getInstitution().getInstitutionID();
                if (!roomEquipment.isEmpty()) {
                    meetingRoomDAO.add(new MeetingRoom(roomName, institutionID, availableSeats, roomEquipment));
                } else {
                    meetingRoomDAO.add(new MeetingRoom(roomName, institutionID, availableSeats));
                }
                resetAll();
            }
        }
    }
    @FXML
    private void onCancelClick() {
        //close window
    }

    private void resetAll() {
        if (!roomEquipment.isEmpty()) {
            roomEquipment.clear();
        }
        if (lwAllEquipment.getSelectionModel().getSelectedItem() != null) {
            lwAllEquipment.getSelectionModel().clearSelection();
        }
        ResetService.resetFields(textFields);
    }
}