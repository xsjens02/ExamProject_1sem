package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Catering;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CateringDAO_Impl implements InstitutionDAO<Catering> {
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
        try {
            PreparedStatement readCatering = connection.prepareStatement("SELECT * FROM tblCatering WHERE fldMenuID=" + id);
            ResultSet cateringData = readCatering.executeQuery();
            while (cateringData.next()) {
                String menuName = cateringData.getString(2).trim();
                double pricePerPerson = cateringData.getDouble(3);

                return new Catering(id, menuName, pricePerPerson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Catering> readAll() {
        return null;
    }

    /**
     * read all catering from an institution
     * @param institutionID identificaton of institution
     * @return all catering from institution
     */
    @Override
    public List<Catering> readAllFromInstitution(int institutionID) {
        List<Catering> cateringList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_institution_cateringIDs(?)");
            ps.setInt(1, institutionID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int menuID = rs.getInt(1);

                cateringList.add(read(menuID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!cateringList.isEmpty()) {
            return cateringList;
        }
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
