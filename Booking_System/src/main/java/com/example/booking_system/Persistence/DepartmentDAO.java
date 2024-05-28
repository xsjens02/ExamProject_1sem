package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Department;
import com.example.booking_system.Model.User;

import java.util.List;

public interface DepartmentDAO extends DAO<Department> {
    List<Department> readAllFromUser(User user);
}
