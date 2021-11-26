package com.example.midnightexpress;

public class CheckingOut {
    
    String name;
    String cost;

    public CheckingOut(String name, String cost)
    {
        this.name = name;
        this.cost = cost;
        
    }


    public String getTitle()
    {
        return name;
    }
    
    public String getCost(){
        return cost;
    }

    public String toString()
    {
        return this.name + "\t\t" + this.cost;
    }
}
