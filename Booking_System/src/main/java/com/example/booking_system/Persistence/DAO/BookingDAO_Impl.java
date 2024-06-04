package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Booking;
import com.example.booking_system.Model.Models.User;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO_Impl implements BookingDAO<Booking> {
    private final Connection connection;
    public BookingDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    /**
     * add a new booking to database
     * @param entity new booking
     * @return true if added, false if not
     */
    @Override
    public boolean add(Booking entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call add_booking(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cs.setString(1, entity.getBookingTitle());
            cs.setInt(2, entity.getUserID());
            cs.setString(3, entity.getResponsible());
            cs.setInt(4, entity.getRoomID());
            cs.setBoolean(5, entity.isAdhoc());
            cs.setDate(6, (java.sql.Date) entity.getDate());
            cs.setDouble(7, entity.getStartTime());
            cs.setDouble(8, entity.getEndTime());
            cs.setDouble(9, entity.getDuration());
            cs.setInt(10, entity.getAttendance());
            cs.setInt(11, entity.getMenuID());
            cs.setInt(12, entity.getDepartmentID());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * read a booking from database
     * @param id identification of booking
     * @return booking
     */
    @Override
    public Booking read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblBooking WHERE fldBookingID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String bookingTitle = rs.getString(2);
                int userID = rs.getInt(3);
                String responsible = rs.getString(4).trim();
                int roomID = rs.getInt(5);
                boolean adhoc = rs.getBoolean(6);
                Date date = rs.getDate(7);
                double startTime = rs.getDouble(8);
                double endTime = rs.getDouble(9);
                double duration = rs.getDouble(10);
                int attendance = rs.getInt(11);
                int menuID = rs.getInt(12);
                int departmentID = rs.getInt(13);

                return new Booking(id, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, attendance, menuID, departmentID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * read all bookings from database
     * @return all bookings from database
     */
    @Override
    public List<Booking> readAll() {
        List<Booking> bookingList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblEquipment");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt(1);
                String bookingTitle = rs.getString(2);
                int userID = rs.getInt(3);
                String responsible = rs.getString(4).trim();
                int roomID = rs.getInt(5);
                boolean adhoc = rs.getBoolean(6);
                Date date = rs.getDate(7);
                double startTime = rs.getDouble(8);
                double endTime = rs.getDouble(9);
                double duration = rs.getDouble(10);
                int attendance = rs.getInt(11);
                int menuID = rs.getInt(12);
                int departmentID = rs.getInt(13);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, attendance, menuID, departmentID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!bookingList.isEmpty()) {
            return bookingList;
        }
        return null;
    }

    /**
     * read all bookings associated with a user
     * @param user to retrieve from
     * @return all associated bookings
     */
    @Override
    public List<Booking> readAllFromUser(User user) {
        List<Booking> bookingList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_user_bookings(?)");
            ps.setInt(1, user.getUserID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt(1);

                bookingList.add(read(bookingID));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return bookingList;
    }

    /**
     * read all meeting room bookings on a specific date
     * @param roomID identification for meeting room
     * @param date specific date
     * @return all bookings for a meeting room on specific date
     */
    @Override
    public List<Booking> readAllForRoomOnDate(int roomID, Date date) {
        List<Booking> bookingList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_room_booking_by_date(?, ?)");
            ps.setInt(1, roomID);
            ps.setDate(2, (java.sql.Date) date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt(1);
                String bookingTitle = rs.getString(2);
                int userID = rs.getInt(3);
                String responsible = rs.getString(4).trim();
                boolean adhoc = rs.getBoolean(6);
                double startTime = rs.getDouble(8);
                double endTime = rs.getDouble(9);
                double duration = rs.getDouble(10);
                int attendance = rs.getInt(11);
                int menuID = rs.getInt(12);
                int departmentID = rs.getInt(13);

                bookingList.add(new Booking(bookingID, bookingTitle, userID, responsible, roomID, adhoc, date, startTime, endTime, duration, attendance, menuID, departmentID));
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
        List<Booking> bookingList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM dbo.get_room_bookings(?,?,?)";
            PreparedStatement getRoomBookings = connection.prepareStatement(sql);
            getRoomBookings.setInt(1, roomId);
            getRoomBookings.setDate(2, new java.sql.Date (startDate.getTime()));
            getRoomBookings.setDate(3, new java.sql.Date (endDate.getTime()));

            ResultSet allBookings = getRoomBookings.executeQuery();
            while(allBookings.next()){
                int bookingID = allBookings.getInt("fldBookingID");
                String bookingTitle = allBookings.getString("fldBooking_Title");
                int userID = allBookings.getInt("fldUserID");
                String responsible = allBookings.getString("fldResponsible").trim();
                boolean adhoc = allBookings.getBoolean("fldAdhoc");
                Date date = allBookings.getDate("fldDate");
                double startTime = allBookings.getDouble("fldStart_Time");
                double endTime = allBookings.getDouble("fldEnd_Time");
                double duration = allBookings.getDouble("fldDuration");
                int menuID = allBookings.getInt("fldMenuID");
                int departmentID = allBookings.getInt("fldDepartmentID");

                Booking booking = new Booking(bookingID, bookingTitle, userID, responsible, roomId, adhoc, date, startTime, endTime, duration, menuID, departmentID);

                if(!bookingList.contains(booking)){
                    bookingList.add(booking);
                }else {
                    System.out.println("Dupe booking" + booking);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return bookingList;
    }

    /**
     * remove a booking from a database
     * @param id identification for booking
     * @return true if removed, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tblBooking WHERE fldBookingID=" + id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update a booking in database
     * @param entity updated booking
     * @return true if updated, false if not
     */
    @Override
    public boolean update(Booking entity) {
        try {
            CallableStatement cs = connection.prepareCall("{call update_booking(?, ?, ?)}");
            cs.setInt(1, entity.getBookingID());
            cs.setString(2, entity.getBookingTitle());
            cs.setInt(3, entity.getAttendance());

            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
