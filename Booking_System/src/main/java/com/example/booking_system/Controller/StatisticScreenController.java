package com.example.booking_system.Controller;

import com.example.booking_system.Model.Booking;
import com.example.booking_system.Model.Statistics;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class StatisticScreenController {
    private final Statistics stats = new Statistics();
    public DatePicker dpStartdate;
    public DatePicker dpEnddate;

    public void onDownloadButtonClick(ActionEvent actionEvent) {
        if(dpEnddate.getValue() != null && dpStartdate.getValue() != null){
            Date startDate = Date.valueOf(dpStartdate.getValue());
            Date endDate = Date.valueOf(dpEnddate.getValue());
            stats.getBookings(startDate, endDate);
        }
        List<Booking> bookings = stats.getBookingList();
        if(!bookings.isEmpty()){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Vælg hvor du vil gemme CSV filen");
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if(selectedDirectory != null){
                File csvFile = new File(selectedDirectory, "bookings_statistics.csv");
                saveBookingsToCSV(csvFile, bookings);
            }
        }

    }

    private void saveBookingsToCSV(File file, List<Booking> bookings){
        try(FileWriter writer = new FileWriter(file)){
            writer.append("Booking ID;Booking Title;User ID;Responsible;Room ID;Ad-hoc;Date;Start Time;End Time;Duration;Menu ID;Department ID\n");
            for (Booking booking : bookings) {
                writer.append(String.format("%d;%s;%d;%s;%d;%b;%s;%.2f;%.2f;%.2f;%d;%d\n",
                        booking.getBookingID(),
                        columns(booking.getBookingTitle()),
                        booking.getUserID(),
                        columns(booking.getResponsible()),
                        booking.getRoomID(),
                        booking.isAdhoc(),
                        booking.getDate().toString(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getDuration(),
                        booking.getMenuID(),
                        booking.getDepartmentID()));
            }
        }catch (IOException e){
        }
    }


    private String columns(String data){
        String newColumns = data;

        if(data.contains("\"")){
            newColumns = data.replace("\"", "\"\"");
        }
        if(data.contains(";") || data.contains("\n")){
            newColumns = "\"" + newColumns + "\"";
        }
        return newColumns;
    }


}