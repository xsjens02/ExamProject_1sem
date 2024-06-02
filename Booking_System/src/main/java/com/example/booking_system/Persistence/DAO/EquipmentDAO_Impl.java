package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Persistence.DAO.DAO;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO_Impl implements DAO<Equipment> {
    private final Connection connection;
    public EquipmentDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    /**
     * add a new equipment to database
     * @param entity new equipment
     * @return true if added, false if not
     */
    @Override
    public boolean add(Equipment entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_equipment(?)}");
            cs.setString(1, entity.getEquipmentName());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * read a equipment from database
     * @param id identification of equipment
     * @return equipment
     */
    @Override
    public Equipment read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblEquipment WHERE fldEquipmentID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int equipmentID = rs.getInt(1);
                String equipmentName = rs.getString(2).trim();
                return new Equipment(equipmentID, equipmentName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * read all equipment from database
     * @return all equipment from database
     */
    @Override
    public List<Equipment> readAll() {
        List<Equipment> equipmentList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblEquipment");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int equipmentID = rs.getInt(1);
                String equipmentName = rs.getString(2).trim();
                equipmentList.add(new Equipment(equipmentID, equipmentName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!equipmentList.isEmpty()) {
            return equipmentList;
        }
        return null;
    }

    /**
     * remove a equipment from database
     * @param id identification of equipment
     * @return true if removed, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tblEquipment WHERE fldEquipmentID=" + id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Equipment entity) {
        return false;
    }
}
