/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.io.Serializable;
import java.util.Scanner;
import myControl.*;

/** Main class of the application. display serveral other submenu
 *
 * @author Mavric
 */
public class MainView extends Menu implements Serializable {

    private GuestManager guestMgr;
    private RoomManager roomMgr;
    private ReservationManager resMgr;
    private Menu[] subMenu;

    /** Entry point for this application.
     * 
     * @param args command line arguements
     */
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.run();
    }

    /**
     * constructor to create this instance. instantiated the scanner object
     */
    public MainView() {
        super(new Scanner(System.in));
        GuestManager.init();
        RoomManager.init();
        ReservationManager.init();
        RoomServiceManager.init();
        subMenu = new Menu[]{
            new GuestView(sc),
            new RoomView(sc),
            new ReservationsView(sc),
            new GuestDisplayView(sc),
            new RoomServiceView(sc),};
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Welcome to Hotel App.");
        System.out.println("1) Manage GuestList");
        System.out.println("2) Manage RoomList");
        System.out.println("3) Manage Reservations");
        System.out.println("4) Manage individual guest ");
        System.out.println("5) Manage RoomServiceMenu");
        System.out.println("Please select an option.");
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
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                System.out.println("Thank you!");
                return done;
            }

            subMenu[choice - 1].run();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Not a valid option. Please try again.");
        } catch (NumberFormatException ex) {
            System.out.println("Not a valid option.");
        } catch (Exception ex) {
        }

        return !done;
    }

}
