package tut.ac.za.ordertrolley.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 1/25/2017.
 */

public class Transmission implements Serializable {

    private Object object;
    private String decision;


    public Transmission()
    {
        super();

    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
