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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import myEntities.HotelGuest;
import myEntities.Reservation;
import myEntities.Room;

/**
 * Static control class for managing reservations within the application
 *
 * @author Mavric
 */
public class ReservationManager {

    /**
     * Arraylist to store all reservation objects.
     */
    private static ArrayList<Reservation> reservationsList;
    /**
     * counter for auto numbering of reservations ID.
     */
    private static int resCounter = 1;

    /**
     * private constructor to deny the creation of any instances of this class.
     */
    private ReservationManager() {
    }

    /**
     * Method to initialised the arraylist. attempt to initialise array list
     * from file. if fail create an instance of arraylist. This method must be
     * called before the use of this static class
     *
     */
    public static void init() {
        try {
            reservationsList = loadReservations();
        } catch (IOException ex) {
            reservationsList = new ArrayList();
            resCounter = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to add a reservation into the system.
     *
     * @param guestID String id of the guest
     * @param roomID String id of the room
     * @param strDate String representation of the date. Must be in YYYY MM DD
     * HH format
     * @return return Reservation details if successfully created
     * @throws java.lang.Exception Throw Exception when guest id or room id is
     * not found in system and guest has made a reservation or is already
     * checked in. also throw exceptions when the date is not in the right
     * format
     */
    public static String addReservation(String guestID, String roomID, String strDate) throws Exception {
        HotelGuest g = GuestManager.searchGuest(guestID);
        Room r = RoomManager.searchRoom(roomID);

        //check if guest has reservation
        if (g.hasReservation()) {
            throw new Exception(g.getGuestID() + " has already make a reservation.");
        }
        if (g.isCheckedIn()) {
            throw new Exception(g.getGuestID() + " has already checked in.");
        }
        //check if room is avail
        if (!r.isVacant()) {
            throw new Exception(r.getRoomID() + " is not available for reservation.");
        }

        //convert string to date
        //string format should be "MM/dd/yyyy HH:mm" 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y M d H");
        LocalDateTime resDate;
        try {
            resDate = LocalDateTime.parse(strDate, formatter);
        } catch (Exception ex) {
            throw new Exception("Date should be in \"YYYY MM DD HH\" format");
        }
        if (resDate.isBefore(LocalDateTime.now())) {
            throw new Exception("Reservation date cannot be earlier than today");
        }
        String resID = String.format("R%04d", resCounter++);
        Reservation newRes = new Reservation(resID, g, r, resDate);
        reservationsList.add(newRes);
        return String.format("%-14s | %-8s | %-8s | %-8s | %-16s \n", "Reservation ID", "Status ", "Guest ID", "Room ID", "Date to Check In") + newRes.toString();

    }

    /**
     * Method to edit a reservation in the system. Only roomID is editable.
     *
     * @param guestID String id of guest
     * @param newRoomID String id of new room
     * @return return edited Reservation details if successfully edited
     * @throws java.lang.Exception Throw Exception when guest id is not found in
     * system or when intended room is not available
     */
    public static String editReservation(String guestID, String newRoomID) throws Exception {
        HotelGuest g = GuestManager.searchGuest(guestID);
        if (!RoomManager.isRoomAvailable(newRoomID)) {
            throw new Exception(newRoomID + " is not available to be reserved");
        }
        //retrieve rm object
        Room newRoom = RoomManager.searchRoom(newRoomID);
        Room oldRoom = g.getReservation().getRoom();

        //set old room to vacant
        oldRoom.setStatus(Room.RoomStatus.VACANT);

        //set to new room to occupied
        g.getReservation().setRoom(newRoom);

        return String.format("%-14s | %-8s | %-8s | %-16s\n", "Reservation ID", "Guest ID", "Room ID", "Date to Check In") + g.getReservation().toString();

    }

    /**
     * Method to Cancel a reservation in the system. Does not delete the
     * reservations object. only set its status to cancel. Reason for doing so
     * is not to allow non sequential reservations id in system
     *
     * @param guestID String id of the guest
     * @return return String representation of reservation. note that it is not
     * deleted. only set to cancel
     * @throws java.lang.Exception Throw Exception when guest id does not exist
     * in system.
     */
    public static String cancelReservation(String guestID) throws Exception {
        HotelGuest hg = GuestManager.searchGuest(guestID);
        if (!hg.hasReservation()) {
            throw new Exception(guestID + " does not has any resrvations");
        }

        Reservation res = hg.getReservation();
        res.setCancelled();

        return res.toString();
    }

    /**
     * Method to check the expired reservation in the system. Converts the
     * incoming string object to a local date time object and see if the date is
     * before current system time
     *
     * @param resID String id of reservation
     * @return true if the date has not expired otherwise false
     * @throws java.lang.Exception Throw Exception when reservation id cannot be
     * found in system
     */
    protected static boolean checkValidExpiry(String resID) throws Exception {
        Reservation res = searchReservation(resID);
        if (LocalDateTime.now().compareTo(res.getDateToCheckIn()) < 0) {
            res.setExpire();
            throw new Exception(resID + " to be checked in on " + res.getDateToCheckIn().toString() + " has expired.");
        } else {
            return true;
        }
    }

    /**
     * Method to get a list of reservation in the system.
     *
     * @return String representation of all reservation in the system
     */
    public static String listAllReservations() {
        String outString = String.format("%-14s | %-8s | %-8s | %-16s\n", "Reservation ID", "Guest ID", "Room ID", "Date to Check In");
        for (Reservation res : reservationsList) {
            outString += res.toString() + "\n";
        }

        return outString;
    }

    /**
     * Method to get the room ID base on the guest ID
     *
     * @param guestID String id of the guest
     * @return roomID String room id of the guest if it has been reservated
     * @throws java.lang.Exception Throw Exception
     */
    public static String getRoomFromGuestID(String guestID) throws Exception {
        HotelGuest g = GuestManager.searchGuest(guestID);

        if (!g.hasReservation()) {
            throw new Exception(guestID + " has not made any reservations");
        }

        return g.getReservation().getRoom().getRoomID();
    }

    /**
     * Method to search for a reservation in the system. Reservation ID format
     * should be start with R followed by 4 digits
     *
     * @param resID id of the reservation
     * @return String representation of the reservation
     * @throws java.lang.Exception Throw Exception when reservation is not found
     * in the application or when id is in the wrong format
     */
    private static Reservation searchReservation(String resID) throws Exception {
        if (!resID.matches("R[0-9]{4}")) {
            throw new Exception("Reservation ID should be in RXXXX format.");
        }
        for (Reservation res : reservationsList) {
            if (res.equals(resID)) {
                return res;
            }
        }
        throw new Exception(resID + " does not exist in system.");
    }

    /**
     * Method for serialisation. write arraylist into file
     */
    public static void saveReservations() {
        try {
            FileOutputStream fos = new FileOutputStream("Reservations.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeInt(resCounter);
            oos.writeObject(reservationsList);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method for serialization. reads arraylist into memory from file
     */
    private static ArrayList<Reservation> loadReservations() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("Reservations.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        resCounter = ois.readInt();
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) ois.readObject();
        ois.close();
        return reservations;
    }

}
