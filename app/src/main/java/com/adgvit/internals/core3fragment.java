package com.adgvit.internals;

import android.content.Context;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class core3fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list3;
    DatabaseReference myref;
    View view;
    String uid;
    ConstraintLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_core3fragment, container, false);

        recyclerView=view.findViewById(R.id.recylcerView1_3);

        list3=new ArrayList<>();
        layout = view.findViewById(R.id.emptyDutiesLayout);

        SharedPreferences pref= view.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        uid = pref.getString("uid","");

        loadData();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Alerts").child("Core");
        addData();
        adapter();
        checkData();
        return view;
    }

    public void checkData(){
        if (list3.size()==0){
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            layout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    public void addData() {
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uids = ds.child("users").getValue().toString();
                    if (uids.contains(uid)) {
                        alertdata ad = ds.getValue(alertdata.class);
                        String title = ad.getTitle();
                        String time = unixconvert(ad.getTime().toString());
                        String location = ad.getLocation();
                        String link = ad.getLink();
                        String id = ad.getId();
                        String type = ad.getType();
                        String type1 = "Duties";
                        Long current = System.currentTimeMillis();
                        Long date = Long.valueOf(ad.getTime()) * 1000 + 864000000L;
                        if (current >= date) {
                            //Log.i("Date","Date Matched");
                        } else {
                            if (type.equals(type1)) {
                                //Log.i("type",type);
                                list3.add(new alertcardviewitem(title, time, location, link, id));
                            }

                        }
                    }

                }
                checkData();
                savaData();
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                checkData();
            }
        });
    }
    public void adapter(){
        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list3);
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
    public void savaData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list3);
        editor.putString("core3",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.alert",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("core3","");
        Type type = new TypeToken<ArrayList<alertcardviewitem>>() {}.getType();
        list3 =gson.fromJson(json,type);
        if(list3==null){
            list3 =new ArrayList<>();
        }
    }
}