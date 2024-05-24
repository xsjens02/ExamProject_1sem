package com.example.booking_system.Model;

public class User {
    private int userID;
    private String userName;
    private String password;
    private int institutionID;
    private int roleID;
    private Enum<Role> role;
    private String firstName;
    private String lastName;



    public User(int userID, String userName, String password, int institutionID, int roleID, String firstName, String lastName) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.institutionID = institutionID;
        this.roleID = roleID;
        defineUserRole(roleID);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Enum<Role> getRole() {
        return role;
    }

    public void setRole(Enum<Role> role) {
        this.role = role;
    }

    private void defineUserRole(int roleID){
       if(roleID == 1){
           this.role = Role.GUEST;
       } else if (roleID == 2) {
           this.role = Role.STUDENT;
       } else if (roleID == 3) {
           this.role = Role.TEACHER;
       } else if (roleID == 4) {
           this.role = Role.ADMIN;
       } else if (roleID == 5) {
           this.role = Role.JANITOR;
       }
    }


}