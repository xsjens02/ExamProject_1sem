package com.example.booking_system.Model;
import com.example.booking_system.Persistence.dbConnection;
import com.example.booking_system.Controller.LoginScreenController;

import java.sql.Connection;

public class LoginService {

    private final Connection connection;

    private final LoginScreenController loginScreenController = new LoginScreenController();

    public LoginService(){
        connection = dbConnection.getInstance().getConnection();
    }




   public String hashPassword(String password) {

       String pass = String.valueOf(password.hashCode());
       System.out.println(pass);


       return password;
   }



   public boolean validateLogin(String username, String password){

    }


}
