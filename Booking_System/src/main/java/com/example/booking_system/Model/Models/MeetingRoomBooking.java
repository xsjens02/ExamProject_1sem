package com.example.booking_system.Model.Models;

import java.sql.Time;

public class MeetingRoomBooking {

    private String roomName;
    private String availability;
    private String nextCurrent;
    private Time startTime;
    private Time endTime;
    private String bookingTitle;
    private String responsiblePerson;
    private String unresolvedError;

    public MeetingRoomBooking(String roomName, String availability, String nextCurrent, Time startTime, Time endTime, String bookingTitle, String responsiblePerson, String unresolvedError) {
        this.roomName = roomName;
        this.availability = availability;
        this.nextCurrent = nextCurrent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingTitle = bookingTitle;
        this.responsiblePerson = responsiblePerson;
        this.unresolvedError = unresolvedError;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getNextCurrent() {
        return nextCurrent;
    }

    public void setNextCurrent(String nextCurrent) {
        this.nextCurrent = nextCurrent;
    }
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getBookingTitle() {
        return bookingTitle;
    }

    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    public String getUnresolvedError() {
        return unresolvedError;
    }
}
