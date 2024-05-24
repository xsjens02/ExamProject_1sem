package com.example.booking_system.Model;

public class LoggedInManager {
    private static LoggedInManager instance;
    private User currentUser;

    private LoggedInManager(){
    }

    public static synchronized LoggedInManager getInstance(){
        if(instance == null){
            instance = new LoggedInManager();
        }
        return instance;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public void clearLoggedIn(){
        this.currentUser = null;
    }



}
