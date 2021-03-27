package com.adgvit.internals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    ArrayList<card2item> list2Core,list2Team,finalArrayF,sortedArrayF;
    ArrayList<Integer> timeStampsCore,timeStampsTeam,sortedFTime,timeF;
    DatabaseReference myref,myref1,myref2;
    int count;
    String team;
    List<String> teamlist;
    String uid;
    ConstraintLayout ui1;
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
        ui1 = view.findViewById(R.id.emptyHomeLayout);

        list1 = new ArrayList<>();

        list2Core = new ArrayList<>();
        timeStampsCore = new ArrayList<>();

        list2Team = new ArrayList<>();
        timeStampsTeam = new ArrayList<>();

        finalArrayF = new ArrayList<>();
        sortedArrayF = new ArrayList<>();
        sortedFTime = new ArrayList<>();
        timeF = new ArrayList<>();

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
        addData();
        firebase();

        adapter1();
        adapter2();
        checkData();
        return view;
    }

    public void checkData(){
        Log.i("Sorted",String.valueOf(sortedArrayF.size()));
        if (sortedArrayF.size()==0){
            ui1.setVisibility(View.VISIBLE);
            recyclerViewNotification.setVisibility(View.INVISIBLE);
        }else {
            ui1.setVisibility(View.INVISIBLE);
            recyclerViewNotification.setVisibility(View.VISIBLE);
        }
    }
    public void addData(){
        String title="";
        list1.add(new card1item(R.drawable.androidback,R.drawable.ic_android,"ANDROID",title));
        list1.add(new card1item(R.drawable.mlback,R.drawable.ic_ml,"Machine Language",title));
        list1.add(new card1item(R.drawable.designback,R.drawable.ic_design,"Design",title));
        list1.add(new card1item(R.drawable.iosback,R.drawable.ic_ios,"iOS",title));
        list1.add(new card1item(R.drawable.webback,R.drawable.ic_web,"Web Dev",title));
        adapter1();
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
                timeStampsCore.clear();
                list2Core.clear();
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
                            timeStampsCore.add(ad.getTime());
                            list2Core.add(new card2item(title, time));
                        }
                    }
                }
                myref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        timeStampsTeam.clear();
                        list2Team.clear();

                        finalArrayF.clear();
                        sortedArrayF.clear();
                        sortedFTime.clear();
                        timeF.clear();
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
                                    timeStampsTeam.add(ad.getTime());
                                    list2Team.add(new card2item(title, time));
                                }
                            }
                        }

                        finalArrayF = list2Core;
                        finalArrayF.addAll(list2Team);
                        timeF = timeStampsCore;
                        timeF.addAll(timeStampsTeam);
                        sortedFTime.addAll(timeF);
                        Collections.sort(sortedFTime);
                        Log.i("Time",timeF.toString());
                        Log.i("Sorted",sortedFTime.toString());
                        count =0;
                        int start=0;
                        for (int i=0;i<timeF.size();i++){

                            int index=timeF.indexOf(sortedFTime.get(i));

                            try {
                                sortedArrayF.add(finalArrayF.get(index));
                                //Log.i("Count",String.valueOf(count));

                                if (i==3){
                                    break;
                                }
                            }
                            catch (IndexOutOfBoundsException e){

                            }


                        }
                        savaData();
                        adapter2();
                        checkData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        checkData();

                        adapter2();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //savaData();
                checkData();
                adapter2();
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
        checkData();
        card2adapter adapter1= new card2adapter(getContext(),sortedArrayF);
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
        String json1 = gson.toJson(sortedArrayF);
        editor.putString("scrollbar",json);
        editor.putString("notifications",json1);
        editor.apply();

    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("scrollbar","");
        String json1 = preferences.getString("notifications","");
        Type type = new TypeToken<ArrayList<card1item>>() {}.getType();
        Type type1 = new TypeToken<ArrayList<card2item>>() {}.getType();
        list1 =gson.fromJson(json,type);
        if(list1==null){
            list1 =new ArrayList<>();
        }
        sortedArrayF = gson.fromJson(json1,type1);
        if (sortedArrayF==null){
            sortedArrayF = new ArrayList<>();
        }
    }
}