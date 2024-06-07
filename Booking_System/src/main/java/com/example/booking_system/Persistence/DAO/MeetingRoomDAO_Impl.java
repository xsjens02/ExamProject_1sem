package com.example.booking_system.Persistence.DAO;

import com.example.booking_system.Model.Models.Equipment;
import com.example.booking_system.Model.Models.ErrorReport;
import com.example.booking_system.Model.Models.MeetingRoom;
import com.example.booking_system.Persistence.Database.dbConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class MeetingRoomDAO_Impl implements MeetingRoomDAO<MeetingRoom> {
    private final Connection connection;
    private final DAO<Equipment> equipmentDAO = new EquipmentDAO_Impl();
    private final DAO<ErrorReport> errorReportDAO = new ErrorReportDAO_Impl();
    public MeetingRoomDAO_Impl() {
        connection = dbConnection.getInstance().getConnection();
    }

    /**
     * add a new meeting room to database
     * @param entity new meeting room
     * @return true if added, false if not
     */
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
                    CallableStatement getRoomID = connection.prepareCall("{? = call get_room_id(?, ?)}");
                    getRoomID.registerOutParameter(1, Types.INTEGER);
                    getRoomID.setString(2, entity.getRoomName());
                    getRoomID.setInt(3, entity.getInstitutionID());
                    getRoomID.execute();

                    entity.setRoomID(getRoomID.getInt(1));

                    for (Equipment equipment : entity.getEquipmentList()) {
                        CallableStatement addEquipment = connection.prepareCall("{call add_room_equipment(?, ?)}");
                        addEquipment.setInt(1, entity.getRoomID());
                        addEquipment.setInt(2, equipment.getEquipmentID());
                        addEquipment.executeUpdate();
                    }
                }
            }

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * read a meeting room from database
     * @param id identification of meeting room
     * @return meeting room
     */
    @Override
    public MeetingRoom read(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblMeeting_Room WHERE fldRoomID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String roomName = rs.getString(2).trim();
                int institutionID = rs.getInt(3);
                int availableSeats = rs.getInt(4);

                return createRoom(new MeetingRoom(id, roomName, institutionID, availableSeats));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * reads all meeting rooms from database
     * @return all meeting rooms from database
     */
    @Override
    public List<MeetingRoom> readAll() {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblMeeting_Room");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt(1);
                String roomName = rs.getString(2).trim();
                int institutionID = rs.getInt(3);
                int availableSeats = rs.getInt(4);

                roomList.add(createRoom(new MeetingRoom(roomID, roomName, institutionID, availableSeats)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!roomList.isEmpty()) {
            return roomList;
        }
        return null;
    }

    /**
     * reads all meeting rooms of an institution
     * @param institutionID identification of institution
     * @return all meeting rooms from institution
     */
    @Override
    public List<MeetingRoom> readAllFromInstitution(int institutionID) {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblMeeting_Room WHERE fldInstitutionID=" + institutionID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt(1);
                String roomName = rs.getString(2).trim();
                int availableSeats = rs.getInt(4);

                roomList.add(createRoom(new MeetingRoom(roomID, roomName, institutionID, availableSeats)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!roomList.isEmpty()) {
            return roomList;
        }
        return null;
    }

    /**
     * reads all available meeting rooms of an institution on a specific date within a specific time
     * @param institutionID identification of institution
     * @param searchDate specific date
     * @param startTime specific start time
     * @param endTime specific end time
     * @return all available meeting rooms
     */
    @Override
    public List<MeetingRoom> readAllAvailableOnDate(int institutionID, Date searchDate, Time startTime, Time endTime) {
        List<MeetingRoom> roomList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_available_roomIDs(?, ?, ?, ?)");
            ps.setInt(1, institutionID);
            ps.setDate(2, (java.sql.Date) searchDate);
            ps.setTime(3, startTime);
            ps.setTime(4, endTime);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt(1);

                roomList.add(createRoom(read(roomID)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (!roomList.isEmpty()) {
            return roomList;
        }
        return null;
    }

    /**
     * reads all available meeting rooms of an institution on specific dates within a specific time
     * @param institutionID identification of institution
     * @param searchDates specific dates
     * @param startTime specific start time
     * @param endTime specific end time
     * @return all available meeting rooms
     */
    @Override
    public List<MeetingRoom> readAllAvailableInPeriod(int institutionID, List<Date> searchDates, Time startTime, Time endTime) {
        List<MeetingRoom> availableRooms = new ArrayList<>();
        try {
            int amountDates = searchDates.size();
            Map<Integer, Integer> roomCount = new HashMap<>();
            for (java.util.Date searchDate : searchDates) {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_available_roomIDs(?, ?, ?, ?)");
                ps.setInt(1, institutionID);
                ps.setDate(2, (java.sql.Date) searchDate);
                ps.setTime(3, startTime);
                ps.setTime(4, endTime);
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


    /**
     * removes a meeting room from database
     * @param id identification of meeting room
     * @return true if deleted, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tblMeeting_Room WHERE fldRoomID=" + id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(MeetingRoom entity) {
        return false;
    }

    /**
     * assembles a MeetingRoom object by setting its equipment list and unresolved error reports
     * @param meetingRoom object to be assembled
     * @return complete MeetingRoom object
     */
    private MeetingRoom createRoom(MeetingRoom meetingRoom) {
        List<Equipment> roomEquipment = getRoomEquipment(meetingRoom.getRoomID());
        if (roomEquipment != null) {
            meetingRoom.setEquipmentList(roomEquipment);
        }
        List<ErrorReport> unresolvedRoomReports = getUnresolvedReports(meetingRoom.getRoomID());
        if (unresolvedRoomReports != null) {
            meetingRoom.setUnresolvedReports(unresolvedRoomReports);
        }
        return  meetingRoom;
    }

    /**
     * fetches list of equipment from a meeting room
     * @param roomID identification of meeting room
     * @return equipment list
     */
    private List<Equipment> getRoomEquipment(int roomID) {
        List<Equipment> roomEquipment = null;
        try {
            CallableStatement cs = connection.prepareCall("{? = call validate_room_equipment(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, roomID);
            cs.execute();

            int validation = cs.getInt(1);
            if (validation > 0) {
                roomEquipment = new ArrayList<>();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_room_equipmentIDs(?)");
                ps.setInt(1, roomID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int equipmentID = rs.getInt(1);
                    roomEquipment.add(equipmentDAO.read(equipmentID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomEquipment;
    }

    /**
     * fetches list of unresolved error reports from a meeting room
     * @param roomID identification of meeting room
     * @return unresolved error report list
     */
    private List<ErrorReport> getUnresolvedReports(int roomID) {
        List<ErrorReport> unresolvedReports = null;
        try {
            CallableStatement cs = connection.prepareCall("{? = call validate_room_error_report(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, roomID);
            cs.execute();

            int validation = cs.getInt(1);
            if (validation > 0) {
                unresolvedReports = new ArrayList<>();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM get_unresolved_room_reportIDs(?)");
                ps.setInt(1, roomID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int reportID = rs.getInt(1);
                    unresolvedReports.add(errorReportDAO.read(reportID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unresolvedReports;
    }
}
