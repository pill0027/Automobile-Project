package com.example.krishnaveni.automobile;

import java.io.Serializable;

/**
 * Created by KRISHNAVENI on 2017-12-17.
 */

public class Automobile implements Serializable{

    private long id;
    private float liters,price,kilometer;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    public Automobile(long id, float liters,float price, float kilometer,String timetsmp )
    {
        this.setId(id);
        this.setLiters(liters);
        this.setPrice(price);
        this.setKilometer(kilometer);
        this.setTimestamp(timetsmp);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLiters() {
        return liters;
    }

    public void setLiters(float liters) {
        this.liters = liters;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getKilometer() {
        return kilometer;
    }

    public void setKilometer(float kilometer) {
        this.kilometer = kilometer;
    }
}
