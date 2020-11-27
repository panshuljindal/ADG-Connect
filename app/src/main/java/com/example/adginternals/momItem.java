package com.example.adginternals;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class momItem {
    String date;
    String title;
    String header;
    String points[];

    public momItem(String d,String t,String h,String p[]){
        date = d;
        title = t;
        header = h;
        points = p;
    }

    public String getDate(){
        return date;
    }
    public String getTitle(){
        return title;
    }
    public String getHeader(){
        return header;
    }
    public String[] getPoints(){
        return points;
    }

    public void printDetails (){
        Log.d(TAG, "printDetails: "+date+" "+title+" "+header+" ");
    }


}
