package com.example.booking_system.Persistence.DAO;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface MeetingRoomDAO<T> extends InstitutionDAO<T> {
    List<T> readAllAvailableOnDate(int institutionID, Date searchDate, Time startTime, Time endTime);
    List<T> readAllAvailableInPeriod(int institutionID, List<Date> searchDates, Time startTime, Time endTime);
}
