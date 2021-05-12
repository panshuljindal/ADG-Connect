package com.adgvit.internals.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.adgvit.internals.R;

public class BestOfLuck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_of_luck);
        clearData();
    }
    public void clearData(){
        SharedPreferences userdata = getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser = userdata.edit();
        editoruser.clear();
        editoruser.apply();
        SharedPreferences userdata1 = getSharedPreferences("com.adgvit.com.alert",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser1 = userdata1.edit();
        editoruser1.clear();
        editoruser1.apply();
        SharedPreferences userdata2 = getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser2 = userdata2.edit();
        editoruser2.clear();
        editoruser2.apply();
        SharedPreferences preferences = getSharedPreferences("com.adgvit.com.mom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = preferences.edit();
        editor3.clear();
        editor3.apply();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        Toast.makeText(this, "Bye bye", Toast.LENGTH_SHORT).show();
    }
}