package com.adgvit.internals.Model;

public class getMomdetails {
    String header;
    Integer time;
    String title;
    String team;
    String id;

    public getMomdetails() {
    }

    public getMomdetails(String header, Integer time, String title,String team,String id) {
        this.header = header;
        this.time = time;
        this.title = title;
        this.team = team;
        this.id=id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
