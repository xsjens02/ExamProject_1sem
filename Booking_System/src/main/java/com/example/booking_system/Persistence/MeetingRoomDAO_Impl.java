package com.example.booking_system.Persistence;

import com.example.booking_system.Model.MeetingRoom;

import java.util.List;

public class MeetingRoomDAO_Impl implements DAO<MeetingRoom> {
    @Override
    public boolean add(MeetingRoom entity) {
        return false;
    }

    @Override
    public MeetingRoom read(int id) {
        return null;
    }

    @Override
    public List<MeetingRoom> readAll() {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean update(MeetingRoom entity) {
        return false;
    }
}
