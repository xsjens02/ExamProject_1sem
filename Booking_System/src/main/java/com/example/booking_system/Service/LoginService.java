package com.example.booking_system.Service;
import com.example.booking_system.Model.Subject;
import com.example.booking_system.Model.SystemManager;
import com.example.booking_system.Model.User;
import com.example.booking_system.Persistence.UserDAO;
import com.example.booking_system.Persistence.UserDAO_Impl;
import com.example.booking_system.Persistence.dbConnection;

import java.sql.Connection;

public class LoginService {

    private final Connection connection;

    private final UserDAO userDAO = new UserDAO_Impl();

    //private final LoginScreenController loginScreenController = new LoginScreenController();

    public LoginService(){
        connection = dbConnection.getInstance().getConnection();
    }

    public String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }

    public boolean validateLogin(String username, String password){
        User user = userDAO.readFromUsername(username);
        String hashedPassword = hashPassword(password);

        if (user != null && hashedPassword.equals(user.getPassword())){
            SystemManager.getInstance().setUser(user);
            SystemManager.getInstance().notifySubscribers(Subject.User);
            return true;

        }else {
            return false;
        }

    }
}
