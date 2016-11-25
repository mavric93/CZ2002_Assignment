/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myControl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import myEntities.HotelGuest;
import myEntities.RoomServiceItem;

/**
 * Static class to manage room service menu.
 *
 * @author Mavric
 */
public class RoomServiceManager {

    private static ArrayList<RoomServiceItem> menu;

    /**
     * Private constructor. to block use of instances from this class. This
     * class should be used as a static class instead of singleton
     */
    private RoomServiceManager() {
    }

    /**
     * Method to initialize static class. set menu to be empty *
     */
    public static void init() {
        try {
            menu = loadMenu();
        } catch (IOException ex) {
            menu = new ArrayList();
        } catch (Exception ex) {
        }
    }

    /**
     * Method to add in a roomserviceitem into the menu. Perform validation by
     * looking up if an identical item name is already in the menu.
     *
     * @param nameOfItem name of new roomServiceItem
     * @param cost cost of new roomServiceItem
     * @return
     * @throws Exception thrown when there exist an identical item name in the
     * system. . also when the input string is null or empty or cost is less
     * than zero
     */
    public static boolean addItem(String nameOfItem, double cost) throws Exception {
        RoomServiceItem rsi = new RoomServiceItem(nameOfItem, cost);
        if (nameOfItem == null || nameOfItem.equals("")) {
            throw new Exception("Item name should not be empty");
        }
        if (cost <= 0) {
            throw new Exception("Cost should be a number larger than zero");
        }

        if (getItemIndex(nameOfItem) != -1) {
            throw new Exception("Item name already exists in system.\n"
                    + rsi.toString());
        }

        menu.add(rsi);
        return true;
    }

    /**
     * Method to remove item from menu. Do note that this method only removes
     * item from the menu and not from guest's ordered services records
     *
     * @param nameOfItem name of item to be removed
     * @return true if successful. false if otherwise
     * @throws Exception thrown when item name is empty or name does not exist
     * in menu
     */
    public static boolean removeItem(String nameOfItem) throws Exception {
        if (nameOfItem == null || nameOfItem.equals("")) {
            throw new Exception("Item name should not be empty");
        }

        int itemIndex = getItemIndex(nameOfItem);
        if (itemIndex < 0) {
            throw new Exception(nameOfItem + " does not exist in menu.");
        }
        menu.remove(itemIndex);

        return true;
    }

    /**
     * Method to edit item from menu.
     *
     * @param nameOfItem name of item to be edit
     * @param newPrice price of the item to be edit
     * @return true if successful. false if otherwise
     * @throws Exception thrown when item name is empty or name does not exist
     * in menu
     */
    public static boolean editItem(String nameOfItem, double newPrice) throws Exception {
        if (nameOfItem == null || nameOfItem.equals("")) {
            throw new Exception("Item name should not be empty");
        }

        int itemIndex = getItemIndex(nameOfItem);
        if (itemIndex < 0) {
            throw new Exception(nameOfItem + " does not exist in menu.");
        }
        RoomServiceItem rsi = menu.get(itemIndex);

        rsi.setCost(newPrice);

        return true;
    }

    /**
     * Method to add item to the guest
     *
     * @param guestID
     * @param index of the item
     * @return name of the item
     * @throws Exception thrown
     */
    public static String addItemToGuest(String guestID, int index) throws Exception {
        if (index < 0 || index > menu.size()) {
            System.out.println(index);
            throw new Exception("Invalid choice for room service");
        }

        RoomServiceItem rsi = menu.get(index);
        HotelGuest g = GuestManager.searchGuest(guestID);
        g.orderService(rsi);

        return rsi.getName();
    }

    /**
     * Method to print out all items in menu. Similar to a toString method
     *
     * @return String representation of all items in the list
     * @throws java.lang.Exception throws exception when the list is empty
     */
    public static String getMenuToString() throws Exception {
        if (menu.isEmpty()) {
            throw new Exception("The menu is empty");
        }

        String output = String.format("%3s %-20s %s\n", "", "Name", "Cost");

        int counter = 1;

        for (RoomServiceItem rsi : menu) {
            output += String.format("%02d) %-20s $%.2f\n", counter++, rsi.getName(), rsi.getCost());
        }

        return output;
    }

    /**
     * Method to find the index of a roomService item based on name. protected.
     * only callable by myControl package
     *
     * @param nameOfItem name of item to be search for
     * @return index if found, -1 if otherwise
     */
    protected static int getItemIndex(String nameOfItem) {
        for (RoomServiceItem rsi : menu) {
            if (rsi.getName().equalsIgnoreCase(nameOfItem)) {
                return menu.indexOf(rsi);
            }
        }
        return -1;
    }

    /**
     * Method for serialisation. write arraylist into file
     */
    public static void saveMenu() {
        try {
            FileOutputStream fos = new FileOutputStream("menu.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(menu);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static ArrayList<RoomServiceItem> loadMenu() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("menu.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<RoomServiceItem> loadedMenu = (ArrayList<RoomServiceItem>) ois.readObject();
        ois.close();
        return loadedMenu;
    }
}
