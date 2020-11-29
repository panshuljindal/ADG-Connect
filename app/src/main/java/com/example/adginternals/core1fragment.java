package com.example.adginternals;

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
import java.util.Date;
import java.util.List;

public class core1fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list1;
    FirebaseDatabase database;
    DatabaseReference myref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_core1fragment, container, false);
        recyclerView=view.findViewById(R.id.recylerView1_1);
        list1=new ArrayList<>();
        database =FirebaseDatabase.getInstance();
        myref =database.getReference("Alerts").child("Core");
        addData();
        adapter();
        return view;
    }



    public void addData(){

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1.clear();
                for(DataSnapshot ds: datasnapshot.getChildren()){
                    alertdata ad = ds.getValue(alertdata.class);
                    String title = ad.getTitle();
                    String time = unixconvert(ad.getTime().toString());
                    String location = ad.getLocation();
                    String link = ad.getLink();
                    String id =ad.getId();
                    list1.add(new alertcardviewitem(title,time,location,link,id));
                }
                adapter();
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
    public void adapter(){
        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(alertcardviewadapter);
        recyclerView.setLayoutManager(manager);
    }
}