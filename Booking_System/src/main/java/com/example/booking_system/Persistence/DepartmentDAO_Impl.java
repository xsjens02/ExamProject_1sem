package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Department;

import java.util.List;

public class DepartmentDAO_Impl implements DAO<Department> {
    @Override
    public boolean add(Department entity) {
        return false;
    }

    @Override
    public Department read(int id) {
        return null;
    }

    @Override
    public List<Department> readAll() {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean update(Department entity) {
        return false;
    }
}
