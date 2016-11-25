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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import myEntities.HotelGuest;
import myEntities.Reservation;
import myEntities.Room;
import myEntities.RoomServiceItem;

/**
 * This is a control class that manages all guests in the HOTEL APP. All methods
 * are implemented as static
 *
 * @author Mavric
 */
public class GuestManager implements Serializable {

    /**
     * static counter to keep track of number of guests.
     *
     */
    private static int counter = 1;
    /**
     * Collection of guests.
     *
     */
    private static ArrayList<HotelGuest> guestList = new ArrayList();

    /**
     * disallow use of constructor for guest manager.
     */
    private GuestManager() {
    }

    /**
     * Method to initialize static class guest manager.
     *
     */
    public static void init() {
        try {
            guestList = loadGuests();
            counter = guestList.size() + 1;
        } catch (IOException ex) {
            guestList = new ArrayList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to add a guest into the system. Auto generates a guestID based on
     * number of guests already in system.
     *
     * @param name Name of the Guest
     * @param creditCardNum Credit Card Details of guest
     * @return return Guest details if successfully created
     * @throws java.lang.Exception Throw Exception when the input parameters are
     * null.
     */
    public static String addGuest(String name, String creditCardNum) throws Exception {

        if (name == null || name.equals("")) {
            throw new Exception("Guest name cannot be empty.");
        }

        if (creditCardNum == null || creditCardNum.equals("")) {
            throw new Exception("Credit card number cannot be empty.");
        }

        String guestID = String.format("G%04d", counter++);
        HotelGuest guest = new HotelGuest(guestID, name, creditCardNum);
        boolean isSuccessful = guestList.add(guest);
        if (isSuccessful) {
            return String.format("%s | %s | %s", guest.getGuestID(), guest.getName(), guest.getCreditCardNumner());
        } else {
            throw new Exception("An unknown error has occured. Guest is not added to list.");
        }
    }

    /**
     * Method to update guest information. May be extended to add more details.
     * calls searchGuest to retrieve guestObject;
     *
     * @param guestID GuestID of the intended guest
     * @param newCreditCardNumber new credit card number
     * @return return Guest new details if successfully updated
     * @throws java.lang.Exception threw when guestID has error
     */
    public static String updateGuest(String guestID, String newCreditCardNumber) throws Exception {
        HotelGuest g = searchGuest(guestID);
        g.setCreditCardNumner(newCreditCardNumber);

        return g.toString();
    }

    /**
     * Method to list all guests details iteratively. This Method will list all
     * guests in the collection.
     *
     * @return a formated string containing all guests information
     */
    public static String listAllGuest() {
        String allGuestsToString;
        allGuestsToString = String.format("%-8s | %10s | %16s | %-8s| %-11s\n", "Guest ID", "Name", "Credit Card", "Room ID", "Reservation");
        if (guestList.isEmpty()) {
            return allGuestsToString + "There are no guests in the system.";
        }
        for (HotelGuest eachGuest : guestList) {
            allGuestsToString += eachGuest.toString();
        }
        return allGuestsToString;
    }

    /**
     * Method to retrieve a guest's details. This method takes in a string
     * guestID and attempts to search for the guest object with the same ID in
     * the list. Calls upon searchGuest to perform the lookup.
     *
     * @param guestID intended guest to be searched for
     * @return guest's details
     * @throws java.lang.Exception exception throw by search guest
     */
    public static String getGuestDetails(String guestID) throws Exception {
        HotelGuest g = searchGuest(guestID);
        String outString = String.format("%-8s | %10s | %16s | %-8s| %-11s\n",
                "Guest ID", "Name", "Credit Card", "Room ID", "Reservation");
        outString += g.toString();

        return outString;
    }

    /**
     * Method for walk in guest to check into room. This method will search for
     * guest based on ID and roomID from
     *
     * @param guestID String id of guest
     * @param roomID String id of room
     * @return a string to indicate the success is successful.
     * @throws java.lang.Exception throws exceptions when guest or room is not
     * found or target room is not vacant or adding reference to list fails
     */
    public static String checkIn(String guestID, String roomID) throws Exception {
        Room r = RoomManager.searchRoom(roomID);
        HotelGuest g = searchGuest(guestID);

        if (!r.isVacant()) {
            throw new Exception(roomID + " is not vacant");
        }
        boolean isSuccessful = g.checkIn(r);

        if (isSuccessful) {
            return guestID + " has checked into " + roomID;
        } else {
            throw new Exception("check in failed.");
        }
    }

    /**
     * Method for getting the list of ordered services that the guest had made.
     *
     * @param guestID string of guest ID
     * @return a list of room service item ordered by the user
     * @throws java.lang.Exception throw exception when guestID is not found or
     * when the target guest has not ordered any roomService
     */
    public static String getListOfOrderedServices(String guestID) throws Exception {
        HotelGuest g = searchGuest(guestID);

        if (g.getOrderedServices().isEmpty()) {
            throw new Exception(guestID + " has not ordered any roomService");
        }

        String str = guestID + " has ordered the following..\n";
        double amount = 0;

        for (RoomServiceItem rsi : g.getOrderedServices()) {
            str += rsi.toString() + "\n";
            amount += rsi.getCost();
        }

        str += String.format("\n Total Cost : $%.2f", amount);
        return str;
    }

    /**
     * Method for getting the guest reservation.
     *
     * @param guestID String id of target guest
     * @return String representation of reservation if any
     * @throws java.lang.Exception throws exception when the guest did not make
     * any reservation or guest does not exist
     */
    public static String getReservation(String guestID) throws Exception {
        if (!hasReservation(guestID)) {
            throw new Exception(guestID + " has not made any reservations.");
        }
        HotelGuest guest = searchGuest(guestID);

        return guest.getReservation().toString();
    }

    /**
     * Method for getting the guest to checkIn with an reservation.
     *
     * @param guestID String id of target guest
     * @return returns a to string object to show that guest has checked into
     * the room
     * @throws java.lang.Exception exceptions throw when guest ID is not found
     * or when no reservations has been made or expired
     */
    public static String checkIn(String guestID) throws Exception {
        HotelGuest guest = searchGuest(guestID);
        if (!guest.hasReservation()) {
            throw new Exception(guestID + " did not make any reservations. Please use walk in check in");
        }
        //retrieve reservation and room object
        Reservation r = guest.getReservation();
        //check if r is expired.
        if (r.getDateToCheckIn().isBefore(LocalDateTime.now().plusHours(1))) {
            r.setExpire();
            throw new Exception("Your reservation on " + r.getDateToCheckIn() + " has expired.");
        }

        Room room = r.getRoom();

        boolean isSuccessful = checkIn(guest, room);
        if (isSuccessful) {
            guest.getReservation();
            return guest.toString();
        } else {
            throw new Exception("Unable to check in guest");
        }
    }

    /**
     * Method for guest to check out.
     *
     * @param guestID String id of guest
     * @param byCreditCard Boolean to indicate payment by credit card (true) or
     * cash (false)
     * @return receipt String object to list down all costs incurred by the
     * guest
     * @throws java.lang.Exception thrown when guest is not found or when guest
     * is not checked in
     */
    public static String checkout(String guestID, boolean byCreditCard) throws Exception {
        HotelGuest g = searchGuest(guestID);

        if (!g.isCheckedIn()) {
            throw new Exception("Cannot check out due to the following reason: " + guestID + " is not checked in");
        }

        //generate receipt
        String receipt = g.getBill().generateReceipt(byCreditCard);

        g.checkOut();
        return receipt;
        //return "";
    }

    /**
     * Method to check if the guest has checked in.
     *
     * @param guestID String id of guest
     * @return boolean true for checked in false for not checked in
     * @throws java.lang.Exception throws by searchGuest method when guestID
     * does not exist in system
     */
    public static boolean isCheckedIn(String guestID) throws Exception {
        HotelGuest g = searchGuest(guestID);
        return (g.isCheckedIn());
    }

    /**
     * Method to check if the guest has Reservation.
     *
     * @param guestID String id of guest
     * @return boolean true for reservation made otherwise false
     * @throws java.lang.Exception throws by searchGuest method when guestID
     * does not exist in system
     */
    public static boolean hasReservation(String guestID) throws Exception {
        HotelGuest g = searchGuest(guestID);
        if (g.getReservation() == null) {
            return false;
        }
        return (g.getReservation().isValid());
    }

    /**
     * Method for guest to cancel reservation.
     *
     * @param guestID String id of guest
     * @return String representation of guest object
     * @throws java.lang.Exception throws by searchGuest method when guestID
     * does not exist in system or when guestid does not have any reservation
     */
    public static String cancelReservation(String guestID) throws Exception {
        HotelGuest guest = searchGuest(guestID);
        if (!guest.hasReservation()) {
            throw new Exception(guestID + " has no reservations");
        } else {
            ReservationManager.cancelReservation(guest.getReservation().getReservationID());
        }
        //result of cancellation
        return guest.toString();
    }

    /**
     * Method to check if the room is useable. The difference between this
     * method and the other overloaded method is that in this method the guest
     * does not have any reservation and therefore requires a roomID to be
     * provided before check in can be down
     *
     * @param guestID String id of guest
     * @return String representation of guest if the check in is successful
     * @throws java.lang.Exception throws exception when room is not vacant
     */
    private static boolean checkIn(HotelGuest g, Room r) throws Exception {
        if (r.isOccupied()) {
            throw new Exception(r.getRoomID() + " is occupied by " + r.getCheckIn().getGuest().getGuestID());
        }
        if (r.isUnderMaintanence()) {
            throw new Exception(r.getRoomID() + " is under maintanence ");
        }
        if (r.isReserved()) {
            Reservation res = r.getReservation();
            if (!res.getGuest().equals(g)) {
                throw new Exception(r.getRoomID() + " has been reserved by " + res.getGuest().getGuestID());
            }
        }
        return g.checkIn(r);

    }

    /**
     * Method to convert information to string
     *
     * @param guestID String id of the guest
     * @return string formatted into guest, name and room id
     * @throws java.lang.Exception throws exception when guest is not found
     */
    public static String guestRoomToString(String guestID) throws Exception {
        HotelGuest hg = searchGuest(guestID);
        if (!hg.isCheckedIn()) {
            throw new Exception("Guest is not checked in");
        }

        return String.format("%s %s has checked into %s", guestID, hg.getName(), hg.getRoom().getRoomID());
    }

    /**
     *
     * @param guestID
     * @return
     * @throws Exception
     */
    protected static HotelGuest searchGuest(String guestID) throws Exception {
        if (guestList.isEmpty()) {
            throw new Exception("No guest exists in system.");
        }
        if (!guestID.matches("G[0-9]{4}")) {
            throw new Exception(guestID + " is in the wrong format. It should be GXXXX.");
        }
        for (HotelGuest g : guestList) {
            if (g.equals(guestID)) {

                return g;
            }
        }
        throw new Exception("No such guest ID : " + guestID + " exists in system");
    }

    /**
     * Method for serialisation. write arraylist into file
     */
    public static void saveGuests() {
        try {
            FileOutputStream fos = new FileOutputStream("Guests.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(guestList);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** method for serialization 
     * reads arraylist into memory from file
     */
    private static ArrayList<HotelGuest> loadGuests() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("Guests.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<HotelGuest> guests = (ArrayList<HotelGuest>) ois.readObject();
        System.out.println(guests.size());
        ois.close();
        return guests;
    }
}
