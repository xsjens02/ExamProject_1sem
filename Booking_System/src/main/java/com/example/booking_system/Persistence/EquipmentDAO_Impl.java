package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO_Impl implements DAO<Equipment> {
    private final Connection connection;
    public EquipmentDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(Equipment entity) {
        try {
            CallableStatement addEquipment = connection.prepareCall("{call add_equipment(?)}");
            addEquipment.setString(1, entity.getEquipmentName());

            int result = addEquipment.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Equipment read(int id) {
        try {
            PreparedStatement readEquipment = connection.prepareStatement("SELECT * FROM tblEquipment WHERE fldEquipmentID=" + id);
            ResultSet equipmentData = readEquipment.executeQuery();
            while (equipmentData.next()) {
                int equipmentID = equipmentData.getInt(1);
                String equipmentName = equipmentData.getString(2).trim();
                return new Equipment(equipmentID, equipmentName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Equipment> readAll() {
        List<Equipment> equipments = new ArrayList<>();
        try {
            PreparedStatement readAllEquipment = connection.prepareStatement("SELECT * FROM tblEquipment");
            ResultSet allEquipmentData = readAllEquipment.executeQuery();
            while (allEquipmentData.next()) {
                int equipmentID = allEquipmentData.getInt(1);
                String equipmentName = allEquipmentData.getString(2).trim();

            }
        }
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean update(Equipment entity) {
        return false;
    }
}
