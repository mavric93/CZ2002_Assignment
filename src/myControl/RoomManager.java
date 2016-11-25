/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myControl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import myEntities.HotelGuest;
import myEntities.Room;

/**
 * Static class to manage room objects within the application. The init() must
 * be called before usage of this class
 *
 * @author Mavric
 */
public class RoomManager {

    /**
     * Arraylist of rooms serialisable
     */
    private static ArrayList<Room> rooms;
    /**
     * constant field to set lowest level for floor
     */
    private static final int MINFLOOR = 2;
    /**
     * constant field to set highest level for floor
     */
    private static final int MAXFLOOR = 7;
    /**
     * constant field to set number of single room
     */
    private static final int NUMSINGLEROOM = 3;
    /**
     * constant field to set number of double rooms
     */
    private static final int NUMDOUBLEROOM = 3;
    /**
     * constant field to set number of deluxe rooms
     */
    private static final int NUMDELUXEROOM = 2;

    /**
     * private constructor to restrict the creation of instances from this
     * class.
     */
    private RoomManager() {
    }

    /**
     * Method to initialised the arraylist. attempt to initialise array list
     * from file. if fail create an instance of arraylist. This method must be
     * called before the use of this static class
     *
     */
    public static void init() {
        try {
            rooms = loadRooms();
        } catch (IOException ex) {
            rooms = new ArrayList();
            generateRooms();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to check if the room is available.
     *
     * @param roomID String id of the room
     * @return true if room is vacant otherwise false
     * @throws java.lang.Exception Throw Exception when room id does not exist
     * in the system
     */
    public static boolean isRoomAvailable(String roomID) throws Exception {
        Room room = searchRoom(roomID);
        return (room.getStatus() == Room.RoomStatus.VACANT);
    }

    /**
     * Method to update room in the system. make use of the enumerations
     * declared in this class to update the status.
     *
     * @param roomID String id of the room
     * @param rs enum roomstatus in this class
     * @throws java.lang.Exception Throw Exception
     */
    public static void updateRoomStatus(String roomID, Room.RoomStatus rs) throws Exception {
        Room r = searchRoom(roomID);
        r.setStatus(rs);
    }

    /**
     * Method to get the room detail in the system. 
     *
     * @param roomID String id of the room
     * @return String representation of the room details
     * @throws java.lang.Exception Throw Exception throws exception when room id is not found
     */
    public static String getRoomDetails(String roomID) throws Exception {
        Room r = searchRoom(roomID);
        return String.format("%-10s | %-10s | %-10s\n", "Room ID", "Room Type", "Room Status") + r.toString();
    }

    /**
     * Method to generates the room detail in the system. used to create
     * instances of room and fill up the array
     *
     */
    public static void generateRooms() {

        int index = 0;
        for (int i = MINFLOOR; i <= MAXFLOOR; i++) {
            int roomNum = 1;
            //generates Single rooms
            for (int j = 1; j <= NUMSINGLEROOM; j++) {
                String roomID = String.format("%02d-%02d", i, roomNum++);
                Room r = new Room(roomID, i, Room.RoomType.SINGLE);
                rooms.add(r);
            }
            //generates double rooms
            for (int j = 1; j <= NUMDOUBLEROOM; j++) {
                String roomID = String.format("%02d-%02d", i, roomNum++);
                Room r = new Room(roomID, i, Room.RoomType.DOUBLE);
                rooms.add(r);
            }
            //generates deluxe rooms
            for (int j = 1; j <= NUMDELUXEROOM; j++) {
                String roomID = String.format("%02d-%02d", i, roomNum++);
                Room r = new Room(roomID, i, Room.RoomType.DELUXE);
                rooms.add(r);
            }
        }
    }

    /**
     * Method to get the list of room detail in the system base on input
     * category. Currently supports sorting by floor, room type and room status
     *
     * @param category enum declared in Room.SortBy
     * @return list of room details sorted by category
     */
    public static String listRooms(SortBy category) {
        ArrayList[] sorted;
        String toString = "";
        switch (category) {
            case FLOOR:
                int floorNum = MINFLOOR;
                sorted = sortedByFloor();
                for (ArrayList<Room> floor : sorted) {
                    toString += "Floor " + floorNum++ + " :\n";
                    toString += "\t" + String.format("%-10s | %-10s | %-10s\n", "Room ID", "Room Type", "Room Status");
                    for (Room r : floor) {
                        toString += "\t" + r.toString() + "\n";
                    }
                }
                break;
            case ROOMTYPE:
                Room.RoomType[] roomTypes = Room.RoomType.values();
                for (Room.RoomType rt : roomTypes) {
                    toString += rt.name() + " :\n";
                    toString += "\t" + String.format("%-10s | %-10s\n", "Room ID", "Room Status");
                    for (Room r : rooms) {
                        if (r.getType() == rt) {
                            toString += "\t" + String.format("%-10s | %-10s\n", r.getRoomID(), r.getStatus().name());
                        }
                    }
                }
                break;
            case ROOMSTATUS:
                Room.RoomStatus[] roomStatus = Room.RoomStatus.values();
                for (Room.RoomStatus rs : roomStatus) {
                    toString += rs.name() + " :\n";
                    toString += "\t" + String.format("%-10s | %-10s\n", "Room ID", "Room Type", "Room Type");
                    for (Room r : rooms) {
                        if (r.getStatus() == rs) {
                            toString += "\t" + String.format("%-10s | %-10s\n", r.getRoomID(), r.getType().name());
                        }
                    }
                }
                break;
            case SUMMARY:
                Room.RoomStatus[] roomStatus2 = Room.RoomStatus.values();
                int numVacant = 0;
                for (Room.RoomStatus rs : roomStatus2) {
                    if (rs == Room.RoomStatus.VACANT) {
                        toString += rs.name() + " :\n";
                        for (Room r : rooms) {
                            if (r.getStatus() == Room.RoomStatus.VACANT) {
                                numVacant++;
                            }
                        }
                        toString += "There are " + numVacant + "/" + rooms.size() + " vacant rooms\n";
                    } else {
                        toString += rs.name() + " :\n";
                        toString += "\t" + String.format("%-10s | %-10s\n", "Room ID", "Room Type", "Room Type");
                        for (Room r : rooms) {
                            if (r.getStatus() == rs) {
                                toString += "\t" + String.format("%-10s | %-10s\n", r.getRoomID(), r.getType().name());
                            }
                        }
                    }
                }
                break;
            default:
                toString = "Unknown option.";
        }
        return toString;
    }

    /** enumeration to do sorting types.
     *
     */
    public static enum SortBy {

        /**
         * sort by floor ascending
         */
        FLOOR,

        /**
         * sort by roomtype VACANT TO OCCUPIED TO RESERVED TO MAINT
         */
        ROOMTYPE,

        /**
         * sort by room status, SINGLE DOUBLE DELUXE
         */
        ROOMSTATUS,

        /**
         * similar to status but non verbose for vacant
         */
        SUMMARY;
    }

    /**
     * Method to get a room detail in the system base on roomID.
     * This method is set to protected. only classes in the control package can call it
     * @param roomID String id of the room
     * @return room object
     * @throws java.lang.Exception Throw Exception
     */
    protected static Room searchRoom(String roomID) throws Exception {
        if (roomID == null || roomID.equalsIgnoreCase("")) {
            throw new Exception("RoomID cannot be empty");
        }
        for (Room r : rooms) {
            if (r.equals(roomID)) {
                return r;
            }
        }
        throw new Exception("No such room ID : " + roomID);
    }

    /**
     * Method to use to sort the room base on the floor
     *
     * @return list of room base on floor
     */
    private static ArrayList[] sortedByFloor() {
        //create an array of arrylists
        ArrayList<Room> floors[] = new ArrayList[MAXFLOOR - MINFLOOR + 1];

        //to initialise each arraylist in the array of arraylists
        for (int i = 0; i < floors.length; i++) {
            floors[i] = new ArrayList();
        }

        for (Room r : rooms) {
            floors[r.getFloor() - MINFLOOR].add(r);
        }
        return floors;
    }

    /**
     * Method for serialisation. write arraylist into file
     */
    public static void saveRooms() {
        try {
            FileOutputStream fos = new FileOutputStream("Rooms.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(rooms);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method for serialization. reads arraylist into memory from file
     */
    private static ArrayList<Room> loadRooms() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("Rooms.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<Room> rooms = (ArrayList<Room>) ois.readObject();
        ois.close();
        return rooms;
    }

}
