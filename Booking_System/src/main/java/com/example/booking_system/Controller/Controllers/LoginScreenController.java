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

    /**
     * Method for when the login button is clicked, it retrieves the information from the text fields
     * and calls the validate login method.
     * @param actionEvent
     */
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

    /**
     * Method for when the forgot password button is clicked, it opens up a browser
     * to a website
     * @param actionEvent
     */
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

    /**
     * Method for when the cancel button is clicked, it closes the stage
     * @param actionEvent
     */
    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}