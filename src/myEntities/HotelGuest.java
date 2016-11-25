/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Entity class hotel guest. extends from guest 
 *
 * @author Mavric
 */
public class HotelGuest extends Guest implements Serializable {

    private Bill bill;
    private CheckIn checkInRoom;
    private Reservation reservation;
    private ArrayList<RoomServiceItem> orderedServices;

    //constructor
    /**
     * default constructor for hotel guest.
     *
     */
    public HotelGuest() {
    }

    /**
     * Overloaded constructor for hotel guest. Calls upon the super class guest
     * constructor to instantiate fields
     *
     * @param guestID guest id
     * @param name name of guest
     * @param creditCardNumber credit card number
     */
    public HotelGuest(String guestID, String name, String creditCardNumber) {
        super(guestID, name, creditCardNumber);
        orderedServices = new ArrayList();
        bill = new Bill(this);
    }

    /**
     * method to get the bill
     *
     * @return bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * method to get the reservation
     *
     * @return reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * method to set the reservation
     *
     * @param reservation
     */
    protected void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * method to order service
     *
     * @param rsi - room service item
     */
    public void orderService(RoomServiceItem rsi) {
        orderedServices.add(rsi);
        rsi.setStatus(RoomServiceItem.ItemStatus.ORDERED);
    }

    /**
     * method to set the item status to delevered
     *
     * @param rsi - room service item
     */
    public void deliveredService(RoomServiceItem rsi) {
        for (RoomServiceItem temp : orderedServices) {
            if (temp.equals(rsi)) {
                rsi.setStatus(RoomServiceItem.ItemStatus.DELIVERED);
            }
        }
    }

    /**
     * method to check into a room. creates a check in object to hold both this
     * instance reference and the incoming room object reference does not check
     * if the room is vacant or not.
     *
     * @param r Room object to create bidirectional association
     * @return true to indicate success
     */
    public boolean checkIn(Room r) {
        checkInRoom = new CheckIn(this, r);
        return true;
    }

    /**
     * method to perform check out. reset the orderservices content and remove
     * own and room references from check in. also remove own reference from
     * reservation if it has been made
     */
    public void checkOut() {
        this.orderedServices = new ArrayList<>();
        this.checkInRoom.checkOut();
        this.checkInRoom = null;
        if (this.reservation != null) {
            this.reservation.setCheckOut();
        }

    }

    /**
     * method to get the room detail
     *
     * @return room
     */
    public Room getRoom() {
        return this.checkInRoom.getRoom();
    }

    /**
     * method to get the check in date
     *
     * @return check in date
     */
    public LocalDateTime getCheckInDate() {
        return this.checkInRoom.getDateToCheckIn();
    }

    /**
     * method to get the list of ordered item
     *
     * @return arraylist of orderedservices.
     */
    public ArrayList<RoomServiceItem> getOrderedServices() {
        return orderedServices;
    }

    /**
     * method to check if the guest is checked in
     *
     * @return true if checked in otherwise false
     */
    public boolean isCheckedIn() {
        return (checkInRoom != null);
    }

    /**
     * method to check if the guest has reservation
     *
     * @return true if has reservations reference otherwise false
     */
    public boolean hasReservation() {
        if (reservation == null) {
            return false;
        }
        return (reservation.isValid());
    }

    /**
     * method to override toString method to change the string format
     *
     * @return new string format
     */
    @Override
    public String toString() {
        // return String.format("%-8s | %10s | %16s | %-8s| %-11s\n", "Guest ID" , "Name", "Credit Card" "Room ID", "Reservation")
        String roomID, reservationID;
        if (checkInRoom != null) {
            roomID = this.checkInRoom.getRoom().getRoomID();
        } else {
            roomID = "No room";
        }

        if (reservation != null) {
            reservationID = reservation.getReservationID();
        } else {
            reservationID = "NIL";
        }
        return String.format("%-8s | %10s | %16s | %-8s| %-11s\n", this.getGuestID(), this.getName(), this.getCreditCardNumner(), roomID, reservationID);
    }

}
