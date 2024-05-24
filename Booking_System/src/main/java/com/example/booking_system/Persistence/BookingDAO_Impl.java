package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return false;
    }

    @Override
    public Booking read(int id) {
        try {
            PreparedStatement readBooking = connection.prepareStatement("SELECT * FROM tblBooking WHERE fldBookingID=" + id);
            ResultSet bookingData = readBooking.executeQuery();
            while (bookingData.next()) {
                String bookingTitle = bookingData.getString(2);
                int userID = bookingData.getInt(3);
                int roomID = bookingData.getInt(4);
                boolean adhoc = bookingData.getBoolean(5);
                Date date = bookingData.getDate(6);
                double startTime = bookingData.getDouble(7);
                double endTime = bookingData.getDouble(8);
                int menuID = bookingData.getInt(9);
                int departmentID = bookingData.getInt(10);

                return new Booking(id, bookingTitle, userID, roomID, adhoc, date, startTime, endTime, menuID, departmentID);
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
                int roomID = allBookingData.getInt(4);
                boolean adhoc = allBookingData.getBoolean(5);
                Date date = allBookingData.getDate(6);
                double startTime = allBookingData.getDouble(7);
                double endTime = allBookingData.getDouble(8);
                int menuID = allBookingData.getInt(9);
                int departmentID = allBookingData.getInt(10);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, roomID, adhoc, date, startTime, endTime, menuID, departmentID));
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
    public List<Booking> readAllRoomBookingsByDate(int roomID, Date searchDate) {
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
                boolean adhoc = allBookingData.getBoolean(5);
                Date date = allBookingData.getDate(6);
                double startTime = allBookingData.getDouble(7);
                double endTime = allBookingData.getDouble(8);
                int menuID = allBookingData.getInt(9);
                int departmentID = allBookingData.getInt(10);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, roomID, adhoc, date, startTime, endTime, menuID, departmentID));
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
