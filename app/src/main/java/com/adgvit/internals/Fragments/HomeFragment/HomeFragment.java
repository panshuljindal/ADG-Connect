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
import com.adgvit.internals.Adapter.RecyclerView.Card1Adapter;
import com.adgvit.internals.Adapter.RecyclerView.Card2Adapter;
import com.adgvit.internals.Model.Alertdata;
import com.adgvit.internals.Model.Card1Item;
import com.adgvit.internals.Model.Card2Item;
import com.adgvit.internals.R;
import com.adgvit.internals.Model.ScrollClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView1,recyclerViewNotification;
    private View view;
    private ArrayList<Card1Item> list1;
    private ArrayList<Card2Item> list2Team,finalArrayF,sortedArrayF;
    private ArrayList<Integer> timeStampsTeam,sortedFTime,timeF;
    private DatabaseReference myref,myref2;
    private int count;
    private String team;
    private List<String> teamlist;
    private String uid;
    private String admin;
    ConstraintLayout ui1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void sendToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if (task.isSuccessful()){
                    String token = task.getResult();
                    // Log.i("token",token);
                    if (token.equals("")){

                    }
                    else {
                        String uid1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference myreference= db.getReference("Users");
                        myreference.child(uid1).child("fcm").setValue(token);
                    }

                }
            }
        });
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
        admin = pref.getString("isAdmin","");
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

    private void checkData1(){
        if (uid.equals("")){
            ui1.setVisibility(View.VISIBLE);
            recyclerViewNotification.setVisibility(View.INVISIBLE);
        }
        else {
            ui1.setVisibility(View.INVISIBLE);
            recyclerViewNotification.setVisibility(View.VISIBLE);
        }
    }
    private void checkData(){
        if (uid.isEmpty()){

        }
        else {
            if (sortedArrayF.size() == 0) {
                ui1.setVisibility(View.VISIBLE);
                recyclerViewNotification.setVisibility(View.INVISIBLE);
            } else {
                ui1.setVisibility(View.INVISIBLE);
                recyclerViewNotification.setVisibility(View.VISIBLE);
            }
        }
    }
    private void bestOfLuck(){

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref3 = database.getReference("Users");


        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (uid.isEmpty()){
                        //Log.i("uid","Empty");
                    }
                    else {
                        String bestluck = snapshot.child(uid).child("bestFuture").getValue().toString();
                        if (bestluck.equals("false")) {
                            // Log.i("User", "isMember");
                        } else if (bestluck.equals("true")) {
                            mauth.signOut();
                            Intent intent = new Intent(view.getContext(), BestOfLuck.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addData(){
        String title="";
        list1.add(new Card1Item(R.drawable.androidback,R.drawable.ic_android,"ANDROID",title));
        list1.add(new Card1Item(R.drawable.mlback,R.drawable.ic_ml,"Machine Learning",title));
        list1.add(new Card1Item(R.drawable.designback,R.drawable.ic_design,"Design",title));
        list1.add(new Card1Item(R.drawable.iosback,R.drawable.ic_ios,"iOS",title));
        list1.add(new Card1Item(R.drawable.webback,R.drawable.ic_web,"Web Dev",title));
        adapter1();
    }
    private void firebase(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    try {
                        ScrollClass sc = ds.getValue(ScrollClass.class);
                        String id= sc.getId();
                        String title = sc.getTitle();
                        String type = sc.getType();
                        if(type.equals("0")){
                            list1.add(new Card1Item(R.drawable.iosback,R.drawable.ic_ios,"iOS",title));
                        }
                        else if(type.equals("1")){
                            list1.add(new Card1Item(R.drawable.webback,R.drawable.ic_web,"Web Dev",title));
                        }
                        else if(type.equals("2")){
                            list1.add(new Card1Item(R.drawable.androidback,R.drawable.ic_android,"ANDROID",title));
                        }
                        else if(type.equals("3")){
                            list1.add(new Card1Item(R.drawable.mlback,R.drawable.ic_ml,"Machine Learning",title));
                        }
                        else if(type.equals("8")){
                            list1.add(new Card1Item(R.drawable.designback,R.drawable.ic_design,"Design",title));
                        }
                    }catch (Exception e){

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
                    try {
                        String uids = ds.child("users").getValue().toString();
                        //Log.i("uids",uids);
                        if (admin.equals("true")){
                            Alertdata ad = ds.getValue(Alertdata.class);
                            String title = ad.getTitle();
                            String time = unixconvert(ad.getTime().toString());
                            Long current = System.currentTimeMillis();
                            Long date = Long.valueOf(ad.getTime()) * 1000 + 86400000L;
                            if (current >= date) {
                                //Log.i("Date","Date Matched");
                            } else {
                                timeStampsTeam.add(ad.getTime());
                                list2Team.add(new Card2Item(title, time));
                            }
                        }
                        else {
                            if(uids.contains(uid)){
                                Alertdata ad = ds.getValue(Alertdata.class);
                                String title = ad.getTitle();
                                String time = unixconvert(ad.getTime().toString());
                                Long current = System.currentTimeMillis();
                                Long date = Long.valueOf(ad.getTime()) * 1000 + 86400000L*3;
                                if (current >= date) {
                                    //Log.i("Date","Date Matched");
                                } else {
                                    timeStampsTeam.add(ad.getTime());
                                    list2Team.add(new Card2Item(title, time));
                                }
                            }
                        }
                    }catch (Exception e){

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

    private String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM, hh:mma").format(df);
        return vv;
    }

    private void adapter1(){
        Card1Adapter adapter = new Card1Adapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(manager);
    }
    private void adapter2(){
        checkData();
        Card2Adapter adapter1= new Card2Adapter(getContext(),sortedArrayF);
        LinearLayoutManager manager1= new LinearLayoutManager(getContext());
        manager1.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotification.setAdapter(adapter1);
        recyclerViewNotification.setLayoutManager(manager1);
    }
    private void savaData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        String json1 = gson.toJson(sortedArrayF);
        editor.putString("scrollbar",json);
        editor.putString("notifications",json1);
        editor.apply();

    }
    private void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.home", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("scrollbar","");
        String json1 = preferences.getString("notifications","");
        Type type = new TypeToken<ArrayList<Card1Item>>() {}.getType();
        Type type1 = new TypeToken<ArrayList<Card2Item>>() {}.getType();
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