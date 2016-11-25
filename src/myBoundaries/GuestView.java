/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.GuestManager;

/** Submenu to manage list of guest in the application
 *
 * @author Mavric
 */
public class GuestView extends Menu {

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     */
    public GuestView(Scanner sc) {
        super(sc);
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("GuestList Management...");
        System.out.println("1) Create a guest");
        System.out.println("2) List all guests");
        System.out.println("3) Check guest's details");
        System.out.println("0) Exit");
    }

    /**
     * Method to ask user for choice and process option. may calls other submenu
     * if need be
     *
     * @return true when user keys in zero to indicate end of this menu
     */
    @Override
    public boolean processChoices() {
        int choice = 0;
        boolean done = true;
        try {
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Creating a guest..");
                    System.out.println("Please enter guest's name");
                    String name = sc.nextLine();
                    System.out.println("Please enter guest's credit card number");
                    String creditCardNum = sc.nextLine();
                    String result = GuestManager.addGuest(name, creditCardNum);
                    System.out.println("Guest created.");
                    System.out.println(result);
                    break;
                case 2:
                    System.out.println("Listing all guests");
                    System.out.println(GuestManager.listAllGuest());
                    break;
                case 3:
                    System.out.println("Printing the details of a guest..");
                    System.out.println("Please key in a guest ID");
                    String guestID = sc.nextLine();
                    System.out.println(GuestManager.getGuestDetails(guestID));
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
