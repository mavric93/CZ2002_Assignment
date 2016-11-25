/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.RoomManager;
import myEntities.Room;

/** Submenu to manage options for rooms in the application
 *
 * @author Mavric
 */
public class RoomView extends Menu {

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     */
    public RoomView(Scanner sc) {
        super(sc);
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {

        System.out.println("RoomList Management...");
        System.out.println("1) Update a room's status");
        System.out.println("2) List all room by type");
        System.out.println("3) List all room by status");
        System.out.println("4) List all room by floor");
        System.out.println("5) Show summary of rooms");
        System.out.println("6) Check room's details");
        System.out.println("0) Exit");
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
                    System.out.println("Updating a room status");
                    new RoomStatusView(sc).run();
                    break;
                case 2:
                    System.out.println("Listing all room by type");
                    System.out.println(RoomManager.listRooms(RoomManager.SortBy.ROOMTYPE));
                    break;
                case 3:
                    System.out.println("Listing all room by status");
                    System.out.println(RoomManager.listRooms(RoomManager.SortBy.ROOMSTATUS));
                    break;
                case 4:
                    System.out.println("Listing all room by floor");
                    System.out.println(RoomManager.listRooms(RoomManager.SortBy.FLOOR));
                    break;
                case 5:
                    System.out.println("Showing a summary");
                    System.out.println(RoomManager.listRooms(RoomManager.SortBy.SUMMARY));
                    break;
                case 6:
                    System.out.println("Listing room details");
                    String roomID = sc.nextLine();
                    System.out.println(RoomManager.getRoomDetails(roomID));

                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    return done;
                default:
                    System.out.println("Invalid option : " + choice);
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
