package com.example.booking_system.Model;

public class Catering {
    private int menuID;
    private String menuName;
    private String menuDescription;
    private double pricePerPerson;

    public Catering(String menuName, double pricePerPerson){
        this.menuName = menuName;
        this.pricePerPerson = pricePerPerson;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }
    @Override
    public String toString() {
        return this.menuName;
    }
}
