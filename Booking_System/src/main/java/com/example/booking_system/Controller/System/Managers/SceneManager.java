package com.example.booking_system.Controller.System.Managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneManager {
    private static final HashMap<Controller, Scene> scenes = new HashMap<>();

    // static initializer block to load scenes during class initialization
    static {
        loadScene(Controller.Configuration, "/com/example/booking_system/ConfigurationWindow.fxml");
        buildScene(Controller.Info, "/com/example/booking_system/InfoScreen.fxml", "/Stylesheets/tableViewStyle.css");
        loadScene(Controller.Login, "/com/example/booking_system/loginScreen.fxml");
        loadScene(Controller.NewBooking, "/com/example/booking_system/NewBooking.fxml");
        loadScene(Controller.EditBooking, "/com/example/booking_system/EditBooking.fxml");
        loadScene(Controller.NewCateringOption, "/com/example/booking_system/NewCateringOption.fxml");
        loadScene(Controller.NewErrorReport, "/com/example/booking_system/NewErrorReport.fxml");
        loadScene(Controller.NewMeetingRoom, "/com/example/booking_system/NewMeetingRoom.fxml");
        loadScene(Controller.OpeningHoursAndTimeSlots, "/com/example/booking_system/OpeningHoursAndTimeSlotsWindow.fxml");
        loadScene(Controller.Statistics, "/com/example/booking_system/statisticsWindow.fxml");
    }

    /**
     * loads a scene from the given FXML resource and associates it with the specified controller
     * @param controller controller associated with the scene
     * @param fxmlResource path to the FXML resource
     */
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

    /**
     * builds a scene from the given FXML and CSS resources and associates it with the specified controller
     * @param controller controller associated with the scene
     * @param fxmlResource path to the FXML resource
     * @param cssResource path to the CSS resource
     * @return built scene
     */
    private static Scene buildScene(Controller controller, String fxmlResource, String cssResource) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlResource));
            Parent view = loader.load();
            scene = new Scene(view);
            URL cssURL = SceneManager.class.getResource(cssResource);
            scene.getStylesheets().add(cssURL.toExternalForm());
            scenes.put(controller, scene);
        } catch (IOException e) {
            throw  new RuntimeException();
        }
        return scene;
    }

    /**
     * changes the stage's scene to the scene associated with the specified controller
     * @param currentStage current stage
     * @param sceneController controller associated with the desired scene
     * @param sceneTitle title of the scene
     */
    public static void changeStage(Stage currentStage, Controller sceneController, String sceneTitle) {
        currentStage.setTitle(sceneTitle);
        currentStage.setScene(scenes.get(sceneController));
    }

    /**
     * opens a new stage with the scene associated with the specified controller
     * @param sceneController controller associated with the desired scene
     * @param sceneTitle title of the scene
     */
    public static void openScene(Controller sceneController, String sceneTitle) {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(sceneTitle);
        newStage.setScene(scenes.get(sceneController));
        newStage.showAndWait();
    }

    /**
     * Closes the current scene's stage
     * @param currentScene current scene
     */
    public static void closeScene(Scene currentScene) {
        Stage stage = (Stage) currentScene.getWindow();
        stage.close();
    }
}
