package com.example.booking_system.ControllerService.Utilities;

import com.example.booking_system.ControllerService.Managers.SystemManager;
import com.example.booking_system.ControllerService.Utilities.FormattingService;
import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.DAO.BookingDAO_Impl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TableViewService {

    private BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    public Institution institution = SystemManager.getInstance().getInstitution();
    public TableViewService() {}
    public LocalDate localDate = LocalDate.now();
    public LocalTime localTime = LocalTime.now();
    public int tempHour = localTime.getHour();
    public double tempMinute = localTime.getMinute();
    public double tempTime = tempHour + (tempMinute / 60);

    public TableView populateTableView(TableView tableView) {

        tableView.getColumns().clear();
        createTableColumns(tableView);
        tableView.setItems(getAllMeetingRoomsAndBookings(institution.getInstitutionID()));
        return tableView;
    }

    public TableView createTableColumns(TableView tableView) {
        TableColumn<MeetingRoomBooking, String> roomName = new TableColumn<>("Mødelokale");
        roomName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomName()));
        TableColumn<MeetingRoomBooking, String> availability = new TableColumn<>("Ledig");
        availability.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAvailability()));
        TableColumn<MeetingRoomBooking, String> nextCurrent = new TableColumn<>("Næste/Nuværende");
        nextCurrent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNextCurrent()));
        TableColumn<MeetingRoomBooking, String> startTime = new TableColumn<>("Start tid");
        startTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
        TableColumn<MeetingRoomBooking, String> endTime = new TableColumn<>("Slut tid");
        endTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
        TableColumn<MeetingRoomBooking, String> bookingTitle = new TableColumn<>("Møde titel");
        bookingTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingTitle()));
        TableColumn<MeetingRoomBooking, String> responsiblePerson = new TableColumn<>("Ansvarlig");
        responsiblePerson.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResponsiblePerson()));
        TableColumn<MeetingRoomBooking, String> unresolvedError = new TableColumn<>("Fejl");
        unresolvedError.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnresolvedError()));

        tableView.getColumns().addAll(roomName, availability, nextCurrent, startTime, endTime, bookingTitle, responsiblePerson, unresolvedError);

        roomName.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(3));
        availability.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(1.5));
        nextCurrent.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(4.65));
        startTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        endTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        bookingTitle.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(6.5));
        responsiblePerson.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(3));
        unresolvedError.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(1));

        availability.setCellFactory(new Callback<TableColumn<MeetingRoomBooking, String>, TableCell<MeetingRoomBooking, String>>() {
            @Override
            public TableCell<MeetingRoomBooking, String> call(TableColumn<MeetingRoomBooking, String> meetingRoomBookingStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            // Change background color based on some condition
                            if (item.contains("Ledig")) {
                                setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                            } else if (item.contains("Optaget")){
                                setStyle("-fx-background-color: red; -fx-text-fill: black;");
                            }
                        }
                    }
                };
            }
        });

        return tableView;
    }

    public ObservableList<MeetingRoomBooking> getAllMeetingRoomsAndBookings(int institutionID) {
        ObservableList<MeetingRoomBooking> allMeetingRoomAndBookings = FXCollections.observableArrayList();
        for (int i = 0; i < institution.getMeetingRoomList().size(); i++) {

            Date tempDate = Date.valueOf(localDate);
            MeetingRoom tempMeetingRoom = institution.getMeetingRoomList().get(i);
            tempMeetingRoom.setupDailyBookings(tempDate);
            if (tempMeetingRoom.getDailyBookings() != null) {
                Booking nextBooking = getNextBooking(tempMeetingRoom.getDailyBookings(), tempTime);
                if(nextBooking != null) {
                    allMeetingRoomAndBookings.add(new MeetingRoomBooking(
                            tempMeetingRoom.getRoomName(),
                            determineAvailability(nextBooking, tempTime),
                            determineNextCurrent(nextBooking, tempTime),
                            FormattingService.formatTime(nextBooking.getStartTime()),
                            FormattingService.formatTime(nextBooking.getEndTime()),
                            nextBooking.getBookingTitle(),
                            nextBooking.getResponsible(),
                            determineUnresolvedError(tempMeetingRoom)));

                }
                else{
                    allMeetingRoomAndBookings.add(noBookings(tempMeetingRoom));
                }
            } else {
                allMeetingRoomAndBookings.add(noBookings(tempMeetingRoom));
            }
        }
        return allMeetingRoomAndBookings;
    }

    private Booking getNextBooking(List<Booking> bookingList, double tempTime) {

        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getStartTime() <= tempTime && bookingList.get(i).getEndTime() > tempTime) {
                return bookingList.get(i);
            }
            else if(bookingList.get(i).getStartTime()>tempTime && bookingList.get(i).getEndTime()>tempTime){
                return bookingList.get(i);
            }
        }
        return null;
    }
    private MeetingRoomBooking addMeetingRoomBooking (MeetingRoom meetingRoom, Booking booking, double timeInput){
        return new MeetingRoomBooking(
                meetingRoom.getRoomName(),
                determineAvailability(booking, timeInput),
                determineNextCurrent(booking, timeInput),
                FormattingService.formatTime(booking.getStartTime()),
                FormattingService.formatTime(booking.getEndTime()),
                booking.getBookingTitle(),
                booking.getResponsible(),
                determineUnresolvedError(meetingRoom));
    }
    private String determineAvailability(Booking booking, double tempTime){
        if(booking.getDate().before(Date.valueOf(localDate))){
            return "";
        }
        else if(booking.getStartTime()>tempTime || booking.getEndTime()<tempTime){
            return "Ledig";
        }
        return "Optaget";
    }
    private String determineNextCurrent(Booking booking, double tempTime){
        if(booking.getDate().before(Date.valueOf(localDate))){
            return "Afholdt";
        }
        else if(booking.getStartTime()>tempTime){
            return "Kommende: ";
        }
        else if(booking.getStartTime()<tempTime && booking.getEndTime()>tempTime) {
            return "Igangværende: ";
        }
        return "Kommende";
    }
    private MeetingRoomBooking noBookings(MeetingRoom meetingRoom){
        return new MeetingRoomBooking(
                meetingRoom.getRoomName(),
                "Ledig",
                "Ingen bookinger resten af dagen",
                "",
                "",
                "",
                "",
                determineUnresolvedError(meetingRoom)
        );
    }
    private String determineUnresolvedError(MeetingRoom meetingRoom){
        if(meetingRoom.getUnresolvedReports() != null){
            return "!";
        }
        return "";
    }
    public TableView searchBookings(TableView tableView, Date dateInput, double timeInput){
        createTableColumns(tableView);
        tableView.setItems(getAllMeetingRoomBookingSearchResults(dateInput, timeInput));

        return tableView;
    }
    public TableView searchBookingsByText(TableView tableView, String textInput, Date dateInput){
        createTableColumns(tableView);
        tableView.setItems(searchMeetingRoomBookingsByText(textInput,dateInput));

        return tableView;
    }
    public ObservableList<MeetingRoomBooking> getAllMeetingRoomBookingSearchResults(Date dateInput, double timeInput){
        ObservableList<MeetingRoomBooking> allMeetingRoomBookingSearchResults = FXCollections.observableArrayList();
        for (int i = 0; i < institution.getMeetingRoomList().size(); i++) {

            MeetingRoom tempMeetingRoom = institution.getMeetingRoomList().get(i);
            tempMeetingRoom.setupDailyBookings(dateInput);
            if (tempMeetingRoom.getDailyBookings() != null) {
                Booking nextBooking = getNextBooking(tempMeetingRoom.getDailyBookings(), timeInput);
                if (nextBooking != null) {
                    allMeetingRoomBookingSearchResults.add(addMeetingRoomBooking(tempMeetingRoom, nextBooking, timeInput));
                } else {
                    allMeetingRoomBookingSearchResults.add(noBookings(tempMeetingRoom));
                    }
            }
            else {
                allMeetingRoomBookingSearchResults.add(noBookings(tempMeetingRoom));
            }
        }
        return allMeetingRoomBookingSearchResults;
    }
    public ObservableList<MeetingRoomBooking> searchMeetingRoomBookingsByText(String textInput,Date dateInput){
        ObservableList<MeetingRoomBooking> allMeetingRoomBookingsByText = FXCollections.observableArrayList();
        for (int i = 0; i < institution.getMeetingRoomList().size(); i++) {

            MeetingRoom tempMeetingRoom = institution.getMeetingRoomList().get(i);
            tempMeetingRoom.setupDailyBookings(dateInput);
            if (tempMeetingRoom.getDailyBookings() != null) {
                if(!textInput.isEmpty()){
                    for (int j = 0; j < tempMeetingRoom.getDailyBookings().size(); j++) {
                        if(tempMeetingRoom.getDailyBookings().get(j).getBookingTitle().contains(textInput)){
                            allMeetingRoomBookingsByText.add(addMeetingRoomBooking(tempMeetingRoom,tempMeetingRoom.getDailyBookings().get(j), tempTime));
                        }
                    }
                }
            }

        }
        return allMeetingRoomBookingsByText;
    }

}
