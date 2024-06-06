package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.Controller;
import com.example.booking_system.Controller.System.Managers.SceneManager;
import com.example.booking_system.Controller.System.PubSub.Subscriber;
import com.example.booking_system.Controller.ControllerService.FormattingService;
import com.example.booking_system.Controller.ControllerService.TableViewService;
import com.example.booking_system.Model.Models.Institution;
import com.example.booking_system.Controller.System.PubSub.Subject;
import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Model.Models.User;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.DAO.InstitutionDAO_Impl;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class InfoScreenController implements Initializable, Subscriber {

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
        SystemManager.getInstance().subscribe(Subject.Institution,this);
        SystemManager.getInstance().subscribe(Subject.User,this);
        administration.getItems().addAll("Konfiguration","Statistik");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double mainHBoxHeight = mainHBox.getHeight();

        tableView.setPrefHeight(primaryScreenBounds.getHeight()-mainHBoxHeight);
        tableView.setFixedCellSize(tableView.getPrefHeight()/8);
        tableViewService = new TableViewService();
        tableViewService.populateTableView(tableView);

        adjustWindowSize(primaryScreenBounds);
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
        administration.minWidthProperty().bind(editBookingButton.widthProperty().multiply(1.2));
        loginButton.setGraphic(imageView);
    }
    @FXML
    private void onAdministrationDropdownChoice(){
        if(administration.getSelectionModel().getSelectedIndex() == 0) {
            SceneManager.openScene(Controller.Configuration,"Konfiguration");}
        if(administration.getSelectionModel().getSelectedIndex() == 1){
            SceneManager.openScene(Controller.Statistics, "statistik");
        }
    }

    /**
     * Method for when the login button is clicked, it checks if the user is already set
     * and if not it opens the scene for the login page.
     * @param actionEvent
     */
    public void onLoginButtonClick(ActionEvent actionEvent) {
        User currentUser = SystemManager.getInstance().getUser();
        if(!currentUser.getRole().name().equals("GUEST")){
            SystemManager.getInstance().clearUser();
            SystemManager.getInstance().notifySubscribers(Subject.User);
            SystemManager.getInstance().setUser(new User(1));
            updateUI();
        }else {
            openLoginPopup();
        }

    }

    @FXML
    private void onEditBookingClick() {
        SceneManager.openScene(Controller.EditBooking, "rediger booking");
    }

    /**
     * Method for opening the login scene
     */
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

    /**
     * Method that updates the UI for when a user has logged in
     */
    public void updateUI(){
        User currentUser = SystemManager.getInstance().getUser();
        if(!currentUser.getRole().name().equals("GUEST")){
            lblUserInfo.setText("Velkommen, " + currentUser.getFirstName());

            Image logoutImage = new Image(getClass().getResource("/images/logout.png").toExternalForm());
            ImageView imageView = new ImageView(logoutImage);
            imageView.setFitHeight(loginButton.getPrefHeight());
            imageView.setFitWidth(loginButton.getPrefWidth());
            loginButton.setGraphic(imageView);
            switch (currentUser.getRole().name()){
                case "GUEST": administration.setVisible(false); editBookingButton.setVisible(false); break;
                case "JANITOR": break;
                case "STUDENT": administration.setVisible(false); editBookingButton.setVisible(true); break;
                case "TEACHER": administration.setVisible(false); editBookingButton.setVisible(true); break;
                case "ADMIN": administration.setVisible(true); editBookingButton.setVisible(true); break;
            }
        } else {
            administration.setVisible(false);
            editBookingButton.setVisible(false);
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
        if(SystemManager.getInstance().getUser().getRole().name().equals("GUEST")){
            openLoginPopup();
        }
        else {
            SceneManager.openScene(Controller.NewBooking, "Ny booking");
        }
    }
    @FXML
    private void onSearchInputChanged(){
        tableView.getColumns().clear();
        searchInputTextField.clear();
        tableViewService.searchBookings(tableView,Date.valueOf(searchDate.getValue()), Time.valueOf(timeComboBox.getValue()+":00"));
    }
    @FXML
    private void onSearchTextChanged(){
        tableView.getColumns().clear();
        tableViewService.searchBookingsByText(tableView, searchInputTextField.getText(),Date.valueOf(searchDate.getValue()));
    }

    private void populateTimeComboBox(){
        double inputTime = tableViewService.tempHour;
        timeComboBox.setValue(FormattingService.formatTime(inputTime));
        if(searchDate.getValue() != tableViewService.localDate){
            timeComboBox.getItems().clear();
            double standardTime = (SystemManager.getInstance().getInstitution().getOpenTime().getHours())+(SystemManager.getInstance().getInstitution().getOpenTime().getMinutes()/60.0);
            while(standardTime < ((SystemManager.getInstance().getInstitution().getCloseTime().getHours())+(SystemManager.getInstance().getInstitution().getCloseTime().getMinutes()/60.0))){
                timeComboBox.getItems().add(FormattingService.formatTime(standardTime));
                standardTime += SystemManager.getInstance().getInstitution().getBookingTimeInterval()/60.00;
            }
        }
        else {
            if (inputTime > SystemManager.getInstance().getInstitution().getOpenTime().getHours()) {
                while (inputTime < SystemManager.getInstance().getInstitution().getCloseTime().getHours()) {
                    timeComboBox.getItems().add(FormattingService.formatTime(inputTime));
                    inputTime += SystemManager.getInstance().getInstitution().getBookingTimeInterval()/60.00;
                }
            } else {
                inputTime = SystemManager.getInstance().getInstitution().getOpenTime().getHours();
                while (inputTime < SystemManager.getInstance().getInstitution().getCloseTime().getHours()) {
                    timeComboBox.getItems().add(FormattingService.formatTime(inputTime));
                    inputTime += SystemManager.getInstance().getInstitution().getBookingTimeInterval()/60.00;
                }
            }
        }
    }


    @FXML
    private void onResetSearchButtonClick(){
        searchDate.setValue(tableViewService.localDate);
        timeComboBox.setValue(FormattingService.formatTime(tableViewService.tempHour));
        searchInputTextField.clear();
        tableViewService.populateTableView(tableView);
    }

    @Override
    public void onUpdate() {
        tableViewService = new TableViewService();
        tableViewService.populateTableView(tableView);
    }
}