package com.example.booking_system.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.booking_system.Model.LoginService;

public class LoginScreenController {

    LoginService loginService = new LoginService();


    public TextField tfUserName;
    public TextField tfPassword;
    public Button btnLogin;


    public void onBtnLoginClick(ActionEvent actionEvent) {

        loginService.hashPassword(tfPassword.getText());

    }
}
