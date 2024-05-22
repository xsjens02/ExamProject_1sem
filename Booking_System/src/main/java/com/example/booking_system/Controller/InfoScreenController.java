package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    public Button btnLogin;
    List<String> testForInfoScreen = new ArrayList<>();
    String forTest = "This is number: ";


    @FXML
    private ListView listView;

    @FXML
    private HBox HBox;
    @FXML
    public ComboBox administration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        administration.getItems().addAll("Konfiguration","Statistik");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < 10; i++) {
            testForInfoScreen.add(forTest+i);
        }
        double hBoxHeight = HBox.getHeight();

        listView.getItems().addAll(testForInfoScreen);
        listView.setPrefHeight(primaryScreenBounds.getHeight()-hBoxHeight);
        listView.setFixedCellSize(primaryScreenBounds.getHeight()/14);
    }
    @FXML
    private void onAdministrationDropdownChoice(){
        if(administration.getSelectionModel().getSelectedIndex() == 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/ConfigurationWindow.fxml"));
                Parent root = loader.load();

                Stage configWindow = new Stage();
                configWindow.initModality(Modality.APPLICATION_MODAL);
                configWindow.setTitle("Konfiguration");
                configWindow.setScene(new Scene(root));
                configWindow.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(administration.getSelectionModel().getSelectedIndex() == 1){
            System.out.println("Åben statistik");
        }
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/loginScreen.fxml"));
            Parent root = loader.load();

            Stage loginWindow = new Stage();
            loginWindow.initModality(Modality.APPLICATION_MODAL);
            loginWindow.setTitle("Login");
            loginWindow.setScene(new Scene(root));
            loginWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}