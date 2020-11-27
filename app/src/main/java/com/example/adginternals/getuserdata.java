package com.example.adginternals;

public class getuserdata {
    String email;
    String fcm;
    String name;
    String phone;
    String regNo;
    String uid;

    public getuserdata() {
    }

    public getuserdata(String email, String fcm, String name, String phone, String regNo, String uid) {
        this.email = email;
        this.fcm = fcm;
        this.name = name;
        this.phone = phone;
        this.regNo = regNo;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
