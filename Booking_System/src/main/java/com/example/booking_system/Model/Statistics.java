package com.example.booking_system.Model;

import com.example.booking_system.Controller.StatisticScreenController;
import com.example.booking_system.Persistence.BookingDAO;
import com.example.booking_system.Persistence.BookingDAO_Impl;

import java.util.List;

public class Statistics {

    StatisticScreenController statisticController;

    BookingDAO bookingDAO = new BookingDAO_Impl();

    List<Booking> bookingList;

    public void getBookings(){
        for(MeetingRoom mr : SystemManager.getInstance().getInstitution().getMeetingRoomList()){
            List<Booking> bookings = bookingDAO.readAllBookingsInPeriod(mr.getInstitutionID(), );
            if(bookings != null){
                for(Booking booking : bookings){
                    bookingList.add(booking);
                }
            }
        }
    }



}
