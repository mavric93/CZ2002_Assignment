/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.GuestManager;
import myControl.ReservationManager;

/** Submenu from guestDisplay view shown when guest has a reservation
 *
 * @author Mavric
 */
public class ReservedGuestView extends Menu {

    private String guestID;
    /** constructor to create this instance.
     * @param sc scanner to perform I/O function
     * @param guestID guest id to indicate which guest is being processed
     */
    public ReservedGuestView(Scanner sc, String guestID) {
        super(sc);
        this.guestID = guestID;
    }
   /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Please select one of the following options");
        System.out.println("1) Check into reserved room");
        System.out.println("2) Edit reservation");
        System.out.println("3) Cancel reservation");
        System.out.println("0) Return to main menu");
    }

    /**
     * Method to ask user for choice and process option. may calls other submenu if
     * need be
     *
     * @return true when user keys in zero to indicate end of this menu
     *
     */
    @Override
    public boolean processChoices() {
        final boolean done = true;
        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Checking into reserved room " + ReservationManager.getRoomFromGuestID(guestID));
                    GuestManager.checkIn(guestID);
                    System.out.println(GuestManager.guestRoomToString(guestID));
                    return done;
                case 2:
                    System.out.println("Editing reservation");
                    System.out.println("Enter new room to reserved");
                    String newRoom = sc.nextLine();

                    System.out.println("New reservation :");
                    System.out.println(ReservationManager.editReservation(guestID, newRoom));
                    break;
                case 3:
                    System.out.println("Cancelling reservation.");
                    ReservationManager.cancelReservation(guestID);
                    System.out.println("Reservation is cancelled.");
                    break;
                case 0:
                    System.out.println("Return to main menu");
                    return done;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid choice.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
        }
        return !done;
    }

}
