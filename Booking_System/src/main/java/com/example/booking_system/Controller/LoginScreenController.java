package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.booking_system.Service.LoginService;
import javafx.stage.Stage;


public class LoginScreenController {

    public TextField tfUsername;
    private final LoginService loginService = new LoginService();

    public PasswordField pfPassword;
    public Button loginButton;
    public Button cancelButton;
    public Button forgotButton;
    public Label failledLoginLabel;
    public Label forgotPasswordLink;


    public void onLoginButtonClick(ActionEvent actionEvent) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        boolean isLoggedIn = loginService.validateLogin(username, password);

        if(isLoggedIn){
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        }else{
            failledLoginLabel.setVisible(true);
        }

    }

    public void onForgotButtonClick(ActionEvent actionEvent) {

        if(forgotButton.isArmed()){
            forgotPasswordLink.setVisible(true);
        }

    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}