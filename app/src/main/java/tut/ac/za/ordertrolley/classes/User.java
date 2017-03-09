package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/23/2017.
 */

public class User implements Serializable {

    private String userID;
    private String name;
    private String email;
    private String gender;



    public User() {
        super();
    }


    public User(String userID, String name, String gender, String email) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
