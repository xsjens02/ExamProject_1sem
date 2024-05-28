package com.example.booking_system.Model;

import com.example.booking_system.Persistence.InstitutionDAO_Impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class Test {



    public static void main(String[] args) {

        double base = 14.5;
        int hour = (int) base;
        int minute = (int) ((base - hour)*60);

        LocalTime time = LocalTime.of(hour,minute);
        System.out.println(time);
    }
}
