package com.example.booking_system.ControllerService;

import com.example.booking_system.Model.MeetingRoomBooking;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class TableViewService {

    public TableViewService(){}

    public TableView populateTableView(TableView tableView){

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


//        availability.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
//            @Override
//            public TableCell<String, String> call(TableColumn<String, String> stringStringTableColumn) {
//                return new TableCell<String, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty || item == null) {
//                            setText(null);
//                            setStyle("");
//                        } else {
//                            setText(item);
//
//                            // Change background color based on some condition
//                            if (item.contains("Ledig")) {
//                                setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
//                            } else {
//                                setStyle("-fx-background-color: red; -fx-text-fill: black;");
//                            }
//                        }
//                    }
//                };
//            }
//        });

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

        ObservableList<MeetingRoomBooking> test = FXCollections.observableArrayList(
                new MeetingRoomBooking("401","Ledig","Næste: ","24-05-2024","14:00","16:00","Drik øl","Ronnie"),
                new MeetingRoomBooking("402","Optaget","Nuværende: ","24-05-2024","8:15","13:30","Projekt","Ronnie"),
                new MeetingRoomBooking("403","Ledig","Næste: ","24-05-2024","14:00","16:00","Drik øl","Ronnie")
        );
        tableView.setItems(test);

        return tableView;
    }
}
