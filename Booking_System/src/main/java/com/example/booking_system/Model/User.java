package com.example.booking_system.Model;

public class User {
    private int userID;
    private String userName;
    private String password;
    private int institutionID;
    private int roleID;
    private Role role;
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
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public int getInstitutionID() {
        return institutionID;
    }
    public int getRoleID() {
        return roleID;
    }
    public Enum<Role> getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void defineUserRole(int roleID){
        switch (roleID) {
            case 1: this.role = Role.GUEST; break;
            case 2: this.role = Role.JANITOR; break;
            case 3: this.role = Role.STUDENT; break;
            case 4: this.role = Role.TEACHER; break;
            case 5: this.role = Role.ADMIN; break;
        }
    }
}