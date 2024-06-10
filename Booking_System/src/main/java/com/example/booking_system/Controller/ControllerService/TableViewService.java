package com.example.booking_system.Controller.ControllerService;

import com.example.booking_system.Controller.System.Managers.SystemManager;
import com.example.booking_system.Model.Models.Booking;
import com.example.booking_system.Model.Models.Institution;
import com.example.booking_system.Model.Models.MeetingRoom;
import com.example.booking_system.Model.Models.MeetingRoomBooking;
import com.example.booking_system.Persistence.DAO.BookingDAO_Impl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class TableViewService {

    private BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    public Institution institution = SystemManager.getInstance().getInstitution();
    public TableViewService() {}
    public LocalDate localDate = LocalDate.now();
    public LocalTime localTime = LocalTime.now();
    public int tempHour = localTime.getHour();
    public Time tempTime = Time.valueOf(localTime);

    /**
     * Method for creating and populating the tableview with the correct elements
     * @param tableView - this is the tableview from the infoscreen
     * @return the tableview with populated data
     */
    public TableView populateTableView(TableView tableView) {

        tableView.getColumns().clear();
        createTableColumns(tableView);
        tableView.setItems(getAllMeetingRoomsAndBookings(institution.getInstitutionID()));
        return tableView;
    }

    /**
     * Method for creating all the various columns in the tableview and creating the color validation on the column availability
     * @param tableView
     * @return
     */
    public TableView createTableColumns(TableView tableView) {
        TableColumn<MeetingRoomBooking, String> roomName = new TableColumn<>("Mødelokale");
        roomName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomName()));
        TableColumn<MeetingRoomBooking, String> availability = new TableColumn<>("Ledig");
        availability.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAvailability()));
        TableColumn<MeetingRoomBooking, String> nextCurrent = new TableColumn<>("Næste/Nuværende");
        nextCurrent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNextCurrent()));
        TableColumn<MeetingRoomBooking, String> startTime = new TableColumn<>("Start tid");
        startTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime().toString()));
        TableColumn<MeetingRoomBooking, String> endTime = new TableColumn<>("Slut tid");
        endTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime().toString()));
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

    /**
     * Method for creating the ObservableList with MeetingRoomBooking objects displayed on the tableview
     * @param institutionID
     * @return an ObservableList with all the institutions meetingrooms and their next booking
     */
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
                            nextBooking.getStartTime(),
                            nextBooking.getEndTime(),
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

    /**
     * Selects the next booking from a list of bookings based on a specific time
     * @param bookingList
     * @param tempTime
     * @return
     */
    private Booking getNextBooking(List<Booking> bookingList, Time tempTime) {
        bookingList.sort(Comparator.comparing(Booking::getStartTime));

        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getStartTime().before(tempTime) && bookingList.get(i).getEndTime().after(tempTime) || bookingList.get(i).getStartTime().equals(tempTime) && bookingList.get(i).getEndTime().after(tempTime)) {
                return bookingList.get(i);
            }
            else if(bookingList.get(i).getStartTime().after(tempTime) && bookingList.get(i).getEndTime().after(tempTime)){
                return bookingList.get(i);
            }
        }
        return null;
    }

    /**
     * Method for creating a MeetingRoomBooking object based on the three parameters
     * @param meetingRoom
     * @param booking
     * @param timeInput
     * @return
     */
    private MeetingRoomBooking addMeetingRoomBooking (MeetingRoom meetingRoom, Booking booking, Time timeInput){
        return new MeetingRoomBooking(
                meetingRoom.getRoomName(),
                determineAvailability(booking, timeInput),
                determineNextCurrent(booking, timeInput),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getBookingTitle(),
                booking.getResponsible(),
                determineUnresolvedError(meetingRoom));
    }

    /**
     * Method for determining the availability of a meetingroom at the specific time
     * @param booking
     * @param tempTime
     * @return
     */
    private String determineAvailability(Booking booking, Time tempTime){
        if(booking.getDate().before(Date.valueOf(localDate)) || booking.getDate().equals(Date.valueOf(localDate)) && booking.getEndTime().before(tempTime)){
            return "";
        }
        else if(booking.getStartTime().after(tempTime) || booking.getEndTime().before(tempTime)){
            return "Ledig";
        }
        return "Optaget";
    }

    /**
     * Method for determining if the displayed booking is already done, if it is next or currently running
     * @param booking
     * @param tempTime
     * @return
     */
    private String determineNextCurrent(Booking booking, Time tempTime){
        if(booking.getDate().before(Date.valueOf(localDate)) || booking.getDate().equals(Date.valueOf(localDate)) && booking.getEndTime().before(tempTime)){
            return "Afholdt";
        }
        else if(booking.getStartTime().after(tempTime)){
            return "Kommende: ";
        }
        else if(booking.getStartTime().before(tempTime) && booking.getEndTime().after(tempTime) || booking.getStartTime().equals(tempTime)) {
            return "Igangværende: ";
        }
        return "Kommende: ";
    }

    /**
     * Method for creating a noBooking, used for displaying the meetingroom in the tableview when no more bookings are present for the day
     * @param meetingRoom
     * @return
     */
    private MeetingRoomBooking noBookings(MeetingRoom meetingRoom){
        return new MeetingRoomBooking(
                meetingRoom.getRoomName(),
                "Ledig",
                "Ingen bookinger resten af dagen",
                Time.valueOf("00:00:00"),
                Time.valueOf("00:00:00"),
                "",
                "",
                determineUnresolvedError(meetingRoom)
        );
    }

    /**
     * Determines if the meetingroom has an unresolvedErrorReport
     * @param meetingRoom
     * @return
     */
    private String determineUnresolvedError(MeetingRoom meetingRoom){
        if(meetingRoom.getUnresolvedReports() != null && !meetingRoom.getUnresolvedReports().isEmpty()){
            return "!";
        }
        return "";
    }

    /**
     * Search method used when searching specific dates and/or times
     * @param tableView
     * @param dateInput
     * @param timeInput
     * @return
     */
    public TableView searchBookings(TableView tableView, Date dateInput, Time timeInput){
        createTableColumns(tableView);
        tableView.setItems(getAllMeetingRoomBookingSearchResults(dateInput, timeInput));

        return tableView;
    }

    /**
     * Search method when searching the booking titles and returning a list of bookings on the day where the keyword is contained in the booking title
     * @param tableView
     * @param textInput
     * @param dateInput
     * @return
     */
    public TableView searchBookingsByText(TableView tableView, String textInput, Date dateInput){
        createTableColumns(tableView);
        tableView.setItems(searchMeetingRoomBookingsByText(textInput,dateInput));

        return tableView;
    }

    /**
     * Creating an ObservableList with the meeting rooms and bookings when searching by date and/or time
     * @param dateInput
     * @param timeInput
     * @return
     */
    public ObservableList<MeetingRoomBooking> getAllMeetingRoomBookingSearchResults(Date dateInput, Time timeInput){
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

    /**
     * Creating an ObservableList with the meeting rooms and bookings when searching by keyword
     * @param textInput
     * @param dateInput
     * @return
     */
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
