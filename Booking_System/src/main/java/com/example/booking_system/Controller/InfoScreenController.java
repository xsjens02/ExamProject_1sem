package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.Controller;
import com.example.booking_system.ControllerService.FormattingService;
import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.TableViewService;
import com.example.booking_system.Model.Institution;
import com.example.booking_system.Model.Subject;
import com.example.booking_system.Model.SystemManager;
import com.example.booking_system.Model.User;
import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.InstitutionDAO_Impl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InfoScreenController implements Initializable {

    public Label lblUserInfo;
    DAO<Institution> institutionDAO = new InstitutionDAO_Impl();

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
    @FXML
    private TextField searchInputTextField;
    @FXML
    private ComboBox<String> timeComboBox;
    @FXML
    private DatePicker searchDate;
    @FXML
    private Button resetButton;
    @FXML
    private Button editBookingButton;
    private TableViewService tableViewService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SystemManager.getInstance().initManager(1);
        //SystemManager.getInstance().setUser(new User(2, "test", "test", 1, 3, "test", "test"));
        administration.getItems().addAll("Konfiguration","Statistik");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double mainHBoxHeight = mainHBox.getHeight();

        tableView.setPrefHeight(primaryScreenBounds.getHeight()-mainHBoxHeight);
        tableView.setFixedCellSize(tableView.getPrefHeight()/8);
        tableViewService = new TableViewService();
        tableViewService.populateTableView(tableView);

        adjustWindowSize(primaryScreenBounds);
        //TableViewService.updateTime(timeComboBox);
        populateTimeComboBox();
        updateUI();
        searchDate.setValue(LocalDate.now());


    }
    private void adjustWindowSize(Rectangle2D primaryScreenBounds){
        leftHBox.minWidthProperty().bind(mainHBox.widthProperty().divide(2));
        rightHBox.minWidthProperty().bind(mainHBox.widthProperty().divide(2));

        searchInputTextField.minWidthProperty().bind(rightHBox.widthProperty().divide(9).multiply(3));
        searchDate.minWidthProperty().bind(rightHBox.widthProperty().divide(9).multiply(1.5));
        timeComboBox.minWidthProperty().bind(rightHBox.widthProperty().divide(9).multiply(1.5));
        resetButton.minWidthProperty().bind(rightHBox.widthProperty().divide(9).multiply(1.5));
        if(primaryScreenBounds.getHeight()*2<primaryScreenBounds.getWidth()){
            searchInputTextField.minWidthProperty().set(200);
            searchDate.minWidthProperty().set(100);
            timeComboBox.minWidthProperty().set(100);
            resetButton.minWidthProperty().set(100);
        }

        Image loginImage = new Image(getClass().getResource("/images/login.png").toExternalForm());
        ImageView imageView = new ImageView(loginImage);
        imageView.setFitHeight(loginButton.getPrefHeight());
        imageView.setFitWidth(loginButton.getPrefWidth());
        administration.minWidthProperty().bind(editBookingButton.widthProperty().multiply(1.5));
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
        User currentUser = SystemManager.getInstance().getUser();
        if(currentUser != null){
            SystemManager.getInstance().clearUser();
            SystemManager.getInstance().notifySubscribers(Subject.User);
            updateUI();
        }else {
            openLoginPopup();
        }

    }


    private void openLoginPopup(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/booking_system/loginScreen.fxml"));
            Parent root = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setInfoScreenController(this);

            Stage loginWindow = new Stage();
            loginWindow.initModality(Modality.APPLICATION_MODAL);
            loginWindow.setTitle("Login");
            loginWindow.setScene(new Scene(root));
            loginWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
 
    public void updateUI(){
        User currentUser = SystemManager.getInstance().getUser();
        if(currentUser != null){
            lblUserInfo.setText("Velkommen, " + currentUser.getFirstName());

            Image logoutImage = new Image(getClass().getResource("/images/logout.png").toExternalForm());
            ImageView imageView = new ImageView(logoutImage);
            imageView.setFitHeight(loginButton.getPrefHeight());
            imageView.setFitWidth(loginButton.getPrefWidth());
            loginButton.setGraphic(imageView);
        } else {
            lblUserInfo.setText("");
            Image loginImage = new Image(getClass().getResource("/images/login.png").toExternalForm());
            ImageView imageView = new ImageView(loginImage);
            imageView.setFitHeight(loginButton.getPrefHeight());
            imageView.setFitWidth(loginButton.getPrefWidth());
            loginButton.setGraphic(imageView);
        }
    }


    @FXML
    public void onErrorReportClick() {
        SceneManager.openScene(Controller.NewErrorReport, "Fejlmelding");
    }

    @FXML
    public void onNewBookingClick() {
        SceneManager.openScene(Controller.NewBooking, "Ny booking");
    }
    @FXML
    private void onSearchInputChanged(){
        searchInputTextField.clear();
        tableViewService.searchBookings(tableView,Date.valueOf(searchDate.getValue()),FormattingService.formatTime(timeComboBox.getValue()));
    }
    @FXML
    private void onSearchTextChanged(){
        tableViewService.searchBookingsByText(tableView, searchInputTextField.getText(),Date.valueOf(searchDate.getValue()));
    }
    private void populateTimeComboBox(){
        double inputTime = tableViewService.tempHour;
        timeComboBox.setValue(FormattingService.formatTime(inputTime));
        if(searchDate.getValue() != tableViewService.localDate){
            timeComboBox.getItems().clear();
            double standardTime = 08.00;
            while(standardTime < tableViewService.institution.getCloseTime()){
                timeComboBox.getItems().add(FormattingService.formatTime(standardTime));
                standardTime += 0.25;
            }
        }
        else {
            if (inputTime > tableViewService.institution.getOpenTime()) {
                while (inputTime < tableViewService.institution.getCloseTime()) {
                    timeComboBox.getItems().add(FormattingService.formatTime(inputTime));
                    inputTime += 0.25;
                }
            } else {
                inputTime = tableViewService.institution.getOpenTime();
                while (inputTime < tableViewService.institution.getCloseTime()) {
                    timeComboBox.getItems().add(FormattingService.formatTime(inputTime));
                    inputTime += 0.25;
                }
            }

        }
    }
    @FXML
    private void onResetSearchButtonClick(){
        searchDate.setValue(tableViewService.localDate);
        timeComboBox.setValue(FormattingService.formatTime(tableViewService.tempHour));
        searchInputTextField.clear();
    }
}