package com.example.booking_system.Persistence;

import com.example.booking_system.Model.Equipment;
import com.example.booking_system.Model.ErrorReport;
import com.example.booking_system.Model.MeetingRoom;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class MeetingRoomDAO_Impl implements MeetingRoomDAO {
    private final Connection connection;
    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    private final DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();
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
                if (entity.getEquipmentList() != null) {
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

                return createMeetingRoom(new MeetingRoom(id, roomName, institutionID, availableSeats));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MeetingRoom read(int id, int institutionID) {
        try {
            PreparedStatement readRoom = connection.prepareStatement("SELECT * FROM tblMeeting_Room WHERE fldRoomID=" + id + " AND fldInstitutionID=" + institutionID);
            ResultSet roomData = readRoom.executeQuery();
            while (roomData.next()) {
                String roomName = roomData.getString(2).trim();
                int availableSeats = roomData.getInt(4);

                return createMeetingRoom(new MeetingRoom(id, roomName, institutionID, availableSeats));
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

                roomList.add(createMeetingRoom(new MeetingRoom(roomID, roomName, institutionID, availableSeats)));
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
    public List<MeetingRoom> readAllFromInstitution(int institutionID) {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement readAllRooms = connection.prepareStatement("SELECT * FROM tblMeeting_Room WHERE fldInstitutionID=" + institutionID);
            ResultSet allRoomsData = readAllRooms.executeQuery();
            while (allRoomsData.next()) {
                int roomID = allRoomsData.getInt(1);
                String roomName = allRoomsData.getString(2).trim();
                int availableSeats = allRoomsData.getInt(4);

                roomList.add(createMeetingRoom(new MeetingRoom(roomID, roomName, institutionID, availableSeats)));
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
    public List<MeetingRoom> readAllAvailableRooms(int institutionID, Date searchDate, double startTime, double endTime) {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement readAllRoomIDs = connection.prepareStatement("SELECT * FROM get_available_roomIDs(?, ?, ?)");
            readAllRoomIDs.setDate(1, (java.sql.Date) searchDate);
            readAllRoomIDs.setDouble(2, startTime);
            readAllRoomIDs.setDouble(3, endTime);
            ResultSet allRoomID = readAllRoomIDs.executeQuery();
            while (allRoomID.next()) {
                int roomID = allRoomID.getInt(1);

                roomList.add(createMeetingRoom(read(roomID, institutionID)));
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
    public List<MeetingRoom> readAllAvailableRooms(int institutionID, List<Date> searchDates, double startTime, double endTime) {
        List<MeetingRoom> availableRooms = new ArrayList<>();
        try {
            int amountDates = searchDates.size();
            Map<Integer, Integer> roomCount = new HashMap<>();
            for (java.util.Date searchDate : searchDates) {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_available_roomIDs(?, ?, ?)");    //sp namespace
                ps.setDate(1, (java.sql.Date) searchDate);
                ps.setDouble(2, startTime);
                ps.setDouble(3, endTime);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int roomID = rs.getInt(1);
                    roomCount.put(roomID, roomCount.getOrDefault(roomID, 0) +1);
                }
            }

            for (Map.Entry<Integer, Integer> entry : roomCount.entrySet()) {
                if (entry.getValue() == amountDates) {
                    availableRooms.add(read(entry.getKey()));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        if (!availableRooms.isEmpty()) {
            return availableRooms;
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

    private List<ErrorReport> getUnresolvedRoomReports(int roomID) {
        List<ErrorReport> unresolvedReports = null;
        try {
            CallableStatement validateReports = connection.prepareCall("{? = call validate_room_error_report(?)}");
            validateReports.registerOutParameter(1, Types.INTEGER);
            validateReports.setInt(2, roomID);
            validateReports.execute();

            int validation = validateReports.getInt(1);
            if (validation > 0) {
                unresolvedReports = new ArrayList<>();
                try {
                    PreparedStatement readRoomReportIDs = connection.prepareStatement("SELECT * FROM get_unresolved_room_reportIDs(?)");
                    readRoomReportIDs.setInt(1, roomID);
                    ResultSet reportIDs = readRoomReportIDs.executeQuery();
                    while (reportIDs.next()) {
                        int reportID = reportIDs.getInt(1);
                        unresolvedReports.add(errorReportDAO.read(reportID));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unresolvedReports;
    }

    private MeetingRoom createMeetingRoom(MeetingRoom meetingRoom) {
        List<Equipment> roomEquipment = getRoomEquipment(meetingRoom.getRoomID());
        if (roomEquipment != null) {
            meetingRoom.setEquipmentList(roomEquipment);
        }
        List<ErrorReport> unresolvedRoomReports = getUnresolvedRoomReports(meetingRoom.getRoomID());
        if (unresolvedRoomReports != null) {
            meetingRoom.setUnresolvedReports(unresolvedRoomReports);
        }
        return  meetingRoom;
    }
}
