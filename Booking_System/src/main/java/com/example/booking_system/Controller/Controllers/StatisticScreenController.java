package com.example.booking_system.Controller.Controllers;

import com.example.booking_system.Controller.System.Managers.SceneManager;
import com.example.booking_system.Model.Models.Booking;
import com.example.booking_system.Model.Models.Statistics;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
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
    public VBox vBox;

    /**
     *  Method for when the download button is clicked, it gets the dates from the date pickers
     *  and gets the bookings that are in that timeframe, so the CSV file can be downloaded
     * @param actionEvent
     */
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
                if (selectedDirectory.canExecute()) {
                    SceneManager.closeScene(vBox.getScene());
                    dpStartdate.setValue(null);
                    dpEnddate.setValue(null);
                }
            }
        }

    }

    /**
     * Method for saving the booking information into a csv file, and formats it correctly
     * @param file
     * @param bookings
     */
    private void saveBookingsToCSV(File file, List<Booking> bookings){
        try(FileWriter writer = new FileWriter(file)){
            writer.append("Booking ID;Booking Title;User ID;Responsible;Room ID;Ad-hoc;Date;Start Time;End Time;Duration;Attendance;Menu ID;Department ID\n");
            for (Booking booking : bookings) {
                writer.append(String.format("%d;%s;%d;%s;%d;%b;%s;%s;%s;%s;%d;%d;%d\n",
                        booking.getBookingID(),
                        specialCharacters(booking.getBookingTitle()),
                        booking.getUserID(),
                        specialCharacters(booking.getResponsible()),
                        booking.getRoomID(),
                        booking.isAdhoc(),
                        booking.getDate().toString(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getDuration(),
                        booking.getAttendance(),
                        booking.getMenuID(),
                        booking.getDepartmentID()));
            }
        }catch (IOException e){
        }
    }

    /**
     * Method that replaces special characters
     * @param data
     * @return
     */
    private String specialCharacters(String data){
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