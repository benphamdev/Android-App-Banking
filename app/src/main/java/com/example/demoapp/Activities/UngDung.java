package com.example.demoapp.Activities;

public class UngDung {
    private int resourceID;
    private String name;

    public UngDung(int resourceID, String name) {
        this.resourceID = resourceID;
        this.name = name;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
