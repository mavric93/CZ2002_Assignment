/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;

/** Entity class for room serice item. extends orderlineitem.
 *
 * @author Mavric
 */
public class RoomServiceItem extends OrderLineItem implements Serializable{

    private ItemStatus status;
    private int quantity;

    /**
     *
     * @param name
     * @param cost
     */
    public RoomServiceItem(String name, double cost) {
        super(name, cost);
    }

    /**
     *
     * @return
     */
    public ItemStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** enumeration for status.
     * Ordered, preparing, delivered.
     */
    public static enum ItemStatus {

        /**
         *
         */
        ORDERED,

        /**
         *
         */
        PREPARING,

        /**
         *
         */
        DELIVERED;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%-20s %.2f", getName(), getCost());
    }

}
