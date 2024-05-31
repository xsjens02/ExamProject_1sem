package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO_Impl implements BookingDAO {
    private final Connection connection;
    public BookingDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(Booking entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_booking(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cs.setString(1, entity.getBookingTitle());
            cs.setInt(2, entity.getUserID());
            cs.setString(3, entity.getResponsible());
            cs.setInt(4, entity.getRoomID());
            cs.setBoolean(5, entity.isAdhoc());
            cs.setDate(6, (java.sql.Date) entity.getDate());
            cs.setDouble(7, entity.getStartTime());
            cs.setDouble(8, entity.getEndTime());
            cs.setDouble(9, entity.getDuration());
            cs.setInt(10, entity.getMenuID());
            cs.setInt(11, entity.getDepartmentID());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Booking read(int id) {
        try {
            PreparedStatement readBooking = connection.prepareStatement("SELECT * FROM tblBooking WHERE fldBookingID=" + id);
            ResultSet bookingData = readBooking.executeQuery();
            while (bookingData.next()) {
                String bookingTitle = bookingData.getString(2);
                int userID = bookingData.getInt(3);
                String responsible = bookingData.getString(4).trim();
                int roomID = bookingData.getInt(5);
                boolean adhoc = bookingData.getBoolean(6);
                Date date = bookingData.getDate(7);
                double startTime = bookingData.getDouble(8);
                double endTime = bookingData.getDouble(9);
                double duration = bookingData.getDouble(10);
                int menuID = bookingData.getInt(11);
                int departmentID = bookingData.getInt(12);

                return new Booking(id, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, menuID, departmentID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Booking> readAll() {
        List<Booking> bookingList = new ArrayList<>();
        try {
            PreparedStatement readAllBookings = connection.prepareStatement("SELECT * FROM tblEquipment");
            ResultSet allBookingData = readAllBookings.executeQuery();
            while (allBookingData.next()) {
                int bookingID = allBookingData.getInt(1);
                String bookingTitle = allBookingData.getString(2);
                int userID = allBookingData.getInt(3);
                String responsible = allBookingData.getString(4).trim();
                int roomID = allBookingData.getInt(5);
                boolean adhoc = allBookingData.getBoolean(6);
                Date date = allBookingData.getDate(7);
                double startTime = allBookingData.getDouble(8);
                double endTime = allBookingData.getDouble(9);
                double duration = allBookingData.getDouble(10);
                int menuID = allBookingData.getInt(11);
                int departmentID = allBookingData.getInt(12);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, menuID, departmentID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!bookingList.isEmpty()) {
            return bookingList;
        }
        return null;
    }

    @Override
    public List<Booking> readAllBookingsByDate(int roomID, Date searchDate) {
        List<Booking> bookingList = new ArrayList<>();
        try {
            PreparedStatement readAllBookings = connection.prepareStatement("SELECT * FROM get_room_booking_by_date(?, ?)");
            readAllBookings.setInt(1, roomID);
            readAllBookings.setDate(2, (java.sql.Date) searchDate);
            ResultSet allBookingData = readAllBookings.executeQuery();
            while (allBookingData.next()) {
                int bookingID = allBookingData.getInt(1);
                String bookingTitle = allBookingData.getString(2);
                int userID = allBookingData.getInt(3);
                String responsible = allBookingData.getString(4).trim();
                boolean adhoc = allBookingData.getBoolean(6);
                Date date = allBookingData.getDate(7);
                double startTime = allBookingData.getDouble(8);
                double endTime = allBookingData.getDouble(9);
                double duration = allBookingData.getDouble(10);
                int menuID = allBookingData.getInt(11);
                int departmentID = allBookingData.getInt(12);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, menuID, departmentID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!bookingList.isEmpty()) {
            return bookingList;
        }
        return null;
    }

    @Override
    public List<Booking> readAllBookingsInPeriod(int roomId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement removeBooking = connection.prepareStatement("DELETE FROM tblBooking WHERE fldBookingID=" + id);
            int result = removeBooking.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Booking entity) {
        return false;
    }
}
