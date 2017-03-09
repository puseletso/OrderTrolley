package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/23/2017.
 */

public class Order implements Serializable {


    private String userID;
    private Store_Distance storeDistance;
    private String date;
    private Payment payment;
    private double deliveryFee;
    private double itemPrice;


 public  Order()
 {
    super();
  }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Store_Distance getStoreDistance() {
        return storeDistance;
    }

    public void setStoreDistance(Store_Distance storeDistance) {
        this.storeDistance = storeDistance;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}


