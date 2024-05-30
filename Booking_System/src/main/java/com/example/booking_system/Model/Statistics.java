package com.example.booking_system.Model;

import com.example.booking_system.Controller.StatisticScreenController;
import com.example.booking_system.Persistence.BookingDAO;
import com.example.booking_system.Persistence.BookingDAO_Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statistics {

    public Statistics() {
        bookingList = new ArrayList<>();
    }

    BookingDAO bookingDAO = new BookingDAO_Impl();

    List<Booking> bookingList;

    public void getBookings(Date startDate, Date endDate){
        bookingList.clear();

        for(MeetingRoom mr : SystemManager.getInstance().getInstitution().getMeetingRoomList()){
            List<Booking> bookings = bookingDAO.readAllBookingsInPeriod(mr.getInstitutionID(), startDate, endDate);
            if(bookings != null){
                for(Booking booking : bookings){
                    boolean alreadyExists = false;
                    for(Booking existingBookings : bookingList){
                        if(existingBookings.getBookingID() == booking.getBookingID()){
                            alreadyExists = true;
                            break;
                        }
                    }
                    if(!alreadyExists){
                        bookingList.add(booking);
                    }
                }
            }
        }

        System.out.println("Total bookings retrieved: " + bookingList.size());
        for(Booking booking : bookingList){
            System.out.println(booking);
        }
    }

    public List<Booking> getBookingList(){
        return bookingList;
    }



}
