package com.example.booking_system.Persistence.DAO;

import java.sql.Date;
import java.util.List;

public interface MeetingRoomDAO<T> extends InstitutionDAO<T> {
    List<T> readAllAvailableOnDate(int institutionID, Date searchDate, double startTime, double endTime);
    List<T> readAllAvailableWithinDates(int institutionID, List<Date> searchDates, double startTime, double endTime);
}
