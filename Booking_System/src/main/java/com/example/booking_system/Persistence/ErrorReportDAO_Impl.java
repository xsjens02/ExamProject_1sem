package com.example.booking_system.Persistence;

import com.example.booking_system.Model.ErrorReport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorReportDAO_Impl implements DAO<ErrorReport>{
    private final Connection connection;
    public ErrorReportDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(ErrorReport entity) {
        try {
            CallableStatement addReport = connection.prepareCall("{call add_error_report(?, ?, ?, ?, ?)}");
            addReport.setInt(1, entity.getUserID());
            addReport.setInt(2, entity.getRoomID());
            addReport.setInt(3, entity.getEquipmentID());
            addReport.setString(4, entity.getDescription());
            addReport.setBoolean(5, entity.isResolved());

            int result = addReport.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ErrorReport read(int id) {
        try {
            PreparedStatement readReport = connection.prepareStatement("SELECT * FROM tblError_Report WHERE fldReportID=" + id);
            ResultSet reportData = readReport.executeQuery();
            while (reportData.next()) {
                int userID = reportData.getInt(2);
                int roomID = reportData.getInt(3);
                int equipmentID = reportData.getInt(4);
                String description = reportData.getString(5).trim();
                boolean resolved = reportData.getBoolean(6);

                return new ErrorReport(id, userID, roomID, equipmentID, description, resolved);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ErrorReport> readAll() {
        List<ErrorReport> reportList = new ArrayList<>();
        try {
            PreparedStatement readAllReports = connection.prepareStatement("SELECT * FROM tblError_Report");
            ResultSet allReportData = readAllReports.executeQuery();
            while (allReportData.next()) {
                int reportID = allReportData.getInt(1);
                int userID = allReportData.getInt(2);
                int roomID = allReportData.getInt(3);
                int equipmentID = allReportData.getInt(4);
                String description = allReportData.getString(5).trim();
                boolean resolved = allReportData.getBoolean(6);

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

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement removeReport = connection.prepareStatement("DELETE FROM tblError_Report WHERE fldReportID=" + id);
            int result = removeReport.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ErrorReport entity) {
        return false;
    }
}
