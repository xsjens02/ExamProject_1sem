package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Model.Models.ErrorReport;
import com.example.booking_system.Model.Models.MeetingRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class EditErrorReportController {

    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private ListView<ErrorReport> lwErrorReports;
    @FXML
    private TextArea txtErrorDescription;

    @FXML
    void onCancelClick() {}

    @FXML
    void onRemoveClick() {}

}
