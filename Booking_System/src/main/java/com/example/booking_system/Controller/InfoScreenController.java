package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.TableViewService;
import com.example.booking_system.Model.Institution;
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
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private TableView tableView;
    @FXML
    private HBox mainHBox;
    @FXML
    public ComboBox administration;
    @FXML
    private HBox leftHBox;
    @FXML
    private HBox rightHBox;
    @FXML
    private Button loginButton;
    private TableViewService tableViewService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Institution.setInstance(1);
        administration.getItems().addAll("Konfiguration","Statistik");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double mainHBoxHeight = mainHBox.getHeight();

        tableView.setPrefHeight(primaryScreenBounds.getHeight()-mainHBoxHeight);
        tableView.setFixedCellSize(tableView.getPrefHeight()/8);
        tableViewService = new TableViewService();
        tableViewService.populateTableView(tableView);

        adjustWindowSize();
    }
    private void adjustWindowSize(){
        leftHBox.minWidthProperty().bind(mainHBox.widthProperty().divide(2));
        rightHBox.minWidthProperty().bind(mainHBox.widthProperty().divide(2));

        Image loginImage = new Image(getClass().getResource("/images/login.png").toExternalForm());
        ImageView imageView = new ImageView(loginImage);
        imageView.setFitHeight(loginButton.getPrefHeight());
        imageView.setFitWidth(loginButton.getPrefWidth());
        loginButton.setGraphic(imageView);
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

    @FXML
    public void onErrorReportClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/NewErrorReport.fxml"));
            Parent root = loader.load();

            Stage errorReportWindow = new Stage();
            errorReportWindow.initModality(Modality.APPLICATION_MODAL);
            errorReportWindow.setTitle("Fejlmelding");
            errorReportWindow.setScene(new Scene(root));
            errorReportWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}