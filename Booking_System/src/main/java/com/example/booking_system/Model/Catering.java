package com.example.booking_system.Model;

public class Catering {
    private int menuID;
    private String menuName;
    private double pricePerPerson;

    public Catering(String menuName, double pricePerPerson){
        this.menuName = menuName;
        this.pricePerPerson = pricePerPerson;
    }

    public Catering(int menuID, String menuName, double pricePerPerson) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.pricePerPerson = pricePerPerson;
    }

    public int getMenuID() {
        return menuID;
    }
    public String getMenuName() {
        return menuName;
    }
    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @Override
    public String toString() {
        return this.menuName;
    }
}
