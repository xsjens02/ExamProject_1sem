package com.example.booking_system.ControllerService;

import com.example.booking_system.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static final HashMap<ControllerKeys, Scene> scenes = new HashMap<>();

    static {
        try {
            FXMLLoader configViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/ConfigurationWindow.fxml"));
            Parent configView = configViewLoader.load();
            Scene configScene = new Scene(configView);
            scenes.put(ControllerKeys.Configuration, configScene);

            FXMLLoader infoViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/InfoScreen.fxml"));
            Parent infoView = infoViewLoader.load();
            Scene infoScene = new Scene(infoView);
            scenes.put(ControllerKeys.Info, infoScene);

            FXMLLoader loginViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/loginScreen.fxml"));
            Parent loginView = loginViewLoader.load();
            Scene loginScene = new Scene(loginView);
            scenes.put(ControllerKeys.Login, loginScene);

            FXMLLoader newBookingViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/NewBooking.fxml"));
            Parent newBookingView = newBookingViewLoader.load();
            Scene newBookingScene = new Scene(newBookingView);
            scenes.put(ControllerKeys.NewBooking, newBookingScene);

            FXMLLoader newCateringViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/NewCateringOption.fxml"));
            Parent newCateringView = newCateringViewLoader.load();
            Scene newCateringScene = new Scene(newCateringView);
            scenes.put(ControllerKeys.NewCateringOption, newCateringScene);

            FXMLLoader newErrorReportViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/NewErrorReport.fxml"));
            Parent newErrorReportView = newErrorReportViewLoader.load();
            Scene newErrorReportScene = new Scene(newErrorReportView);
            scenes.put(ControllerKeys.NewErrorReport, newErrorReportScene);

            FXMLLoader newMeetingRoomViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/NewMeetingRoom.fxml"));
            Parent newMeetingRoomView = newMeetingRoomViewLoader.load();
            Scene newMeetingRoomScene = new Scene(newMeetingRoomView);
            scenes.put(ControllerKeys.NewMeetingRoom, newMeetingRoomScene);

            FXMLLoader openingHoursAndTimeSlotsViewLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/booking_system/OpeningHoursAndTimeSlotsWindow.fxml"));
            Parent openingHoursAndTimeSlotsView = openingHoursAndTimeSlotsViewLoader.load();
            Scene openingHoursAndTimeSlotsScene = new Scene(openingHoursAndTimeSlotsView);
            scenes.put(ControllerKeys.OpeningHoursAndTimeSlots, openingHoursAndTimeSlotsScene);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void changeScene(Stage currentStage, ControllerKeys sceneController, String sceneTitle) {
        currentStage.setTitle(sceneTitle);
        currentStage.setScene(scenes.get(sceneController));
    }

    public static void openScene(ControllerKeys sceneController, String sceneTitle) {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(sceneTitle);
        newStage.setScene(scenes.get(sceneController));
        newStage.showAndWait();
    }
}
