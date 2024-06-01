package com.example.booking_system.Model;

import java.util.Date;

public class Booking {
    private int bookingID;
    private String bookingTitle;
    private int userID;
    private String responsible;
    private int roomID;
    private int amountGuest;
    private boolean adhoc;
    private Date date;
    private double startTime;
    private double endTime;
    private double duration;
    private int menuID;
    private int departmentID;

    public Booking(String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, double startTime, double endTime, double duration) {
        this.bookingTitle = bookingTitle;
        this.userID = userID;
        this.responsible = responsible;
        this.roomID = roomID;
        this.adhoc = adhoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public Booking(String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, double startTime, double endTime, double duration, int menuID, int departmentID) {
        this.bookingTitle = bookingTitle;
        this.userID = userID;
        this.responsible = responsible;
        this.roomID = roomID;
        this.adhoc = adhoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.menuID = menuID;
        this.departmentID = departmentID;
    }

    public Booking(int bookingID, String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, double startTime, double endTime, double duration, int menuID, int departmentID) {
        this.bookingID = bookingID;
        this.bookingTitle = bookingTitle;
        this.userID = userID;
        this.responsible = responsible;
        this.roomID = roomID;
        this.adhoc = adhoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.menuID = menuID;
        this.departmentID = departmentID;
    }
    public Booking(double startTime, double endTime, String bookingTitle, String responsible){
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingTitle = bookingTitle;
        this.responsible = responsible;
    }

    public int getBookingID() {
        return bookingID;
    }
    public String getBookingTitle() {
        return bookingTitle;
    }
    public int getUserID() {
        return userID;
    }
    public String getResponsible() {
        return responsible;
    }
    public int getRoomID() {
        return roomID;
    }
    public int getAmountGuest() {
        return amountGuest;
    }
    public boolean isAdhoc() {
        return adhoc;
    }
    public Date getDate() {
        return date;
    }
    public double getStartTime() {
        return startTime;
    }
    public double getEndTime() {
        return endTime;
    }
    public double getDuration() {
        return duration;
    }
    public int getMenuID() {
        return menuID;
    }
    public int getDepartmentID() {
        return departmentID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public void setAmountGuest(int amountGuest) {
        this.amountGuest = amountGuest;
    }
    public void setAdhoc(boolean adhoc) {
        this.adhoc = adhoc;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return this.bookingTitle + "  -  " + this.date;
    }
}
