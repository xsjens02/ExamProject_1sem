package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.Controllers.InfoScreenController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.booking_system.Model.ModelService.LoginService;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class LoginScreenController {

    public TextField tfUsername;
    private final LoginService loginService = new LoginService();
    private InfoScreenController infoScreenController;

    public PasswordField pfPassword;
    public Button loginButton;
    public Button cancelButton;
    public Button forgotButton;
    public Label failedLoginLabel;

    public void setInfoScreenController(InfoScreenController infoScreenController){
        this.infoScreenController = infoScreenController;
    }


    public void onLoginButtonClick(ActionEvent actionEvent) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        boolean isLoggedIn = loginService.validateLogin(username, password);

        if(isLoggedIn){
            if(infoScreenController != null){
                infoScreenController.updateUI();
            }
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        }else{
            tfUsername.clear();
            pfPassword.clear();
            failedLoginLabel.setVisible(true);
        }

    }

    public void onForgotButtonClick(ActionEvent actionEvent) {
        String url = "https://moodle.easv.dk/login/forgot_password.php";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try{
                desktop.browse(new URI(url));
            }catch (IOException | URISyntaxException e){
                e.printStackTrace();
            }
        }

    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}