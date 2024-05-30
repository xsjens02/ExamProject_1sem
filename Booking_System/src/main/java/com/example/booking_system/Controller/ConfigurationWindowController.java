package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.Controller;
import com.example.booking_system.ControllerService.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationWindowController implements Initializable {

    @FXML
    VBox VBox;
    @FXML
    Button newMeetingRoomButton;
    @FXML
    Button newCateringOptionButton;
    @FXML
    Button adjustOpeningHoursAndTimeSlotsButton;
    @FXML
    Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setWindowSize();
    }
    @FXML
    private void setWindowSize(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        VBox.setMinHeight(primaryScreenBounds.getHeight()/2);
        VBox.setMinWidth(primaryScreenBounds.getHeight()/2);
        VBox.setMaxWidth(primaryScreenBounds.getWidth()/2);
        adjustOpeningHoursAndTimeSlotsButton.setMinWidth(VBox.getMinWidth()-30);
        newMeetingRoomButton.setMinWidth(adjustOpeningHoursAndTimeSlotsButton.getMinWidth());
        newCateringOptionButton.setMinWidth(adjustOpeningHoursAndTimeSlotsButton.getMinWidth());
        returnButton.setMinWidth(adjustOpeningHoursAndTimeSlotsButton.getMinWidth()/2);
    }

    @FXML
    private void onNewMeetingRoomButtonClick(){
        SceneManager.changeStage((Stage) VBox.getScene().getWindow(), Controller.NewMeetingRoom, "nyt mødelokale");
    }
    @FXML
    private void onNewCateringOptionButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/NewCateringOption.fxml"));
            Parent root = loader.load();

            Stage newCateringOptionStage = new Stage();
            newCateringOptionStage.initModality(Modality.APPLICATION_MODAL);
            newCateringOptionStage.setTitle("Tilføj forplejning");
            newCateringOptionStage.setScene(new Scene(root));
            newCateringOptionStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onAdjustOpeningHoursAndTimeSlotsButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/OpeningHoursAndTimeSlotsWindow.fxml"));
            Parent root = loader.load();

            Stage openingHoursAndTimeSlotsStage = new Stage();
            openingHoursAndTimeSlotsStage.initModality(Modality.APPLICATION_MODAL);
            openingHoursAndTimeSlotsStage.setTitle("Tilpas åbningstider og tidsintervaller");
            openingHoursAndTimeSlotsStage.setScene(new Scene(root));
            openingHoursAndTimeSlotsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onReturnButtonClick(){
        //Stage stage = (Stage) returnButton.getScene().getWindow();
        //stage.close();
        SceneManager.openScene(Controller.Statistic, "statistik");
    }
}
