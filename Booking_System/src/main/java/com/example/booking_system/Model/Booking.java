package com.example.booking_system.Model;

import java.util.Date;

public class Booking {
    private int bookingID;
    private String bookingTitle;
    private int userID;
    private int roomID;
    private boolean adhoc;
    private Date date;
    private double startTime;
    private double endTime;
    private int menuID;
    private int departmentID;

    public Booking(int bookingID, String bookingTitle, int userID, int roomID, boolean adhoc, Date date, double startTime, double endTime, int menuID, int departmentID) {
        this.bookingID = bookingID;
        this.bookingTitle = bookingTitle;
        this.userID = userID;
        this.roomID = roomID;
        this.adhoc = adhoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.menuID = menuID;
        this.departmentID = departmentID;
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
    public int getRoomID() {
        return roomID;
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
    public void setRoomID(int roomID) {
        this.roomID = roomID;
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
    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
}
