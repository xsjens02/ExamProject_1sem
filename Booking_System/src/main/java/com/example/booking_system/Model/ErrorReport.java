package com.example.booking_system.Model;

public class ErrorReport {
    private int reportID;
    private int userID;
    private int roomID;
    private int equipmentID;
    private String description;
    private boolean resolved;

    public ErrorReport(int userID, int roomID, int equipmentID, String description, boolean resolved) {
        this.userID = userID;
        this.roomID = roomID;
        this.equipmentID = equipmentID;
        this.description = description;
        this.resolved = resolved;
    }

    public ErrorReport(int reportID, int userID, int roomID, int equipmentID, String description, boolean resolved) {
        this.reportID = reportID;
        this.userID = userID;
        this.roomID = roomID;
        this.equipmentID = equipmentID;
        this.description = description;
        this.resolved = resolved;
    }

    public int getReportID() {
        return reportID;
    }
    public int getUserID() {
        return userID;
    }
    public int getRoomID() {
        return roomID;
    }
    public int getEquipmentID() {
        return equipmentID;
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
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
