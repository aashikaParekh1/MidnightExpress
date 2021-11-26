package com.example.midnightexpress;

import java.io.Serializable;

public class Menu implements Serializable
{

    int image = 0;
    String title = "";
    int allergy = 0;
    String cost = "";
    boolean active;
    int costAmount;
    public boolean checked;

    public Menu(int image, String title, String cost, int allergy,  boolean active, int costAmount)
    {
        this.image = image;
        this.title = title;
        this.allergy = allergy;
        this.cost = cost;
        this.active = active;
        this.costAmount = costAmount;

    }

    public int getImage()
    {

        return image;


    }

    public int getSymb()
    {

        return allergy;


    }

    public String getTitle()
    {
        return title;
    }

    public String getCost()
    {
        return cost;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString()
    {
        return this.title;
    }

    public int getCostAmount()
    {
        return this.costAmount;
    }




}
