package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Department;
import com.example.booking_system.Model.Models.User;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO_Impl implements UserDAO<Department> {
    private final Connection connection;
    public DepartmentDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public boolean add(Department entity) {
        return false;
    }

    /**
     * read a department from database
     * @param id identification of department
     * @return department
     */
    @Override
    public Department read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblDepartment WHERE fldDepartmentID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String departmentName = rs.getString(2).trim();

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

    /**
     * read all departments associated with a user
     * @param user to retrieve from
     * @return all associated departments
     */
    @Override
    public List<Department> readAllFromUser(User user) {
        List<Department> departmentList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_user_departments(?)");
            ps.setInt(1, user.getUserID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int departmentID = rs.getInt(1);

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
