package com.example.booking_system.Model.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserConstructorWithRoleTest {

    @Test
    void validateRoleEQGuest() {
        User user = new User(1);
        assertEquals(Role.GUEST, user.getRole());
    }
    @Test
    void validateRoleEQJanitor() {
        User user = new User(2);
        assertEquals(Role.JANITOR, user.getRole());
    }
    @Test
    void validateRoleEQStudent() {
        User user = new User(3);
        assertEquals(Role.STUDENT, user.getRole());
    }
    @Test
    void validateRoleEQTeacher() {
        User user = new User(4);
        assertEquals(Role.TEACHER, user.getRole());
    }
    @Test
    void validateRoleEQAdmin() {
        User user = new User(5);
        assertEquals(Role.ADMIN, user.getRole());
    }
    @Test
    void validateRoleBVALowOut() {
        User user = new User(0);
        assertNull(user.getRole());
    }
    @Test
    void validateRoleBVAHighOut() {
        User user = new User(6);
        assertNull(user.getRole());
    }
}