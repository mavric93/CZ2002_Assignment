/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.util.Objects;

/** Super class for hotel guest. maintains basic information for guest
 *
 * @author Mavric
 */
public class Guest implements Serializable {

    private String guestID;

    private String name;

    private String creditCardNumner;

    /**
     * Default constructor for guest object.
     *
     */
    public Guest() {
    }

    /** Overloaded constructor for guest Object.
     * 
     * @param guestID String id to identify this instance
     * @param name String name for the guest
     * @param creditCardNumner String credit card number of the guest
     */
    public Guest(String guestID, String name, String creditCardNumner) {
        this.guestID = guestID;
        this.name = name;
        this.creditCardNumner = creditCardNumner;
    }

    /**
     * method get guest ID
     *
     * @return guest
     *
     */
    public String getGuestID() {
        return guestID;
    }

    /**
     * method to get guest name
     *
     * @return name
     *
     */
    public String getName() {
        return name;
    }

    /**
     * method to set guest name
     *
     * @param name
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method to get credit card numner
     *
     * @return creditCardNumner
     *
     */
    public String getCreditCardNumner() {
        return creditCardNumner;
    }

    /**
     * method to set credit card numner
     *
     * @param creditCardNumner
     *
     */
    public void setCreditCardNumner(String creditCardNumner) {
        this.creditCardNumner = creditCardNumner;
    }

    /**
     * method to check if guest exists
     *
     * @param obj other object to be compared to
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
        final Guest other = (Guest) obj;
        return Objects.equals(this.guestID, other.guestID);
    }

    /**
     * method to check if guest ID is the same
     *
     * @param ID
     * @return true or false
     */
    public boolean equals(String ID) {
        return (this.guestID.equals(ID));
    }

    /**
     * method to override to string method
     *
     * @return the new string format
     */
    @Override
    public String toString() {
        return String.format("%-10s | %-15s | %-16s", "GuestID", "Guest Name", "Credit Card)");
        //return "Guest{" + "guestID=" + guestID + ", name=" + name + ", creditCardNumner=" + creditCardNumner + '}';
    }

}
