package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ProJava on 1/23/2017.
 */

public class Store implements Serializable{

    private String storeID;
    private String name;
    private String email;
    private List<Item> itemList;
    private Address address;
    private String imageUrl;

    public Store() {
        super();
    }

    public Store(String storeID,String name, String email, List<Item> itemList,Address address,String image) {
        this.storeID = storeID;
        this.name = name;
        this.email = email;
        this.itemList = itemList;
        this.imageUrl = image;
        this.address = address;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
