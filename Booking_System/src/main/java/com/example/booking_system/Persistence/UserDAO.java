package com.example.booking_system.Persistence;

import com.example.booking_system.Model.User;

public interface UserDAO extends DAO<User> {
    User readFromUsername(String username);
}
