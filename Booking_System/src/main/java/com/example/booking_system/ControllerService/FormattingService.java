package com.example.booking_system.ControllerService;

import java.time.LocalTime;

public class FormattingService {

    public static String formatTime(double timeInput){
        int hour = (int) timeInput;
        int minute = (int) ((timeInput - hour)*60);

        LocalTime time = LocalTime.of(hour,minute);
        return ""+time;
    }
}
