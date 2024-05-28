package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Catering;
import com.example.booking_system.Model.Department;
import com.example.booking_system.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO_Impl implements DepartmentDAO {
    private final Connection connection;
    public DepartmentDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public boolean add(Department entity) {
        return false;
    }

    @Override
    public Department read(int id) {
        try {
            PreparedStatement readDepartment = connection.prepareStatement("SELECT * FROM tblDepartment WHERE fldDepartmentID=" + id);
            ResultSet departmentData = readDepartment.executeQuery();
            while (departmentData.next()) {
                String departmentName = departmentData.getString(2).trim();

                return new Department(id, departmentName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Department> readAll() {
        return null;
    }

    @Override
    public List<Department> readAllFromUser(User user) {
        List<Department> departmentList = new ArrayList<>();
        try {
            PreparedStatement readAllDepartmentIDs = connection.prepareStatement("SELECT * FROM get_user_departments(?)");
            readAllDepartmentIDs.setInt(1, user.getUserID());
            ResultSet allDepartmentIDs = readAllDepartmentIDs.executeQuery();
            while (allDepartmentIDs.next()) {
                int departmentID = allDepartmentIDs.getInt(1);

                departmentList.add(read(departmentID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!departmentList.isEmpty()) {
            return departmentList;
        }
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
