package com.example.booking_system.Model;

public class MeetingRoomBooking {

    private String roomName;
    private String availability;
    private String nextCurrent;
    private String bookingDate;
    private String startTime;
    private String endTime;
    private String bookingTitle;
    private String responsiblePerson;

    public MeetingRoomBooking(String roomName, String availability, String nextCurrent, String bookingDate, String startTime, String endTime, String bookingTitle, String responsisblePerson) {
        this.roomName = roomName;
        this.availability = availability;
        this.nextCurrent = nextCurrent;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingTitle = bookingTitle;
        this.responsiblePerson = responsisblePerson;
    }

    public void checkAvailability(){

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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
