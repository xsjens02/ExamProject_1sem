package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InfoScreenController implements Initializable {

    List<String> testForInfoScreen = new ArrayList<>();
    String forTest = "This is number: ";

    @FXML
    private ListView listView;

    @FXML
    private HBox HBox;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < 10; i++) {
            testForInfoScreen.add(forTest+i);
        }
        double hBoxHeight = HBox.getHeight();

        listView.getItems().addAll(testForInfoScreen);
        listView.setPrefHeight(primaryScreenBounds.getHeight()-hBoxHeight);
        listView.setFixedCellSize(primaryScreenBounds.getHeight()/14);
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/loginScreen.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}