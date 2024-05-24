package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.booking_system.Model.LoginService;

public class LoginScreenController {

    public TextField tfUsername;
    private final LoginService loginService = new LoginService();


    public Button btnLogin;
    public PasswordField pfPassword;


    public void onLoginButtonClick(ActionEvent actionEvent) {
        loginService.hashPassword(pfPassword.getText());
        loginService.validateLogin(tfUsername.getText(), pfPassword.getText());
    }
}
