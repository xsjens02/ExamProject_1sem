package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDAO<T> extends UserDAO<T> {
    List<T> readAllForRoomOnDate(int roomID, Date date);
    List<Booking> readAllBookingsInPeriod(int roomId, Date startDate, Date endDate);
}
