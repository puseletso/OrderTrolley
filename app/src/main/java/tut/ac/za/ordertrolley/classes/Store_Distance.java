package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/31/2017.
 */

public class Store_Distance implements Serializable {

    private Store store;
    private double totalDistance;
    private String deliveryArea;



    public Store_Distance() {
        super();
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }
}
