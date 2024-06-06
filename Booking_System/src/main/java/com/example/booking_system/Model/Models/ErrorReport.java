package com.example.booking_system.Model.Models;

import com.example.booking_system.Persistence.DAO.EquipmentDAO_Impl;

public class ErrorReport {
    private int reportID;
    private int userID;
    private int roomID;
    private int equipmentID;
    private String description;
    private boolean resolved;
    private final EquipmentDAO_Impl equipmentDAO = new EquipmentDAO_Impl();
    private String unresolvedDescription;

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
    public String getUnresolvedDescription() {
        return unresolvedDescription;
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
    public void setUnresolvedDescription(String unresolvedDescription) {
        this.unresolvedDescription = unresolvedDescription;
    }

    public void setupUnresolvedDescription() {
        Equipment equipment = equipmentDAO.read(this.getEquipmentID());
        this.setUnresolvedDescription("Udstyr: " + equipment.getEquipmentName() + "\n\nBeskrivelse:\n" + this.getDescription());
    }

    @Override
    public String toString() {
        return "rapport nr." + this.reportID;
    }
}
