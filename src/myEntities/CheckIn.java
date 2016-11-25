/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Association class between Guest and room.
 *
 * @author Mavric
 */
public class CheckIn implements Serializable {

    private Room room;
    private HotelGuest guest;
    private LocalDateTime dateToCheckIn;

    /** Public constructor to create this object.
     * 
     * @param guest a reference to a hotel guest object
     * @param room a reference to a room object
     */
    public CheckIn(HotelGuest guest, Room room) {
        this.room = room;
        this.guest = guest;
        this.dateToCheckIn = LocalDateTime.now();
        room.checkedIn(this);
    }

    /**
     * method to checkOut.
     * should only be called by guest
     */
    protected void checkOut() {
        this.room.checkedOut();
    }

    /**
     * method to get room
     *
     * @return room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * method to set room
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * method to get hotel guest
     *
     * @return guest
     */
    public HotelGuest getGuest() {
        return guest;
    }

    /**
     * method to set hotel guest
     *
     * @param guest
     */
    public void setGuest(HotelGuest guest) {
        this.guest = guest;
    }

    /**
     * method to get date to check in
     *
     * @return date to check in
     */
    public LocalDateTime getDateToCheckIn() {
        return dateToCheckIn;
    }

    /**
     * method to set date to check in
     *
     * @param dateToCheckIn
     */
    public void setDateToCheckIn(LocalDateTime dateToCheckIn) {
        this.dateToCheckIn = dateToCheckIn;
    }
}
