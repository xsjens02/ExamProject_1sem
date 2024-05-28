package com.example.booking_system.Controller;

import com.example.booking_system.ControllerService.Controller;
import com.example.booking_system.ControllerService.SceneManager;
import com.example.booking_system.ControllerService.TableViewService;
import com.example.booking_system.Model.Institution;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

        adjustWindowSize();
        updateUI();
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
        User currentUser = SystemManager.getInstance().getUser();
        if(currentUser != null){
            SystemManager.getInstance().clearUser();
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
}