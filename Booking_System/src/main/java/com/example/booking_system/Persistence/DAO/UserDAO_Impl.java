package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.User;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public class UserDAO_Impl implements DAO<User> {
    private final Connection connection;

    public UserDAO_Impl(){
        connection = dbConnection.getInstance().getConnection();
    }

    @Override
    public boolean add(User entity) {
        try(CallableStatement statement = connection.prepareCall("{CALL dbo.add_user(?,?,?,?,?,?)}")) {
            statement.setString(1,entity.getUserName());
            String hashedPassword = String.valueOf(entity.getPassword().hashCode());
            statement.setString(2,hashedPassword);
            statement.setInt(3,entity.getInstitutionID());
            statement.setInt(4,entity.getRoleID());
            statement.setString(5,entity.getFirstName());
            statement.setString(6,entity.getLastName());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User read(int id) {
        return null;
    }

    public User read(String username){
        try(CallableStatement statement = connection.prepareCall("{CALL dbo.read_user(?)}")){
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int userID = resultSet.getInt("fldUserID");
                String userName = resultSet.getString("fldUsername");
                String password = resultSet.getString("fldPassword");
                int institutionID = resultSet.getInt("fldInstitutionID");
                int roleID = resultSet.getInt("fldRoleID");
                String firstName = resultSet.getString("fldFirst_Name");
                String lastName = resultSet.getString("fldLast_Name");
                return new User(userID, userName, password, institutionID, roleID, firstName,lastName);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    public boolean removeUsername(String username){
        try(CallableStatement statement = connection.prepareCall("{CALL dbo.delete_user(?)}")){
            statement.setString(1,username);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User entity) {
        try(CallableStatement statement = connection.prepareCall("{CALL dbo.update_user(?,?,?,?,?,?,?)}")){
            statement.setString(1,entity.getUserName());
            statement.setString(2,entity.getUserName());
            statement.setString(3, entity.getPassword());
            statement.setInt(4,entity.getInstitutionID());
            statement.setInt(5,entity.getRoleID());
            statement.setString(6, entity.getFirstName());
            statement.setString(7,entity.getLastName());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

