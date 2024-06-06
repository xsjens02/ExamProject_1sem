package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.ErrorReport;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorReportDAO_Impl implements DAO<ErrorReport> {
    private final Connection connection;
    public ErrorReportDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    /**
     * add a new error report to database
     * @param entity new error report
     * @return true if added, false if not
     */
    @Override
    public boolean add(ErrorReport entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_error_report(?, ?, ?, ?, ?)}");
            cs.setInt(1, entity.getUserID());
            cs.setInt(2, entity.getRoomID());
            cs.setInt(3, entity.getEquipmentID());
            cs.setString(4, entity.getDescription());
            cs.setBoolean(5, entity.isResolved());

            int result = cs.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * read a error report from database
     * @param id identification of error report
     * @return error report
     */
    @Override
    public ErrorReport read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblError_Report WHERE fldReportID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(2);
                int roomID = rs.getInt(3);
                int equipmentID = rs.getInt(4);
                String description = rs.getString(5).trim();
                boolean resolved = rs.getBoolean(6);

                return new ErrorReport(id, userID, roomID, equipmentID, description, resolved);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * read all error reports from database
     * @return all error reports
     */
    @Override
    public List<ErrorReport> readAll() {
        List<ErrorReport> reportList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblError_Report");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int reportID = rs.getInt(1);
                int userID = rs.getInt(2);
                int roomID = rs.getInt(3);
                int equipmentID = rs.getInt(4);
                String description = rs.getString(5).trim();
                boolean resolved = rs.getBoolean(6);

                reportList.add(new ErrorReport(reportID, userID, roomID, equipmentID, description, resolved));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!reportList.isEmpty()) {
            return reportList;
        }
        return null;
    }

    /**
     * remove an error report from database
     * @param id identification of error report
     * @return true if removed, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tblError_Report WHERE fldReportID=" + id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update a error report in database
     * @param entity updated error report
     * @return true if updated, false if not
     */
    @Override
    public boolean update(ErrorReport entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call update_error_report(?, ?)}");
            cs.setInt(1, entity.getReportID());
            cs.setBoolean(2, entity.isResolved());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
