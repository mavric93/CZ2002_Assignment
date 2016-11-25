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
import myControl.RoomServiceManager;

/** submenu for guestDisplayview shown when guest is checked in.
 *
 * @author Mavric
 */
public class checkedInGuestView extends GuestDisplayView {

    private String guestID;

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     * @param guestID guest id to indicate which guest is being processed
     */
    public checkedInGuestView(Scanner sc, String guestID) {
        super(sc);
        this.guestID = guestID;
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println(guestID + " is checked in.");
        System.out.println("1) Checkout and make payment");
        System.out.println("2) View Ordered room services.");
        System.out.println("3) Order room services");
        System.out.println("0) Return to main menu");
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
        boolean done = true;
        try {
            Integer choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Check out and make payment");
                    System.out.println("Payment by creditcard? ( Y/N )");
                    boolean payByCreditCard = true;
                    if (sc.nextLine().equalsIgnoreCase("n")) {
                        payByCreditCard = false;
                    }
                    String result = GuestManager.checkout(guestID, payByCreditCard);
                    System.out.println(result);
                    return done;
                case 2:
                    System.out.println("Printing list of ordered services");
                    String orderedServices = GuestManager.getListOfOrderedServices(guestID);
                    System.out.println(orderedServices);
                    break;
                case 3:
                    System.out.println("Ordering room services");
                    System.out.println("Please select one of the following room services");
                    System.out.println(RoomServiceManager.getMenuToString());
                    int roomServiceChoice = Integer.parseInt(sc.nextLine());
                    String orderedItem = RoomServiceManager.addItemToGuest(guestID, roomServiceChoice - 1);
                    System.out.println(guestID + " has ordered " + orderedItem);
                    break;
                case 0:
                    System.out.println("");
                    System.out.println("");
                    return done;
            }

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println(aiobe.getMessage());
            return false;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return !done;
    }
}
