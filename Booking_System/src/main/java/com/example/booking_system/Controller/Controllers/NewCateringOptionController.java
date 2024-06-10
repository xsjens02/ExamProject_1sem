package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.SceneManager;
import com.example.booking_system.Model.Models.Catering;
import com.example.booking_system.Persistence.DAO.CateringDAO_Impl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class
NewCateringOptionController implements Initializable {

    @FXML
    VBox VBox;
    @FXML
    Label newCateringNameLabel;
    @FXML
    TextField newCateringNameTextField;
    @FXML
    Label pricePerPersonLabel;
    @FXML
    TextField pricePerPersonTextField;
    @FXML
    Label validatePriceLabel;
    @FXML
    Button addButton;
    @FXML
    Button cancelButton;

    CateringDAO_Impl cateringDAO = new CateringDAO_Impl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWindowSize();
    }

    /**
     * Method to adjust window size according to screen size
     */
    @FXML
    private void setWindowSize(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        VBox.setMinHeight(primaryScreenBounds.getHeight()/3);
        VBox.setMinWidth(primaryScreenBounds.getHeight()/1.5);
        VBox.setMaxWidth(primaryScreenBounds.getWidth()/2);
        newCateringNameTextField.setMaxWidth(VBox.getMinWidth()-30);
        pricePerPersonTextField.setMaxWidth(VBox.getMinWidth()-30);
    }

    /**
     * A validation method to make sure that the input can be parsed to a double
     * @return
     */
    @FXML
    private double convertStringPriceToDouble(){
        double pricePerPerson = 0;
        try {
            pricePerPerson = Double.parseDouble(pricePerPersonTextField.getText());
            validatePriceLabel.setText("");
        } catch (NumberFormatException ex) {
            validatePriceLabel.setTextFill(Color.RED);
            validatePriceLabel.setText("Du skal skrive et tal");
        }
        return pricePerPerson;
    }

    /**
     * Method to add a catering option to the database
     */
    @FXML
    private void onAddButtonClick(){
        if(validateInputVariables()) {
            cateringDAO.add(new Catering(newCateringNameTextField.getText(), convertStringPriceToDouble()));
            validatePriceLabel.setTextFill(Color.GREEN);
            validatePriceLabel.setText(newCateringNameTextField.getText()+" er tilføjet til databasen");
            newCateringNameTextField.setText("");
            pricePerPersonTextField.setText("");
        }
    }

    /**
     * A simple boolean method to validate that both inputs necessary for adding to the database is filled
     * @return
     */
    private boolean validateInputVariables(){
        if(!newCateringNameTextField.getText().isBlank()&&validatePriceLabel.getText().isBlank()){
            return true;
        }
        return false;
    }

    /**
     * Closes the scene
     */
    @FXML
    private void onCancelButtonClick(){
        SceneManager.closeScene(VBox.getScene());
    }

}
