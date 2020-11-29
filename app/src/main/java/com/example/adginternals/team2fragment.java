package com.example.adginternals;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class team2fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list1_2;
    DatabaseReference myref;
    String team;
    List<String> mylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_team2fragment, container, false);

        recyclerView=view.findViewById(R.id.recyclerView2_3);
        list1_2=new ArrayList<>();
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        myref = db.getReference("Alerts").child("Team");

        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        team = pref.getString("teams","");
        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        mylist= new ArrayList<>(Arrays.asList(team2.split(", ")));

        addData();
        adapter();
        return view;
    }
    public void addData() {
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    alertdata ad = ds.getValue(alertdata.class);
                    String title = ad.getTitle();
                    String time = unixconvert(ad.getTime().toString());
                    String location = ad.getLocation();
                    String link = ad.getLink();
                    String id =ad.getId();
                    String type=ad.getType();
                    String type1 = "2";
                    if(type.equals(type1)) {
                        if (mylist.contains(type)) {
                            list1_2.add(new alertcardviewitem(title, time, location, link, id));
                        }
                    }
                }
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(){
        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list1_2);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(alertcardviewadapter);
        recyclerView.setLayoutManager(manager);
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM, hh:mma").format(df);
        return vv;
    }
}