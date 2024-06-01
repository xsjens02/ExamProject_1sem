package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Booking;
import com.example.booking_system.Model.User;

import java.util.Date;
import java.util.List;

public interface BookingDAO extends DAO<Booking> {
    List<Booking> readAllFromUser(User user);
    List<Booking> readAllBookingsByDate(int roomID, Date searchDate);
    List<Booking> readAllBookingsInPeriod(int roomId, Date startDate, Date endDate);
}
