package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.ControllerService.AlertService;
import com.example.booking_system.Controller.System.Managers.SceneManager;
import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Controller.System.PubSub.Subject;
import com.example.booking_system.Controller.System.PubSub.Subscriber;
import com.example.booking_system.Model.Models.ErrorReport;
import com.example.booking_system.Model.Models.Institution;
import com.example.booking_system.Model.Models.MeetingRoom;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.DAO.ErrorReportDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditErrorReportController implements Initializable, Subscriber {
    //region FXML annotation
    @FXML
    private VBox VBox;
    @FXML
    private ListView<MeetingRoom> lwMeetingRooms;
    @FXML
    private ListView<ErrorReport> lwErrorReports;
    @FXML
    private TextArea txtErrorDescription;
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

        txtErrorDescription.setEditable(false);
        setupMeetingRooms();

        lwMeetingRooms.getSelectionModel().selectedItemProperty().addListener(observable -> {
            MeetingRoom selectedRoom = lwMeetingRooms.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                lwErrorReports.getItems().setAll(selectedRoom.getUnresolvedReports());
            } else {
                lwErrorReports.getItems().clear();
            }
        });

        lwErrorReports.getSelectionModel().selectedItemProperty().addListener(observable -> {
            ErrorReport selectedReport = lwErrorReports.getSelectionModel().getSelectedItem();
            if (selectedReport != null) {
                selectedReport.setupUnresolvedDescription();
                txtErrorDescription.setText(selectedReport.getUnresolvedDescription());
            } else {
                txtErrorDescription.clear();
            }
        });
    }

    /**
     * initializes list of meeting rooms with unresolved error reports
     */
    private void setupMeetingRooms() {
        lwMeetingRooms.getItems().clear();
        Institution currentInstitution = SystemManager.getInstance().getInstitution();
        if (currentInstitution != null) {
            List<MeetingRoom> meetingRoomList = currentInstitution.getMeetingRoomList().stream()
                    .filter(meetingRoom -> meetingRoom.getUnresolvedReports() != null && !meetingRoom.getUnresolvedReports().isEmpty())
                    .toList();

            lwMeetingRooms.getItems().setAll(meetingRoomList);
        }
    }
    //endregion
    //region handler methods
    /**
     * handles resetting view and closing of window
     */
    @FXML
    void onCancelClick() {
        clearView();
        SceneManager.closeScene(VBox.getScene());
    }

    /**
     * handles resolving an error report
     */
    @FXML
    void onRemoveClick() {
        ErrorReport selectedReport = lwErrorReports.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();
            selectedReport.setResolved(true);
            boolean result = errorReportDAO.update(selectedReport);
            if (result) {
                SystemManager.getInstance().updateManager();
                SystemManager.getInstance().notifySubscribers(Subject.Institution);
            } else {
                AlertService.showAlert("fejl", "kunne ikke fjerne fejlrapport");
            }
        }
    }
    //region
    //region view methods
    /**
     * clears view and set it to default state
     */
    private void clearView() {
        lwMeetingRooms.getSelectionModel().clearSelection();
    }
    //endregion
    //region subscriber methods
    /**
     * updates list of meeting rooms with unresolved error report current institution
     */
    @Override
    public void onUpdate() {
        setupMeetingRooms();
    }
    //endregion
}
