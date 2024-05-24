package com.example.booking_system.Model;

import java.util.List;

public class MeetingRoom {
    private int roomID;
    private String roomName;
    private int institutionID;
    private int availableSeats;
    private List<Equipment> equipmentList;
    private List<Booking> bookingList;

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

    public MeetingRoom(int roomID, String roomName, int institutionID, int availableSeats, List<Equipment> equipmentList) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.institutionID = institutionID;
        this.availableSeats = availableSeats;
        this.equipmentList = equipmentList;
    }

    public MeetingRoom(int roomID, String roomName, int institutionID, int availableSeats, List<Equipment> equipmentList, List<Booking> bookingList) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.institutionID = institutionID;
        this.availableSeats = availableSeats;
        this.equipmentList = equipmentList;
        this.bookingList = bookingList;
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
    public List<Booking> getBookingList() {
        return bookingList;
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
    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public String toString() {
        return this.roomName;
    }
}
