package com.example.booking_system.ControllerService;

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
        loadScene(ControllerKeys.Configuration, "/com/example/booking_system/ConfigurationWindow.fxml");
        loadScene(ControllerKeys.Info, "/com/example/booking_system/InfoScreen.fxml");
        loadScene(ControllerKeys.Login, "/com/example/booking_system/loginScreen.fxml");
        loadScene(ControllerKeys.NewBooking, "/com/example/booking_system/NewBooking.fxml");
        loadScene(ControllerKeys.NewCateringOption, "/com/example/booking_system/NewCateringOption.fxml");
        loadScene(ControllerKeys.NewErrorReport, "/com/example/booking_system/NewErrorReport.fxml");
        loadScene(ControllerKeys.NewMeetingRoom, "/com/example/booking_system/NewMeetingRoom.fxml");
        loadScene(ControllerKeys.OpeningHoursAndTimeSlots, "/com/example/booking_system/OpeningHoursAndTimeSlotsWindow.fxml");
    }

    private static void loadScene(ControllerKeys controllerKey, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent view = loader.load();
            Scene scene = new Scene(view);
            scenes.put(controllerKey, scene);
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
