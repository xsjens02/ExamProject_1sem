package com.example.booking_system.Persistence;

import com.example.booking_system.Model.MeetingRoom;
import java.util.List;

public interface MeetingRoomDAO extends DAO<MeetingRoom>{
    List<MeetingRoom> readAllFromInstitution(int institutionID);
}
