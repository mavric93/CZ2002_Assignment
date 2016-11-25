/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.RoomServiceManager;

/**
 *
 * @author Mavric
 */
class RoomServiceView extends Menu {
    /** constructor to create this instance.
     * @param sc scanner to perform I/O function
     */
    public RoomServiceView(Scanner sc) {
        super(sc);
    }
   /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Edit Room Services");
        System.out.println("Please choose one the following option");
        System.out.println("1) View current menu");
        System.out.println("2) Add item to current menu");
        System.out.println("3) Remove item from current menu");
        System.out.println("4) Edit price of item");
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
        boolean done = true;
        try {
            System.out.println("Please enter your choice");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Viewing menu");
                    System.out.println(RoomServiceManager.getMenuToString());
                    break;
                case 2:
                    System.out.println("Adding a new item to the menu");
                    System.out.println("Please enter new item name");
                    String newItemName = sc.nextLine();
                    System.out.println("Please enter new cost of item");
                    double newItemPrice = Double.parseDouble(sc.nextLine());

                    RoomServiceManager.addItem(newItemName, newItemPrice);
                    System.out.println("Item Added");
                    break;
                case 3:
                    System.out.println("Remove item from current menu");
                    System.out.println("Please enter item name");
                    String removeItemName = sc.nextLine();
                    RoomServiceManager.removeItem(removeItemName);

                    System.out.println("Item removed.");
                    break;
                case 4:
                    System.out.println("Edit price of item");
                    System.out.println("Please enter item name");
                    String itemName = sc.nextLine();
                    System.out.println("Please enter new price");
                    double itemPrice = Double.parseDouble(sc.nextLine());
                    RoomServiceManager.editItem(itemName, itemPrice);
                    break;
                case 0:
                    System.out.println("Returning to main menu");
                    return done;
                default:
                    System.out.println("Please enter an option from 0-5");
            }

        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid numerical option.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("Press any keys to continue..");
            System.out.println("");
            System.out.println("");
            sc.nextLine();
        }
        return !done;
    }

}
