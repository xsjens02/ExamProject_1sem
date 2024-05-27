package com.example.booking_system.ControllerService;

import com.example.booking_system.Model.Institution;
import com.example.booking_system.Model.MeetingRoom;
import com.example.booking_system.Model.MeetingRoomBooking;
import com.example.booking_system.Persistence.BookingDAO_Impl;
import com.example.booking_system.Persistence.InstitutionDAO_Impl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Date;

public class TableViewService {

    private BookingDAO_Impl bookingDAO = new BookingDAO_Impl();
    private InstitutionDAO_Impl institutionDAO = new InstitutionDAO_Impl();
    private Institution institution = institutionDAO.read(1);

    public TableViewService(){}

    public TableView populateTableView(TableView tableView){

        createTableColumns(tableView);
        tableView.setItems(getAllMeetingRoomsAndBookings(1));
        return tableView;
    }
    public TableView createTableColumns(TableView tableView){
        TableColumn<MeetingRoomBooking, String> roomName = new TableColumn<>("Mødelokale");
        roomName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomName()));
        TableColumn<MeetingRoomBooking, String> availability = new TableColumn<>("Ledig");
        availability.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getAvailability()));
        TableColumn<MeetingRoomBooking, String> nextCurrent = new TableColumn<>("Næste/Nuværende");
        nextCurrent.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getNextCurrent()));
        TableColumn<MeetingRoomBooking, String> bookingDate = new TableColumn<>("Dato");
        bookingDate.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getBookingDate()));
        TableColumn<MeetingRoomBooking, String> startTime = new TableColumn<>("Start tid");
        startTime.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getStartTime()));
        TableColumn<MeetingRoomBooking, String> endTime = new TableColumn<>("Slut tid");
        endTime.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getEndTime()));
        TableColumn<MeetingRoomBooking, String> bookingTitle = new TableColumn<>("Møde titel");
        bookingTitle.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getBookingTitle()));
        TableColumn<MeetingRoomBooking, String> responsiblePerson = new TableColumn<>("Ansvarlig");
        responsiblePerson.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getResponsiblePerson()));

        tableView.getColumns().addAll(roomName, availability, nextCurrent, bookingDate, startTime, endTime, bookingTitle, responsiblePerson);

        roomName.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(4));
        availability.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        nextCurrent.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(3));
        bookingDate.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        startTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        endTime.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(2));
        bookingTitle.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(6));
        responsiblePerson.minWidthProperty().bind(tableView.widthProperty().divide(24).multiply(3));

        availability.setCellFactory(new Callback<TableColumn<MeetingRoomBooking, String>, TableCell<MeetingRoomBooking, String>>() {
            @Override
            public TableCell<MeetingRoomBooking, String> call(TableColumn<MeetingRoomBooking, String> meetingRoomBookingStringTableColumn) {
                return new TableCell<>(){
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
    public ObservableList<MeetingRoomBooking> getAllMeetingRoomsAndBookings(int institutionID){
        ObservableList<MeetingRoomBooking> allMeetingRoomAndBookings = FXCollections.observableArrayList();

//                new MeetingRoomBooking("401","Ledig","Næste: ","24-05-2024","14:00","16:00","Drik øl","Ronnie"),
//                new MeetingRoomBooking("402","Optaget","Nuværende: ","24-05-2024","8:15","13:30","Projekt","Ronnie"),
//                new MeetingRoomBooking("403","Ledig","Næste: ","24-05-2024","14:00","16:00","Drik øl","Ronnie")

        for (int i = 0; i < institution.getMeetingRoomList().size(); i++) {
            Date tempDate = new Date(2024-05-22);
            MeetingRoom temp = institution.getMeetingRoomList().get(i);
            allMeetingRoomAndBookings.add(new MeetingRoomBooking(
                    institution.getMeetingRoomList().get(i).getRoomName(),
                    "Ledig",
                    "Kommende: ",
                    "2024-05-22",//bookingDAO.readAllRoomBookingsByDate(temp.getRoomID(),tempDate).get(1).getDate().toString(),
                    "10:00",
                    "12:00",
                    "Get SP from Simon",//bookingDAO.readAllRoomBookingsByDate(temp.getRoomID(),tempDate).get(1).getBookingTitle(),
                    "Frank"));
        }

        return allMeetingRoomAndBookings;
    }
}
