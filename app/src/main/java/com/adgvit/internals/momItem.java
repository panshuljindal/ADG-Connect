package com.adgvit.internals;

public class momItem {
    String date;
    String title;
    String header;
    String points;

    public momItem(String date, String title, String header, String points) {
        this.date = date;
        this.title = title;
        this.header = header;
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }

    public String getPoints() {
        return points;
    }
}
