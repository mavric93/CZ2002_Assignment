/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.*;

/**
 * Submenu to display options for an individual guest
 *
 * @author Mavric
 */
class GuestDisplayView extends Menu {

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     */
    public GuestDisplayView(Scanner sc) {
        super(sc);
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Displaying guest details..");
    }

    /**
     * Method to ask user for choice and process option. may calls other submenu
     * if need be
     *
     * @return true when user keys in zero to indicate end of this menu
     *
     */
    @Override
    public boolean processChoices() {
        try {
            System.out.println("Please enter guest ID");
            String guestID = sc.nextLine();

            System.out.println(GuestManager.getGuestDetails(guestID));
            //guest is already checked in. only show options to check out or order roomServices and make payment
            if (GuestManager.isCheckedIn(guestID)) {
                new checkedInGuestView(sc, guestID).run();

                //guest is not checked in but has reservations
            } else if (GuestManager.hasReservation(guestID)) {
                System.out.println("Guest " + guestID + " has made the following reservation.");
                String resID = GuestManager.getReservation(guestID);

                new ReservedGuestView(sc, guestID).run();
            } else {
                //guest is neither check in nor has any reservation
                new normalGuestView(sc, guestID).run();
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid option : ");
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("Press any keys to continue..");
            System.out.println("");
            System.out.println("");
            sc.nextLine();
        }

        return true;
    }

}
