package com.example.booking_system.Model;

public class ErrorReport {
    private int reportID;
    private MeetingRoom meetingRoom;
    private Equipment equipment;
    private String description;
    private boolean resolved;

    public ErrorReport(int reportID, String description, boolean resolved) {
        this.reportID = reportID;
        this.description = description;
        this.resolved = resolved;
    }

    public ErrorReport(int reportID, MeetingRoom meetingRoom, Equipment equipment, String description, boolean resolved) {
        this.reportID = reportID;
        this.meetingRoom = meetingRoom;
        this.equipment = equipment;
        this.description = description;
        this.resolved = resolved;
    }

    public int getReportID() {
        return reportID;
    }
    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }
    public Equipment getEquipment() {
        return equipment;
    }
    public String getDescription() {
        return description;
    }
    public boolean isResolved() {
        return resolved;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }
    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
