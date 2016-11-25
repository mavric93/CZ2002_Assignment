/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entity class for bill object. should be instiated by guest only
 *
 * @author Mavric
 */
public class Bill implements Serializable {

    private final HotelGuest guest;
    private double amount;

    /**
     * public constructor of this class
     *
     * @param guest takes in a reference to a guest object
     */
    public Bill(HotelGuest guest) {
        this.guest = guest;
        this.amount = 0;
    }

    /**
     * Method to generate receipt. The recepit is a string detailing all the
     * costs that is chargeable to the guest
     *
     * @param byCreditCard boolean to indicate payment by credit card (true) or by cash (false)
     * @return String details of of the bill
     */
    public String generateReceipt(boolean byCreditCard) {
        String receipt = "Bill for " + guest.getGuestID() + "\n";

        if (byCreditCard) {
            receipt += "Payment by Credit Card : " + guest.getCreditCardNumner() + "\n";
        } else {
            receipt += "Payment by cash\n";
        }

        Room stayedRoom = guest.getRoom();
        int daysStayed = LocalDateTime.now().compareTo(guest.getCheckInDate());
        double roomCost = stayedRoom.getCost();

        String roomID = stayedRoom.getRoomID();
        String roomType = stayedRoom.getType().name();
        receipt += String.format("%-15s : %s\n", "Room stayed", roomID);
        receipt += String.format("%-15s : %s\n", "Room Type", roomType);
        receipt += String.format("%-15s : $%.2f\n", "Room Cost", roomCost);
        receipt += String.format("%-15s : %d\n", "Days Stayed", daysStayed);
        amount = roomCost * daysStayed;
        receipt += String.format("%-15s : $%.2f\n", "Total Cost", amount);
        receipt += "\n\n";

        receipt += "Room Service Ordered:\n";
        if (guest.getOrderedServices().isEmpty()) {
            receipt += "No room services ordered.\n";
            receipt += String.format("Total bill : $%.2f", amount);
            return receipt;
        } else {
            receipt += String.format("%-20s %s\n\n", "Service ordered", "Cost");
            for (RoomServiceItem rsi : guest.getOrderedServices()) {
                receipt += rsi.toString() + "\n";
                amount += rsi.getCost();
            }
            receipt += String.format("Total bill : $%.2f", amount);
        }
        return receipt;
    }
}
