package com.example.demoapp.Adapters;

import java.io.Serializable;

public class NotificationBalance implements Serializable {
    private String notification;

    public NotificationBalance(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
