package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.ClearingService;
import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.ErrorReportDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewErrorReportController implements Initializable {

    private final DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();

    @FXML
    private VBox VBox;
    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private ListView<Equipment> lwRoomEquipment;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Label lblDescriptionError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<MeetingRoom> meetingRooms = SystemManager.getInstance().getInstitution().getMeetingRoomList();
        lwMeetingRooms.getItems().addAll(meetingRooms);

        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<Equipment> equipmentList = newValue.getEquipmentList();
                lwRoomEquipment.getItems().setAll(equipmentList);
            }
        }));
    }
    @FXML
    void onCreateClick() {
        if (validateErrorReport()) {
            int userID = SystemManager.getInstance().getUser().getUserID();
            int roomID = lwMeetingRooms.getSelectionModel().getSelectedItem().getRoomID();
            int equipmentID = lwRoomEquipment.getSelectionModel().getSelectedItem().getEquipmentID();
            String description = txtDescription.getText();

            boolean insert = errorReportDAO.add(new ErrorReport(userID, roomID, equipmentID, description, false));
            if (insert) {
                resetAll();
            }
        }
    }

    @FXML
    void onCancelClick() {
        SceneManager.closeScene(VBox.getScene());
    }

    private boolean validateErrorReport() {
        return ValidationService.validateAreaEntered(txtDescription, lblDescriptionError)
                && ValidationService.validAreaLength(txtDescription, 150, lblDescriptionError)
                && lwMeetingRooms.getSelectionModel().getSelectedItem() != null
                && lwRoomEquipment.getSelectionModel().getSelectedItem() != null
                && SystemManager.getInstance().getUser() != null;
    }

    private void resetAll() {
        if (lwRoomEquipment.getSelectionModel().getSelectedItem() != null) {
            lwRoomEquipment.getSelectionModel().clearSelection();
        }
        if (!lwRoomEquipment.getItems().isEmpty()) {
            lwRoomEquipment.getItems().clear();
        }
        if (lwMeetingRooms.getSelectionModel().getSelectedItem() != null) {
            lwMeetingRooms.getSelectionModel().clearSelection();
        }
        ClearingService.clearArea(txtDescription);
    }
}

