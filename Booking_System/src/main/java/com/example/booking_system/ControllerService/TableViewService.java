package com.example.booking_system.ControllerService;

import com.example.booking_system.Model.*;
import com.example.booking_system.Persistence.BookingDAO_Impl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TableViewService {

    private BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    private Institution institution = SystemManager.getInstance().getInstitution();

    public TableViewService() {
    }

    private LocalDate localDate = LocalDate.now();
    private LocalTime localTime = LocalTime.now();
    private int tempHour = localTime.getHour();
    private double tempMinute = localTime.getMinute();
    private double tempTime = tempHour + (tempMinute / 60);

    public TableView populateTableView(TableView tableView) {

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
        nextCurrent.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(3));
        startTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        endTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        bookingTitle.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(6));
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
                            } else {
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
                Booking nextBooking = getNextBooking(tempMeetingRoom.getDailyBookings());
                if(nextBooking.getStartTime() != 0) {
                    allMeetingRoomAndBookings.add(new MeetingRoomBooking(
                            tempMeetingRoom.getRoomName(),
                            determineAvailability(nextBooking),
                            determineNextCurrent(nextBooking),
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

    private Booking getNextBooking(List<Booking> bookingList) {

        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getStartTime() < tempTime && bookingList.get(i).getEndTime() > tempTime) {
                return bookingList.get(i);
            }
            else if(bookingList.get(i).getStartTime()>tempTime && bookingList.get(i).getEndTime()>tempTime){
                return bookingList.get(i);
            }
        }
        return new Booking(0,0,"","");
    }
    private String determineAvailability(Booking booking){
        if(booking.getStartTime()>tempTime || booking.getEndTime()<tempTime){
            return "Ledig";
        }
        return "Optaget";
    }
    private String determineNextCurrent(Booking booking){
        if(booking.getStartTime()>tempTime){
            return "Kommende: ";
        }
        return "Igangværende: ";
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
}
