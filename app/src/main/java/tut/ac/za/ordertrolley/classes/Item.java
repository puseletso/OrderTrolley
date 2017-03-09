package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/23/2017.
 */

public class Item implements Serializable {


    private String name;
    private double price;
    private String description;
    private String catergory;
    private String imageUrl;

    public Item()
    {
        super();
    }

    public Item(String name, String description, String catergory, double price,String imageUrl) {
        this.name = name;
        this.description = description;
        this.catergory = catergory;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCatergory() {
        return catergory;
    }

    public void setCatergory(String catergory) {
        this.catergory = catergory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
