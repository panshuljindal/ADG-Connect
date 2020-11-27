package com.example.adginternals;

public class alertdata {
    String id;
    String link;
    String location;
    Integer time;
    String title;
    String type;

    public alertdata() {
    }

    public alertdata(String id, String link, String location, Integer time, String title, String type) {
        this.id = id;
        this.link = link;
        this.location = location;
        this.time = time;
        this.title = title;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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
}
