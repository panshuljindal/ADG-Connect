package com.adgvit.internals.Fragments.HomeFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adgvit.internals.Activity.BestOfLuck;
import com.adgvit.internals.Activity.MainActivity;
import com.adgvit.internals.Adapter.RecyclerView.card1adapter;
import com.adgvit.internals.Adapter.RecyclerView.card2adapter;
import com.adgvit.internals.Model.alertdata;
import com.adgvit.internals.Model.card1item;
import com.adgvit.internals.Model.card2item;
import com.adgvit.internals.R;
import com.adgvit.internals.Model.scrollClass;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView1,recyclerViewNotification;
    View view;
    ArrayList<card1item> list1;
    ArrayList<card2item> list2Team,finalArrayF,sortedArrayF;
    ArrayList<Integer> timeStampsTeam,sortedFTime,timeF;
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


        list2Team = new ArrayList<>();
        timeStampsTeam = new ArrayList<>();

        finalArrayF = new ArrayList<>();
        sortedArrayF = new ArrayList<>();
        sortedFTime = new ArrayList<>();
        timeF = new ArrayList<>();

        loadData();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Home").child("Scroll");
        myref2 = db.getReference("Home").child("Notification");

        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", MODE_PRIVATE);
        team = pref.getString("teams","");
        uid = pref.getString("uid","");
        bestOfLuck();
        checkData1();
        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        teamlist= new ArrayList<>(Arrays.asList(team2.split(", ")));
        addData();
        firebase();

        adapter1();
        adapter2();
        checkData();
        sendToken();
        return view;
    }

    public void checkData1(){
        if (uid.equals("")){
            ui1.setVisibility(View.VISIBLE);
            recyclerViewNotification.setVisibility(View.INVISIBLE);
        }
        else {
            ui1.setVisibility(View.INVISIBLE);
            recyclerViewNotification.setVisibility(View.VISIBLE);
        }
    }
    public void checkData(){
        if (uid.isEmpty()){

        }
        else {
            Log.i("Sorted", String.valueOf(sortedArrayF.size()));
            if (sortedArrayF.size() == 0) {
                ui1.setVisibility(View.VISIBLE);
                recyclerViewNotification.setVisibility(View.INVISIBLE);
            } else {
                ui1.setVisibility(View.INVISIBLE);
                recyclerViewNotification.setVisibility(View.VISIBLE);
            }
        }
    }
    public void bestOfLuck(){

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref3 = database.getReference("Users");


        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (uid.isEmpty()){
                    //Log.i("uid","Empty");
                }
                else {
                    String bestluck = snapshot.child(uid).child("bestFuture").getValue().toString();
                    if (bestluck.equals("false")) {
                        Log.i("User", "isMember");
                    } else if (bestluck.equals("true")) {
                        mauth.signOut();
                        Intent intent = new Intent(view.getContext(), BestOfLuck.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    else if(type.equals("8")){
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
                finalArrayF.addAll(list2Team);
                timeF.addAll(timeStampsTeam);
                sortedFTime.addAll(timeF);
                Collections.sort(sortedFTime);
                count =0;
                for (int i=0;i<timeF.size();i++){
                    int index=timeF.indexOf(sortedFTime.get(i));
                    timeF.set(index,0);
                    try {
                        sortedArrayF.add(finalArrayF.get(index));
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
                loadData();
                checkData();
                adapter2();
            }
        });
    }

    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM, hh:mma").format(df);
        return vv;
    }
    public void sendToken(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        String token = pref.getString("Token","");
        String uid = pref.getString("uid","");
        if (uid.equals("")){

        }else {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference myref = db.getReference("Users");
            myref.child(uid).child("fcm").setValue(token);
        }
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
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        String json1 = gson.toJson(sortedArrayF);
        editor.putString("scrollbar",json);
        editor.putString("notifications",json1);
        editor.apply();

    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home", MODE_PRIVATE);
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