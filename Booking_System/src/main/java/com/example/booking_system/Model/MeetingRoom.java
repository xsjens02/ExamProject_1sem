package com.example.booking_system.Model;


import com.example.booking_system.Persistence.DAO.BookingDAO_Impl;
import java.util.Date;
import java.util.List;

public class MeetingRoom {
    private int roomID;
    private String roomName;
    private int institutionID;
    private int availableSeats;
    private List<Equipment> equipmentList;
    private List<ErrorReport> unresolvedReports;
    private List<Booking> dailyBookings;
    private final BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    private String roomDescription;

    public MeetingRoom(String roomName, int institutionID, int availableSeats) {
        this.roomName = roomName;
        this.institutionID = institutionID;
        this.availableSeats = availableSeats;
    }

    public MeetingRoom(int roomID, String roomName, int institutionID, int availableSeats) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.institutionID = institutionID;
        this.availableSeats = availableSeats;
    }

    public MeetingRoom(String roomName, int institutionID, int availableSeats, List<Equipment> equipmentList) {
        this.roomName = roomName;
        this.institutionID = institutionID;
        this.availableSeats = availableSeats;
        this.equipmentList = equipmentList;
    }

    public int getRoomID() {
        return roomID;
    }
    public String getRoomName() {
        return roomName;
    }
    public int getInstitutionID() {
        return institutionID;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }
    public List<ErrorReport> getUnresolvedReports() {
        return unresolvedReports;
    }
    public List<Booking> getDailyBookings() {
        return dailyBookings;
    }
    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
    public void setUnresolvedReports(List<ErrorReport> unresolvedReports) {
        this.unresolvedReports = unresolvedReports;
    }
    public void setDailyBookings(List<Booking> dailyBookings) {
        this.dailyBookings = dailyBookings;
    }
    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setupDailyBookings(Date date) {
        this.setDailyBookings(bookingDAO.readAllForRoomOnDate(this.getRoomID(), date));
    }

    public void setupDescription() {
        if (this.getRoomDescription() == null) {
            if (!this.equipmentList.isEmpty()) {
                StringBuilder equipmentDescription = new StringBuilder();
                for (Equipment equipment : equipmentList) {
                    equipmentDescription.append(equipment.toString()).append("\n");
                }
                this.setRoomDescription(this.roomName + "\n\nAntal pladser: " + this.availableSeats + "\n\nUdstyr:\n" + equipmentDescription);
            } else {
                this.setRoomDescription(this.roomName + "\n\nAntal pladser: " + this.availableSeats);
            }
        }
    }

    @Override
    public String toString() {
        return this.roomName;
    }
}
