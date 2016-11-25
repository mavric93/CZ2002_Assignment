/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for room object.
 *
 * @author Mavric
 */
public class Room implements Serializable {

    private final String roomID;
    private final int floor;
    private final RoomType type;

    private CheckIn checkedInGuest;
    private RoomStatus status;
    private Reservation reservation;

    /**
     * Constructor for room object
     *
     * @param roomID String id for this instance
     * @param floor floor number
     * @param type type Single, DOUBLE, DELUXE
     */
    public Room(String roomID, int floor, RoomType type) {
        this.roomID = roomID;
        this.floor = floor;
        this.status = RoomStatus.VACANT;
        this.type = type;
    }

    /**
     * MEthod to retrieve ID for this instance.
     *
     * @return String room ID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Method to retrieve floor number from this instance
     *
     * @return floor number
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Method to retrieve status from this instance
     *
     * @return status (VACANT, OCCUPIED, RESERVED, MAINTANENCE;)
     */
    public RoomStatus getStatus() {
        return status;
    }

    /**
     * Method to set room status
     *
     * @param status VACANT, OCCUPIED, RESERVED, MAINTANENCE;
     */
    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    /**
     * Method to store reference to checkin which stores reference to room
     *
     * @param c
     */
    protected void checkedIn(CheckIn c) {
        this.checkedInGuest = c;
        this.status = RoomStatus.OCCUPIED;
    }

    /**
     * Method to retrieve check in object which allows retrieving of guest
     *
     * @return
     */
    public CheckIn getCheckIn() {
        return checkedInGuest;
    }

    /**
     * Method to remove reference to check in and also guest reference. will set
     * status to vacant
     *
     */
    protected void checkedOut() {
        this.checkedInGuest = null;
        this.status = RoomStatus.VACANT;
    }

    /**
     * Method to get room type
     *
     * @return room type ( SINGLE, DOUBLE, DELUXE)
     */
    public RoomType getType() {
        return type;
    }

    /**
     * Method to get Reservation. may return null.
     *
     * @return reference to reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Method to set a reservation object. will also set status to reserved
     *
     * @param reservation
     */
    protected void setReservation(Reservation reservation) {
        this.status = RoomStatus.RESERVED;
        this.reservation = reservation;
    }

    /**
     * Enumeration for room status. possible status are VACANT, OCCUPIED,
     * RESERVED, MAINTANENCE;
     *
     */
    public static enum RoomStatus {

        /**
         * VACANT
         */
        VACANT,
        /**
         * OCCUPIED
         */
        OCCUPIED,
        /**
         * RESERVED
         */
        RESERVED,
        /**
         * MAINTANENCE
         */
        MAINTANENCE;
    }

    /**
     * Method to see if room is under maintance. Return
     *
     * @return true if under MAINTANENCE otherwise false
     */
    public boolean isUnderMaintanence() {
        return (this.status == RoomStatus.MAINTANENCE);
    }

    /**
     * Method to check if room is vacant.
     *
     * @return true if is vacant otherwise false
     */
    public boolean isVacant() {
        return this.status == RoomStatus.VACANT;
    }

    /**
     * Method to check if room is occupied.
     *
     * @return true if is occupied otherwise false
     */
    public boolean isOccupied() {
        return this.status == RoomStatus.OCCUPIED;
    }

    /**
     * Method to check if room is reserved.
     *
     * @return true if is reserved otherwise false
     */
    public boolean isReserved() {
        return this.status == RoomStatus.RESERVED;
    }

    /**
     * enumeration for room type. also comes with a cost.
     */
    public static enum RoomType {

        /**
         * SINGLE cost will be 100
         */
        SINGLE(100),
        /**
         * DOUBLE cost will be 200
         */
        DOUBLE(200),
        /**
         * DELUXE cost will be 300
         */
        DELUXE(300);
        int cost;

        RoomType(int cost) {
            this.cost = cost;
        }
    }

    /**
     * Method to retrieve cost of the room based on type. may return null
     *
     * @return
     */
    public double getCost() {
        return type.cost;
    }

    /**
     * Method to compare if instance is equal
     *
     * @param obj other room to be compared to
     * @return true if the other room object has the same id
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
        final Room other = (Room) obj;
        if (!Objects.equals(this.roomID, other.roomID)) {
            return false;
        }
        return true;
    }

    /**
     * Overload method to check based on id.
     *
     * @param ID
     * @return true if id is the same
     */
    public boolean equals(String ID) {
        if (ID == null || ID.equals("")) {
            return false;
        }
        if (this.roomID.equals(ID)) {
            return true;
        }
        return false;
    }

    /**
     * Method to retrieve string representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        //return String.format("%-10s | %-10s | %-10s", "Room ID","Room Type", "Room Status");
        return String.format("%-10s | %-10s | %-10s", this.roomID, this.type, this.status);
    }

}
