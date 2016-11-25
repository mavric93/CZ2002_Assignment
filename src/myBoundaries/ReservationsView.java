/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.GuestManager;
import myControl.ReservationManager;

/** Submenu to manage options for reservations in the application
 *
 * @author Mavric
 */
class ReservationsView extends Menu {
    
    /** constructor to create this instance.
     * @param sc scanner to perform I/O function
     */
    public ReservationsView(Scanner sc) {
        super(sc);
    }
   /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Please select one of the following options");
        System.out.println("1) View all Reservations");
        System.out.println("2) Edit reservations");
        System.out.println("0) return to main menu");
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
        int choice = 0;
        boolean done = true;
        try {
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Listing all reservations a guest..");
                    System.out.println(ReservationManager.listAllReservations());
                    break;
                case 2:
                    System.out.println("Editing a reservation");
                    System.out.println("Please enter reservation ID");
                    String resIDd = sc.nextLine();
                    System.out.println("Please enter new Date in \"YYYY MM DD HH\" format");
                    String date = sc.nextLine();
                    ReservationManager.editReservation(resIDd, date);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    return done;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid option : ");
            return !done;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return !done;
        } finally {
            System.out.println("Press any keys to continue..");
            System.out.println("");
            System.out.println("");
            sc.nextLine();
        }
        return !done;
    }
    
}
