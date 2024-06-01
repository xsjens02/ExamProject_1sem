package com.example.booking_system;

import com.example.booking_system.ControllerService.Controller;
import com.example.booking_system.ControllerService.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.openScene(Controller.Info, "Booking System");
    }


    public static void main(String[] args) {
        launch();
    }
}