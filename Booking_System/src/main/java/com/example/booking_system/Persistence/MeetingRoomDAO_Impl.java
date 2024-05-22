package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.MeetingRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingRoomDAO_Impl implements DAO<MeetingRoom> {
    private final Connection connection;
    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    public MeetingRoomDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }
    @Override
    public boolean add(MeetingRoom entity) {
        try {
            CallableStatement addRoom = connection.prepareCall("{call add_meeting_room(?, ?, ?)}");
            addRoom.setString(1, entity.getRoomName());
            addRoom.setInt(2, entity.getInstitutionID());
            addRoom.setInt(3, entity.getAvailableSeats());

            int result = addRoom.executeUpdate();

            if (result > 0) {
                try {
                    CallableStatement getID = connection.prepareCall("{? = call get_room_id(?, ?)}");
                    getID.registerOutParameter(1, Types.INTEGER);
                    getID.setString(2, entity.getRoomName());
                    getID.setInt(3, entity.getInstitutionID());
                    getID.execute();

                    entity.setRoomID(getID.getInt(1));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (entity.getEquipmentList() != null) {
                    for (Equipment equipment : entity.getEquipmentList()) {
                        try {
                            PreparedStatement addRoomEquipment = connection.prepareStatement("INSERT INTO tblMeeting_Room_Equipment VALUES (?, ?)");
                            addRoomEquipment.setInt(1, entity.getRoomID());
                            addRoomEquipment.setInt(2, equipment.getEquipmentID());
                            addRoomEquipment.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public MeetingRoom read(int id) {
        try {
            PreparedStatement readRoom = connection.prepareStatement("SELECT * FROM tblMeeting_Room WHERE fldRoomID=" + id);
            ResultSet roomData = readRoom.executeQuery();
            while (roomData.next()) {
                String roomName = roomData.getString(2).trim();
                int institutionID = roomData.getInt(3);
                int availableSeats = roomData.getInt(4);

                List<Equipment> roomEquipment = getRoomEquipment(id);
                if (roomEquipment != null) {
                    return new MeetingRoom(id, roomName, institutionID, availableSeats, roomEquipment);
                } else {
                    return new MeetingRoom(id, roomName, institutionID, availableSeats);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<MeetingRoom> readAll() {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement readAllRooms = connection.prepareStatement("SELECT * FROM tblMeeting_Room");
            ResultSet allRoomsData = readAllRooms.executeQuery();
            while (allRoomsData.next()) {
                int roomID = allRoomsData.getInt(1);
                String roomName = allRoomsData.getString(2).trim();
                int institutionID = allRoomsData.getInt(3);
                int availableSeats = allRoomsData.getInt(4);

                List<Equipment> roomEquipment = getRoomEquipment(roomID);
                if (roomEquipment != null) {
                    roomList.add(new MeetingRoom(roomID, roomName, institutionID, availableSeats, roomEquipment));
                } else {
                    roomList.add(new MeetingRoom(roomID, roomName, institutionID, availableSeats));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!roomList.isEmpty()) {
            return roomList;
        }
        return null;
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement removeRoom = connection.prepareStatement("DELETE FROM tblMeeting_Room WHERE fldRoomID=" + id);
            int result = removeRoom.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(MeetingRoom entity) {
        return false;
    }

    private List<Equipment> getRoomEquipment(int roomID) {
        List<Equipment> roomEquipment = null;
        try {
            CallableStatement validateEquipment = connection.prepareCall("{? = call validate_room_equipment(?)}");
            validateEquipment.registerOutParameter(1, Types.INTEGER);
            validateEquipment.setInt(2, roomID);
            validateEquipment.execute();

            int validation = validateEquipment.getInt(1);
            if (validation > 0) {
                roomEquipment = new ArrayList<>();
                try {
                    PreparedStatement readRoomEquipmentIDs = connection.prepareStatement("SELECT * FROM get_room_equipmentIDs(?)");
                    readRoomEquipmentIDs.setInt(1, roomID);
                    ResultSet equipmentIDs = readRoomEquipmentIDs.executeQuery();
                    while (equipmentIDs.next()) {
                        int equipmentID = equipmentIDs.getInt(1);
                        roomEquipment.add(equipmentDAO.read(equipmentID));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomEquipment;
    }
}
