/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/** Entity class for reservations. maintain references to guest and room
 *
 * @author Mavric
 */
public class Reservation implements Serializable {

    private final String reservationID;
    private Room room;
    private HotelGuest guest;
    private LocalDateTime dateToCheckIn;
    private ReservationStatus reservationStatus;

    //constructor overloading 
    /**
     * Constructor to create reservation object
     *
     * @param reservationID id for this instance
     * @param guest reference to guest object
     * @param room reference to room object
     * @param dateCheckIn date to check in
     */
    public Reservation(String reservationID, HotelGuest guest, Room room, LocalDateTime dateCheckIn) {
        //store ID
        this.reservationID = reservationID;
        //set room to this instance
        this.room = room;
        //copy this instance to room
        this.room.setReservation(this);
        //set guest to this instance
        this.guest = guest;
        //cop this instance to room
        this.guest.setReservation(this);

        //date
        this.dateToCheckIn = dateCheckIn;
        this.reservationStatus = Reservation.ReservationStatus.CONFIRMED;
    }

    /**
     * method to get reservation ID.
     *
     * @return reservationID
     */
    public String getReservationID() {
        return reservationID;
    }

    /**
     * method get room.
     *
     * @return room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * method set room.
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * method get guest.
     *
     * @return guest
     */
    public HotelGuest getGuest() {
        return guest;
    }

    /**
     * method set Guest.
     *
     * @param guest
     */
    public void setGuest(HotelGuest guest) {
        this.guest = guest;
    }

    /**
     * method to get dateToCheckIn.
     *
     * @return dateToCheckIn
     */
    public LocalDateTime getDateToCheckIn() {
        return dateToCheckIn;
    }

    /**
     * method set date to check in.
     *
     * @param dateToCheckIn
     */
    public void setDateToCheckIn(LocalDateTime dateToCheckIn) {
        this.dateToCheckIn = dateToCheckIn;
    }

    /**
     * method check if the ro date expire.
     *
     * @return true or false
     */
    public boolean checkExpire() {
        return LocalDateTime.now().compareTo(this.dateToCheckIn) < 0;
    }

    /**
     * method set the reservation as expire.
     */
    public void setExpire() {
        this.guest.setReservation(null);
        this.room.setReservation(null);
        this.reservationStatus = ReservationStatus.EXPIRED;
    }

    /**
     * method to cancel the reservation.
     */
    public void setCancelled() {
        this.guest.setReservation(null);
        this.room.setStatus(Room.RoomStatus.VACANT);
        this.room.setReservation(null);
        this.reservationStatus = ReservationStatus.CANCELLED;
    }

    /**
     * method check in to reservation.
     */
    public void checkIntoReservation() {
        this.reservationStatus = ReservationStatus.CHECKEDIN;
        guest.checkIn(room);
    }

    /**
     * method to check out of reservation.
     * will remove this instance reference from guest and room object
     */
    void setCheckOut() {
        //checkedout called 
        this.reservationStatus = ReservationStatus.CHECKEDOUT;
        this.guest.setReservation(null);
        this.room.setReservation(null);
    }

    /**
     * enum of reservation status.
     */
    private static enum ReservationStatus {
        CONFIRMED, EXPIRED, CHECKEDIN, CANCELLED, CHECKEDOUT;
    }

    /**
     * method to check if the reservation is valid.
     *
     * @return true if reservation has been confirmed
     */
    public boolean isValid() {
        return this.reservationStatus == ReservationStatus.CONFIRMED;
    }

    /**
     * method to override the hash code method.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * method to override equals method.
     *
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.reservationID, other.reservationID)) {
            return false;
        }
        return true;
    }

    /**
     * method check if the reservation id are equals
     *
     * @param otherResID
     * @return true of false
     */
    public boolean equals(String otherResID) {
        return this.reservationID.endsWith(otherResID);
    }

    /**
     * method to get the status of the reservation
     *
     * @return status
     */
    public String getStatus() {
        return this.reservationStatus.name();
    }

    /**
     * method to override toString function
     *
     * @return new string format
     */
    @Override
    public String toString() {
        //return String.format("%-14s | %-8s | %-8s | %-8s | %-16 %s", "Reservation ID","Status", "Guest ID", "Room ID", "Date to Check In");
        return String.format("%-14s | %-8s | %-8s | %-8s | %-16s %s", this.reservationID, this.getStatus(), this.guest.getGuestID(), this.room.getRoomID(), this.dateToCheckIn.toLocalDate(), this.dateToCheckIn.toLocalTime());
    }
}
