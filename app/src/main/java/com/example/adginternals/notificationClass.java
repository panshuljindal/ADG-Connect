package com.example.adginternals;

import android.content.Intent;

public class notificationClass {
    String details,id,title,type;
    Integer time;
    public notificationClass(){

    }

    public notificationClass(String details, String id, String title, String type, Integer time) {
        this.details = details;
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
