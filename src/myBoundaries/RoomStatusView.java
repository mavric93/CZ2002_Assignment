/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBoundaries;

import java.util.Scanner;
import myControl.RoomManager;
import myEntities.Room;

/** Submenu for roomview. display case statements to update status
 *
 * @author Mavric
 */
public class RoomStatusView extends RoomView {

    /**
     * constructor to create this instance.
     *
     * @param sc scanner to perform I/O function
     */
    public RoomStatusView(Scanner sc) {
        super(sc);
    }

    /**
     * Method to display banner to console.
     *
     */
    @Override
    public void displayView() {
        System.out.println("Please enter room ID..");
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
            String roomID = sc.nextLine();
            System.out.println(RoomManager.getRoomDetails(roomID));
            System.out.println("Enter new room status");
            System.out.println("1) VACANT, 2) OCCUPIED, 3) RESERVED, 4) MAINTANENCE");
            int choice = Integer.parseInt(sc.nextLine());
            Room.RoomStatus rs = intToRoomStatus(choice);
            RoomManager.updateRoomStatus(roomID, rs);

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println(aiobe.getMessage());
            return false;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return true;
    }

    private Room.RoomStatus intToRoomStatus(int num) throws ArrayIndexOutOfBoundsException {
        try {
            return Room.RoomStatus.values()[num - 1];
        } catch (Exception ex) {
            throw new ArrayIndexOutOfBoundsException("Invalid option.");
        }
    }
}
