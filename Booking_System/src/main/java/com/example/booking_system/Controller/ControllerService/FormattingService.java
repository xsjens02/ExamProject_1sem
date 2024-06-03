package com.example.booking_system.Controller.ControllerService;

import java.time.LocalTime;

public class FormattingService {

    public static String formatTime(double timeInput){
        int hour = (int) timeInput;
        int minute = (int) ((timeInput - hour)*60);

        LocalTime time = LocalTime.of(hour,minute);
        return ""+time;
    }
    public static double formatTime(String timeInput){
        double hour = Double.parseDouble(timeInput.substring(0,2));
        double minute = Double.parseDouble(timeInput.substring(3,5));

        return hour + (minute/60);
    }
}
