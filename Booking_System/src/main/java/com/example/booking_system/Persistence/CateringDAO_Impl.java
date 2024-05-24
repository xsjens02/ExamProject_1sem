package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Catering;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CateringDAO_Impl implements DAO<Catering> {
    private final Connection connection;
    public CateringDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(Catering entity) {
        try {
            CallableStatement addCatering = connection.prepareCall("{call addCatering(?,?)}");
            addCatering.setString(1, entity.getMenuName());
            addCatering.setDouble(2, entity.getPricePerPerson());

            int result = addCatering.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Catering read(int id) {
        return null;
    }

    @Override
    public List<Catering> readAll() {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean update(Catering entity) {
        return false;
    }
}
