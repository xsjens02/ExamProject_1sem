package com.example.booking_system.Persistence;

import com.example.booking_system.Model.MeetingRoom;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public interface MeetingRoomDAO extends DAO<MeetingRoom>{
    MeetingRoom read(int id, int institutionID);
    List<MeetingRoom> readAllFromInstitution(int institutionID);
    List<MeetingRoom> readAllAvailableRooms(int institutionID, Date searchDate, double startTime, double endTime);
    List<MeetingRoom> readAllAvailableRooms(int institutionID, List<Date> searchDates, double startTime, double endTime);
}
