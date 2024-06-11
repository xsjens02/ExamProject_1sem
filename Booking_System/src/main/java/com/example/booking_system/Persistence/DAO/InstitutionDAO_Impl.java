package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Institution;
import com.example.booking_system.Model.Models.MeetingRoom;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstitutionDAO_Impl implements DAO<Institution> {
    private final Connection connection;
    private final MeetingRoomDAO_Impl meetingRoomDAO = new MeetingRoomDAO_Impl();
    public InstitutionDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    /**
     * add a new institution to database
     * @param entity new institution
     * @return true if added, false if not
     */
    @Override
    public boolean add(Institution entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_institution(?, ?, ?, ?)}");
            cs.setString(1, entity.getInstitutionName());
            cs.setTime(2, entity.getOpenTime());
            cs.setTime(3, entity.getCloseTime());
            cs.setInt(4, entity.getBookingTimeInterval());

            int result = cs.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * read an institution from database
     * @param id identification of institution
     * @return institution
     */
    @Override
    public Institution read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblInstitution WHERE fldInstitutionID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String institutionName = rs.getString(2).trim();
                Time openTime = rs.getTime(3);
                Time closeTime = rs.getTime(4);
                int bookingTimeInterval = rs.getInt(5);

                List<MeetingRoom> institutionRooms = meetingRoomDAO.readAllFromInstitution(id);
                if (institutionRooms != null) {
                    return new Institution(id, institutionName, openTime, closeTime, bookingTimeInterval, institutionRooms);
                } else {
                    return new Institution(id, institutionName, openTime, closeTime, bookingTimeInterval);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * read all institutions from database
     * @return all institutions from database
     */
    @Override
    public List<Institution> readAll() {
        List<Institution> institutionList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblInstitution");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int institutionID = rs.getInt(1);
                String institutionName = rs.getString(2).trim();
                Time openTime = rs.getTime(3);
                Time closeTime = rs.getTime(4);
                int bookingTimeInterval = rs.getInt(5);

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

    /**
     * remove institution from database
     * @param id identification of institution
     * @return true if removed, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tblInstitution WHERE fldInstitutionID=" + id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update a institution in database
     * @param entity updated institution
     * @return true if updated, false if not
     */
    @Override
    public boolean update(Institution entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call update_institution(?, ?, ?, ?)}");
            cs.setInt(1, entity.getInstitutionID());
            cs.setTime(2, entity.getOpenTime());
            cs.setTime(3, entity.getCloseTime());
            cs.setInt(4, entity.getBookingTimeInterval());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
