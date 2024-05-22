package com.example.booking_system.Controller;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.MeetingRoom;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.EquipmentDAO_Impl;
import com.example.booking_system.Persistence.MeetingRoomDAO_Impl;
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

    @FXML
    private ListView<Equipment> lwRoomEquipment, lwAllEquipment;
    @FXML
    private TextField txtRoomName, txtRoomCapacity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        List<TextField> requiredFields = new ArrayList<>(Arrays.asList(txtRoomName, txtRoomCapacity));
        if (fieldsNotEmpty(requiredFields) && isInteger(txtRoomCapacity.getText())) {
            if (validInputLength(txtRoomName, 30)) {
                String roomName = txtRoomName.getText();
                int availableSeats = Integer.parseInt(txtRoomCapacity.getText());
                //institutionID
                if (!roomEquipment.isEmpty()) {
                    meetingRoomDAO.add(new MeetingRoom(roomName, 1, availableSeats, roomEquipment));
                } else {
                    meetingRoomDAO.add(new MeetingRoom(roomName, 1, availableSeats));
                }
                resetAll();
            }
        }
    }
    @FXML
    private void onCancelClick() {
        //close window
    }

    private boolean fieldsNotEmpty(List<TextField> textFields) {
        boolean valid = true;
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.setText("skal udfyldes!");
                textField.setStyle("-fx-text-fill: white; -fx-background-color: grey");
                if (valid) {
                    valid = false;
                }
                textField.setOnMouseClicked(e -> {
                    textField.setStyle("-fx-text-fill: black; -fx-background-color: white");
                    textField.clear();
                });
            }
        }
        return valid;
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validInputLength(TextField textField, int maxLength) {
        String fieldValue = textField.getText();
        return fieldValue.length() <= maxLength;
    }

    private void resetAll() {
        if (!roomEquipment.isEmpty()) {
            roomEquipment.clear();
        }
        if (lwAllEquipment.getSelectionModel().getSelectedItem() != null) {
            lwAllEquipment.getSelectionModel().clearSelection();
        }
        txtRoomName.clear();
        txtRoomCapacity.clear();
    }
}