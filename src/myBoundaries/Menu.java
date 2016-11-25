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

/**
 * Abstract class for all our menus to user
 *
 * @author Mavric
 */
public abstract class Menu {

    /**
     * single scanner reference for all our submenus.
     */
    protected Scanner sc;

    /**
     * constructor for menu object
     *
     * @param sc
     */
    public Menu(Scanner sc) {
        this.sc = sc;
    }

    /**
     * run method to start all our submenu. a while not done loop to
     * continuously display banner and process user input
     */
    public final void run() {
        while (true) {
            this.displayView();
            boolean done = this.processChoices();
            saveAllManagers();
            if (done) {
                break;
            }
        }
    }

    /** Abstract class to display banner.
     *
     */
    public abstract void displayView();

    /** Abstract class to process user input
     *
     * @return true if done otherwise false
     */
    public abstract boolean processChoices();

    /** method to save all static control classes instances to file.
     *
     */
    public final void saveAllManagers() {
        GuestManager.saveGuests();
        RoomManager.saveRooms();
        ReservationManager.saveReservations();
        RoomServiceManager.saveMenu();
    }
}
