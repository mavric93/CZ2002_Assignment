/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myEntities;

import java.io.Serializable;
import java.util.Objects;

/** Super class for line items.
 *
 * @author Mavric
 */
public class OrderLineItem implements Serializable {

    private final String name;
    private double cost;

    //constructor

    /** default constructor.
     *
     */
    public OrderLineItem() {
        name = "";
        cost = 0;
    }
    //constructor overloading 

    /** overloaded constructor to create instance.
     *
     * @param name Name of the item
     * @param cost cost of the item
     */
    public OrderLineItem(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * method to get item name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * method to get item cost
     *
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * method to set item cost
     *
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * method method to override hashCode method
     *
     * @return new hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * method to override the equals method
     *
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderLineItem other = (OrderLineItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
