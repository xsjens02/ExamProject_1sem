package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.SceneManager;
import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Controller.System.PubSub.Subject;
import com.example.booking_system.Controller.System.PubSub.Subscriber;
import com.example.booking_system.Controller.ControllerService.ClearingService;
import com.example.booking_system.Controller.ControllerService.ValidationService;
import com.example.booking_system.Model.Models.Equipment;
import com.example.booking_system.Model.Models.ErrorReport;
import com.example.booking_system.Model.Models.MeetingRoom;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.DAO.ErrorReportDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewErrorReportController implements Initializable, Subscriber {
    //region FXMl annotations
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
    //endregion
    //region instance variables
    private final DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();
    //endregion
    //region initializer methods
    /**
     * initializes the controller class
     * @param url location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SystemManager.getInstance().subscribe(Subject.Institution, this);

        setupRoomList();

        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(observable -> {
            MeetingRoom selectedRoom = lwMeetingRooms.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                List<Equipment> equipmentList = selectedRoom.getEquipmentList();
                if (equipmentList != null) {
                    lwRoomEquipment.getItems().setAll(equipmentList);
                }
            }
        });
    }

    /**
     * initializes the listView containing all institution meeting rooms
     */
    private void setupRoomList() {
        lwMeetingRooms.getItems().clear();
        List<MeetingRoom> meetingRooms = SystemManager.getInstance().getInstitution().getMeetingRoomList();
        lwMeetingRooms.getItems().addAll(meetingRooms);
    }
    //endregion
    //region handler methods
    /**
     * handles the creation of an error report for a meeting room equipment issue
     */
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
                SystemManager.getInstance().updateManager();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
            }
        }
    }

    /**
     * handles resetting view and closing of window
     */
    @FXML
    void onCancelClick() {
        resetAll();
        SceneManager.closeScene(VBox.getScene());
    }
    //endregion
    //region view methods
    /**
     * clears view and set it to default state
     */
    private void resetAll() {
        lwMeetingRooms.getSelectionModel().clearSelection();
        lwRoomEquipment.getItems().clear();
        ClearingService.clearArea(txtDescription);
    }
    //endregion
    //region validation methods
    /**
     * validates if all necessary data is in order to create an error report
     * @return true if validation pass, false if not
     */
    private boolean validateErrorReport() {
        return ValidationService.validateAreaEntered(txtDescription, lblDescriptionError)
                && ValidationService.validAreaLength(txtDescription, 150, lblDescriptionError)
                && lwMeetingRooms.getSelectionModel().getSelectedItem() != null
                && lwRoomEquipment.getSelectionModel().getSelectedItem() != null
                && SystemManager.getInstance().getUser() != null;
    }
    //endregion
    //region subscriber method
    /**
     * updates the meeting room ListView to reflect the current institution's meeting rooms
     */
    @Override
    public void onUpdate() {
        setupRoomList();
    }
    //endregion
}

