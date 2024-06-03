package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.Controller;
import com.example.booking_system.Controller.System.Managers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.openScene(Controller.Info, "Booking System");
    }


    public static void main(String[] args) {
        launch();
    }
}