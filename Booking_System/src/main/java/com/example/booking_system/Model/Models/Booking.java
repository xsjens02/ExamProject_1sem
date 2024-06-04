package com.example.booking_system.Model.Models;

import java.sql.Time;
import java.util.Date;

public class Booking {
    private int bookingID;
    private String bookingTitle;
    private int userID;
    private String responsible;
    private int roomID;
    private boolean adhoc;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int duration;
    private int attendance;
    private int menuID;
    private int departmentID;

    public Booking(String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, Time startTime, Time endTime, int duration, int menuID, int departmentID) {
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

    public Booking(int bookingID, String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, Time startTime, Time endTime, int duration, int menuID, int departmentID) {
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

    public Booking(String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, Time startTime, Time endTime, int duration, int attendance, int menuID, int departmentID) {
        this.bookingTitle = bookingTitle;
        this.userID = userID;
        this.responsible = responsible;
        this.roomID = roomID;
        this.adhoc = adhoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.attendance = attendance;
        this.menuID = menuID;
        this.departmentID = departmentID;
    }

    public Booking(int bookingID, String bookingTitle, int userID, String responsible, int roomID, boolean adhoc, Date date, Time startTime, Time endTime, int duration, int attendance, int menuID, int departmentID) {
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
        this.attendance = attendance;
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
    public String getResponsible() {
        return responsible;
    }
    public int getRoomID() {
        return roomID;
    }
    public int getAttendance() {
        return attendance;
    }
    public boolean isAdhoc() {
        return adhoc;
    }
    public Date getDate() {
        return date;
    }
    public Time getStartTime() {
        return startTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public int getDuration() {
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
    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    public void setAdhoc(boolean adhoc) {
        this.adhoc = adhoc;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    public void setDuration(int duration) {
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
