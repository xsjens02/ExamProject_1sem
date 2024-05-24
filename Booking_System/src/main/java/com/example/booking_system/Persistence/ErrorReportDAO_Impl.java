package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.ErrorReport;
import com.example.booking_system.Model.MeetingRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorReportDAO_Impl implements DAO<ErrorReport>{
    private final Connection connection;
    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    private final MeetingRoomDAO meetingRoomDAO = new MeetingRoomDAO_Impl();
    public ErrorReportDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(ErrorReport entity) {
        try {
            CallableStatement addReport = connection.prepareCall("{call add_error_report(?, ?, ?, ?)}");
            addReport.setInt(1, entity.getMeetingRoom().getRoomID());
            addReport.setInt(2, entity.getEquipment().getEquipmentID());
            addReport.setString(3, entity.getDescription());
            addReport.setBoolean(4, entity.isResolved());

            int result = addReport.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ErrorReport read(int id) {
        ErrorReport errorReport = null;
        try {
            PreparedStatement readReport = connection.prepareStatement("SELECT * FROM tblError_Report WHERE fldReportID=" + id);
            ResultSet reportData = readReport.executeQuery();
            while (reportData.next()) {
                int roomID = reportData.getInt(2);
                int equipmentID = reportData.getInt(3);
                String description = reportData.getString(4).trim();
                boolean resolved = reportData.getBoolean(5);

                errorReport = new ErrorReport(id, description, resolved);


                MeetingRoom meetingRoom = meetingRoomDAO.read(roomID);
                if (meetingRoom != null) {
                    errorReport.setMeetingRoom(meetingRoom);
                }

                Equipment equipment = equipmentDAO.read(equipmentID);
                if (equipment != null) {
                    errorReport.setEquipment(equipment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return errorReport;
    }

    @Override
    public List<ErrorReport> readAll() {
        List<ErrorReport> reportList = new ArrayList<>();
        try {
            PreparedStatement readAllReports = connection.prepareStatement("SELECT * FROM tblError_Report");
            ResultSet allReportData = readAllReports.executeQuery();
            while (allReportData.next()) {
                int reportID = allReportData.getInt(1);
                int roomID = allReportData.getInt(2);
                int equipmentID = allReportData.getInt(3);
                String description = allReportData.getString(4).trim();
                boolean resolved = allReportData.getBoolean(5);

                ErrorReport errorReport = new ErrorReport(reportID, description, resolved);

                MeetingRoom meetingRoom = meetingRoomDAO.read(roomID);
                if (meetingRoom != null) {
                    errorReport.setMeetingRoom(meetingRoom);
                }

                Equipment equipment = equipmentDAO.read(equipmentID);
                if (equipment != null) {
                    errorReport.setEquipment(equipment);
                }

                reportList.add(errorReport);
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
