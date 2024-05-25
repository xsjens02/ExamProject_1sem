package com.example.booking_system.ControllerService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static final HashMap<Controller, Scene> scenes = new HashMap<>();

    static {
        loadScene(Controller.Configuration, "/com/example/booking_system/ConfigurationWindow.fxml");
        loadScene(Controller.Info, "/com/example/booking_system/InfoScreen.fxml");
        loadScene(Controller.Login, "/com/example/booking_system/loginScreen.fxml");
        loadScene(Controller.NewBooking, "/com/example/booking_system/NewBooking.fxml");
        loadScene(Controller.NewCateringOption, "/com/example/booking_system/NewCateringOption.fxml");
        loadScene(Controller.NewErrorReport, "/com/example/booking_system/NewErrorReport.fxml");
        loadScene(Controller.NewMeetingRoom, "/com/example/booking_system/NewMeetingRoom.fxml");
        loadScene(Controller.OpeningHoursAndTimeSlots, "/com/example/booking_system/OpeningHoursAndTimeSlotsWindow.fxml");
    }

    private static void loadScene(Controller controller, String fxmlResource) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlResource));
            Parent view = loader.load();
            Scene scene = new Scene(view);
            scenes.put(controller, scene);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private Scene buildScene(String fxmlResource, String cssResource) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlResource));
            Parent view = loader.load();
            scene = new Scene(view);
            scene.getStylesheets().add(SceneManager.class.getResource(cssResource).toExternalForm());
        } catch (IOException e) {
            throw  new RuntimeException();
        }
        return scene;
    }

    public static void changeScene(Stage currentStage, Controller sceneController, String sceneTitle) {
        currentStage.setTitle(sceneTitle);
        currentStage.setScene(scenes.get(sceneController));
    }

    public static void openScene(Controller sceneController, String sceneTitle) {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(sceneTitle);
        newStage.setScene(scenes.get(sceneController));
        newStage.showAndWait();
    }
}
