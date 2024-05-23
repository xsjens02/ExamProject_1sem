package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.Institution;
import com.example.booking_system.Model.MeetingRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstitutionDAO_Impl implements DAO<Institution>{
    private final Connection connection;
    private final MeetingRoomDAO meetingRoomDAO = new MeetingRoomDAO_Impl();
    public InstitutionDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(Institution entity) {
        try {
            CallableStatement addInstitution = connection.prepareCall("{call add_institution(?, ?, ?, ?)}");
            addInstitution.setString(1, entity.getInstitutionName());
            addInstitution.setDouble(2, entity.getOpenTime());
            addInstitution.setDouble(3, entity.getCloseTime());
            addInstitution.setInt(4, entity.getBookingTimeInterval());

            int result = addInstitution.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Institution read(int id) {
        try {
            PreparedStatement readInstitution = connection.prepareStatement("SELECT * FROM tblInsitution WHERE fldInstitutionID=" + id);
            ResultSet institutionData = readInstitution.executeQuery();
            while (institutionData.next()) {
                int institutionID = institutionData.getInt(1);
                String institutionName = institutionData.getString(2).trim();
                double openTime = institutionData.getDouble(3);
                double closeTime = institutionData.getDouble(4);
                int bookingTimeInterval = institutionData.getInt(5);

                List<MeetingRoom> institutionRooms = meetingRoomDAO.readAllFromInstitution(id);
                if (institutionRooms != null) {
                    return new Institution(institutionID, institutionName, openTime, closeTime, bookingTimeInterval, institutionRooms);
                } else {
                    return new Institution(institutionID, institutionName, openTime, closeTime, bookingTimeInterval);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Institution> readAll() {
        List<Institution> institutionList = new ArrayList<>();
        try {
            PreparedStatement readAllInstitution = connection.prepareStatement("SELECT * FROM tblInsitution");
            ResultSet allInstitutionData = readAllInstitution.executeQuery();
            while (allInstitutionData.next()) {
                int institutionID = allInstitutionData.getInt(1);
                String institutionName = allInstitutionData.getString(2).trim();
                double openTime = allInstitutionData.getDouble(3);
                double closeTime = allInstitutionData.getDouble(4);
                int bookingTimeInterval = allInstitutionData.getInt(5);

                List<MeetingRoom> institutionRooms = meetingRoomDAO.readAllFromInstitution(institutionID);
                if (institutionRooms != null) {
                    institutionList.add(new Institution(institutionID, institutionName, openTime, closeTime, bookingTimeInterval, institutionRooms));
                } else {
                    institutionList.add(new Institution(institutionID, institutionName, openTime, closeTime, bookingTimeInterval));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!institutionList.isEmpty()) {
            return institutionList;
        }
        return null;
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement removeInstitution = connection.prepareStatement("DELETE FROM tblInsitution WHERE fldInstitutionID=" + id);
            int result = removeInstitution.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Institution entity) {
        return false;
    }
}
