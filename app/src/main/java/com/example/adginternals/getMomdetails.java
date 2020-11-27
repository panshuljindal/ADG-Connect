package com.example.adginternals;

public class getMomdetails {
    String header;
    String points;
    Integer time;
    String title;

    public getMomdetails() {
    }

    public getMomdetails(String header, String points, Integer time, String title) {
        this.header = header;
        this.points = points;
        this.time = time;
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
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
}
