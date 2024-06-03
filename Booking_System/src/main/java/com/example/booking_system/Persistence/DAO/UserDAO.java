package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.User;

import java.util.List;

public interface UserDAO<T> extends DAO<T> {
    List<T> readAllFromUser(User user);
}
