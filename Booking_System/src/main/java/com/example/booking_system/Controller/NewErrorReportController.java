package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.ValidationService;
import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.ErrorReport;
import com.example.booking_system.Model.Institution;
import com.example.booking_system.Model.MeetingRoom;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.ErrorReportDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewErrorReportController implements Initializable {

    private final DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();

    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private ListView<Equipment> lwRoomEquipment;
    @FXML
    private TextArea txtErrorDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<MeetingRoom> meetingRooms = Institution.getInstance().getMeetingRoomList();
        lwMeetingRooms.getItems().addAll(meetingRooms);

        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!lwRoomEquipment.getItems().isEmpty()) {
                    lwRoomEquipment.getItems().clear();
                }
                List<Equipment> equipmentList = newValue.getEquipmentList();
                lwRoomEquipment.getItems().addAll(equipmentList);
            }
        }));
    }

    @FXML
    void onCancelClick() {
        //close window
    }
    @FXML
    void onCreateClick() {
        if (ValidationService.validateAreaEntered(txtErrorDescription) && lwRoomEquipment.getSelectionModel().getSelectedItem() != null) {
            if (ValidationService.validAreaLength(txtErrorDescription, 150)) {

            }
        }
    }
}

