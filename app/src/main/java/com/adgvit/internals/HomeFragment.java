package com.adgvit.internals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView1,recyclerViewNotification;
    View view;
    ArrayList<card1item> list1;
    ArrayList<card2item> list2,sortedArrayList;
    ArrayList<Integer> timeStamps;
    DatabaseReference myref,myref1,myref2;
    String team;
    List<String> teamlist;
    String uid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerview1);
        recyclerViewNotification= (RecyclerView) view.findViewById(R.id.recyclerviewNotifications);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        timeStamps = new ArrayList<>();
        sortedArrayList = new ArrayList<>();

        loadData();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Home").child("Scroll");
        myref1 = db.getReference("Alerts").child("Core");
        myref2 = db.getReference("Alerts").child("Team");

        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        team = pref.getString("teams","");
        uid = pref.getString("uid","");
        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        teamlist= new ArrayList<>(Arrays.asList(team2.split(", ")));

        firebase();

        adapter1();
        adapter2();
        return view;
    }

    public void firebase(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    scrollClass sc = ds.getValue(scrollClass.class);
                    String id= sc.getId();
                    String title = sc.getTitle();
                    String type = sc.getType();
                    if(type.equals("0")){
                        list1.add(new card1item(R.drawable.iosback,R.drawable.ic_ios,"iOS",title));
                    }
                    else if(type.equals("1")){
                        list1.add(new card1item(R.drawable.webback,R.drawable.ic_web,"Web Dev",title));
                    }
                    else if(type.equals("2")){
                        list1.add(new card1item(R.drawable.androidback,R.drawable.ic_android,"ANDROID",title));
                    }
                    else if(type.equals("3")){
                        list1.add(new card1item(R.drawable.mlback,R.drawable.ic_ml,"Machine Language",title));
                    }
                    else if(type.equals("7")){
                        list1.add(new card1item(R.drawable.designback,R.drawable.ic_design,"Design",title));
                    }

                }
                savaData();
                adapter1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sortedArrayList.clear();
                list2.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String uids = ds.child("users").getValue().toString();
                    //Log.i("uids",uids);
                    if(uids.contains(uid)){
                        alertdata ad = ds.getValue(alertdata.class);
                        String title = ad.getTitle();
                        String time = unixconvert(ad.getTime().toString());
                        Long current = System.currentTimeMillis();
                        Long date = Long.valueOf(ad.getTime()) * 1000 + 864000000L;
                        if (current >= date) {
                            //Log.i("Date","Date Matched");
                        } else {
                            timeStamps.add(ad.getTime());
                            list2.add(new card2item(title, time));
                        }
                    }
                }
                myref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String uids = ds.child("users").getValue().toString();
                            //Log.i("uids",uids);
                            if(uids.contains(uid)){
                                alertdata ad = ds.getValue(alertdata.class);
                                String title = ad.getTitle();
                                String time = unixconvert(ad.getTime().toString());
                                Long current = System.currentTimeMillis();
                                Long date = Long.valueOf(ad.getTime()) * 1000 + 864000000L;
                                if (current >= date) {
                                    //Log.i("Date","Date Matched");
                                } else {
                                    timeStamps.add(ad.getTime());
                                    list2.add(new card2item(title, time));
                                }
                            }
                        }
                        Collections.sort(timeStamps);
                        for (int i=0;i<timeStamps.size();i++){
                            sortedArrayList.add(list2.get(timeStamps.indexOf(timeStamps.get(i))));
                            if (i==3){
                                break;
                            }
                        }
                        savaData();
                        adapter2();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM, hh:mma").format(df);
        return vv;
    }
    public void adapter1(){
        card1adapter adapter = new card1adapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(manager);
    }
    public void adapter2(){
        card2adapter adapter1= new card2adapter(getContext(),sortedArrayList);
        LinearLayoutManager manager1= new LinearLayoutManager(getContext());
        manager1.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotification.setAdapter(adapter1);
        recyclerViewNotification.setLayoutManager(manager1);
    }
    public void savaData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("scrollbar",json);
        String json1 = gson.toJson(list2);
        editor.putString("notifications",json1);
        /*String json2 = gson.toJson(timeStamps);
        editor.putString("timeStamps",json2);*/
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("scrollbar","");
        String json1 = preferences.getString("notifications","");
        String json2 = preferences.getString("timeStamps","");
        Type type = new TypeToken<ArrayList<card1item>>() {}.getType();
        Type type1 = new TypeToken<ArrayList<card2item>>() {}.getType();
        Type type2 = new TypeToken<ArrayList<Integer>>() {}.getType();
        list1 =gson.fromJson(json,type);
        if(list1==null){
            list1 =new ArrayList<>();
        }
        list2 = gson.fromJson(json1,type1);
        if(list2==null){
            list2 = new ArrayList<>();
        }
        /*timeStamps = gson.fromJson(json2,type2);
        if (timeStamps==null){
            timeStamps = new ArrayList<>();
        }*/
    }
}