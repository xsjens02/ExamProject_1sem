package com.example.booking_system.Model;

import com.example.booking_system.Persistence.DAO;
import com.example.booking_system.Persistence.InstitutionDAO_Impl;

import java.util.List;

public class Institution {
    private static Institution instance;
    private static final DAO<Institution> institutionDAO = new InstitutionDAO_Impl();
    private int institutionID;
    private String institutionName;
    private double openTime;
    private double closeTime;
    private int bookingTimeInterval;
    private List<MeetingRoom> meetingRoomList;

    public Institution(int institutionID, String institutionName, double openTime, double closeTime, int bookingTimeInterval) {
        this.institutionID = institutionID;
        this.institutionName = institutionName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookingTimeInterval = bookingTimeInterval;
    }

    public Institution(int institutionID, String institutionName, double openTime, double closeTime, int bookingTimeInterval, List<MeetingRoom> meetingRoomList) {
        this.institutionID = institutionID;
        this.institutionName = institutionName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookingTimeInterval = bookingTimeInterval;
        this.meetingRoomList = meetingRoomList;
    }

    public static Institution getInstance() {
        return instance;
    }

    public static void setInstance(int institutionID) {
        instance = institutionDAO.read(institutionID);
    }

    public int getInstitutionID() {
        return institutionID;
    }
    public String getInstitutionName() {
        return institutionName;
    }
    public double getOpenTime() {
        return openTime;
    }
    public double getCloseTime() {
        return closeTime;
    }
    public int getBookingTimeInterval() {
        return bookingTimeInterval;
    }
    public List<MeetingRoom> getMeetingRoomList() {
        return meetingRoomList;
    }

    public static void setInstance(Institution instance) {
        Institution.instance = instance;
    }
    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
    public void setOpenTime(double openTime) {
        this.openTime = openTime;
    }
    public void setCloseTime(double closeTime) {
        this.closeTime = closeTime;
    }
    public void setBookingTimeInterval(int bookingTimeInterval) {
        this.bookingTimeInterval = bookingTimeInterval;
    }
    public void setMeetingRoomList(List<MeetingRoom> meetingRoomList) {
        this.meetingRoomList = meetingRoomList;
    }

    @Override
    public String toString() {
        return this.institutionName;
    }
}
