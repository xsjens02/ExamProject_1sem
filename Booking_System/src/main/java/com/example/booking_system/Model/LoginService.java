package com.example.booking_system.Model;
import com.example.booking_system.Persistence.LoginDAO_Impl;
import com.example.booking_system.Persistence.dbConnection;
import com.example.booking_system.Controller.LoginScreenController;

import java.sql.Connection;

public class LoginService {

    private final Connection connection;

    private final LoginDAO_Impl loginDAO = new LoginDAO_Impl();

    //private final LoginScreenController loginScreenController = new LoginScreenController();

    public LoginService(){
        connection = dbConnection.getInstance().getConnection();
    }

   public String hashPassword(String password) {
        return String.valueOf(password.hashCode());
   }

   public boolean validateLogin(String username, String password){
       User user = loginDAO.readUsername(username);
       String hashedPassword = hashPassword(password);

        if (user != null && hashedPassword.equals(user.getPassword())){
            System.out.println("True!");
            return hashedPassword.equals(user.getPassword());

        }else {
            System.out.println("False!");
            return false;
        }

   }
}
