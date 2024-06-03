package com.example.booking_system.Model.Models;

public class Equipment {
    private int equipmentID;
    private String equipmentName;

    public Equipment(int equipmentID, String equipmentName) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
    }

    public int getEquipmentID() {
        return equipmentID;
    }
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    @Override
    public String toString() {
        return this.equipmentName;
    }
}
