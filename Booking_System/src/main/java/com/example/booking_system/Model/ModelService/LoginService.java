package com.example.booking_system.Model.ModelService;
import com.example.booking_system.Controller.System.PubSub.Subject;
import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Model.Models.User;
import com.example.booking_system.Persistence.DAO.UserDAO_Impl;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.Connection;

public class LoginService {

    private final Connection connection;

    private final UserDAO_Impl userDAO = new UserDAO_Impl();

    public LoginService(){
        connection = dbConnection.getInstance().getConnection();
    }

    public String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }

    public boolean validateLogin(String username, String password){
        User user = userDAO.read(username);
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
