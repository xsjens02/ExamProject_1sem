package com.example.booking_system.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

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
}