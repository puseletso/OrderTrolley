package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/23/2017.
 */

public class Address implements Serializable {

    private String streetName;
    private String Country;
    private String province;
    private String city;




    public Address() {
        super();
    }


    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
