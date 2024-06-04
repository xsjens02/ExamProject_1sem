package com.example.booking_system.Model.Models;

import java.sql.Time;
import java.util.List;

public class Institution {
    private int institutionID;
    private String institutionName;
    private Time openTime;
    private Time closeTime;
    private int bookingTimeInterval;
    private List<MeetingRoom> meetingRoomList;

    public Institution(int institutionID, String institutionName, Time openTime, Time closeTime, int bookingTimeInterval) {
        this.institutionID = institutionID;
        this.institutionName = institutionName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookingTimeInterval = bookingTimeInterval;
    }

    public Institution(int institutionID, String institutionName, Time openTime, Time closeTime, int bookingTimeInterval, List<MeetingRoom> meetingRoomList) {
        this.institutionID = institutionID;
        this.institutionName = institutionName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookingTimeInterval = bookingTimeInterval;
        this.meetingRoomList = meetingRoomList;
    }

    public int getInstitutionID() {
        return institutionID;
    }
    public String getInstitutionName() {
        return institutionName;
    }
    public Time getOpenTime() {
        return openTime;
    }
    public Time getCloseTime() {
        return closeTime;
    }
    public int getBookingTimeInterval() {
        return bookingTimeInterval;
    }
    public List<MeetingRoom> getMeetingRoomList() {
        return meetingRoomList;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }
    public void setCloseTime(Time closeTime) {
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
