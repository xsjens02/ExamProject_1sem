package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDAO extends DAO<Booking> {
    List<Booking> readAllRoomBookingsByDate(int roomID, Date searchDate);
    List<Booking> readALLByDate(Date searchDate);
}
