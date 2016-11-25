/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.GuestManager;
import myControl.ReservationManager;
import myControl.RoomManager;
import myEntities.HotelGuest;
import myEntities.Room;

/** Submenu for guestdisplayview shown when guest is neither checkin nor has reservation
 *
 * @author Mavric
 */
public class normalGuestView extends GuestDisplayView {

    private String guestID;

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     * @param guestID guest id to indicate which guest is being processed
     */
    public normalGuestView(Scanner sc, String guestID) {
        super(sc);
        this.guestID = guestID;
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Please choose one of the following options");
        System.out.println("1) Check into a room.");
        System.out.println("2) Make a reservation.");
        //  System.out.println("3) Update details");
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
        try {
            Integer choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Checking into a room..");
                    System.out.println("Please enter which room to check into");
                    String roomID = sc.nextLine();
                    String result = GuestManager.checkIn(guestID, roomID);
                    System.out.println(result);
                    break;
                case 2:
                    System.out.println("Making a reservation");
                    System.out.println("Please enter room ID");
                    String reservedRoom = sc.nextLine();

                    System.out.println("Please enter Date and time to check in. Format \"YYYY MM DD HH\"");
                    String dateTime = sc.nextLine();
                    System.out.println();
                    System.out.println(ReservationManager.addReservation(guestID, reservedRoom, dateTime));
                    break;
                //       case 3:
                //         System.out.println("Update guest details");
                //           System.out.println("");
                case 0:
                    System.out.println("");
            }

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println(aiobe.getMessage());
            return false;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

}
