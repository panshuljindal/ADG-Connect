package com.adgvit.internals.Fragments.AlertsFragment.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adgvit.internals.Adapter.RecyclerView.alertcardviewadapter;
import com.adgvit.internals.Model.alertcardviewitem;
import com.adgvit.internals.Model.alertdata;
import com.adgvit.internals.R;
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
import java.util.Date;

public class core2fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list2;
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
        view= inflater.inflate(R.layout.fragment_core2fragment, container, false);

        recyclerView=view.findViewById(R.id.recyclerView1_2);
        list2=new ArrayList<>();
        SharedPreferences pref= view.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        uid = pref.getString("uid","");
        layout = view.findViewById(R.id.emptyMeetingsLayout);
        loadData();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref=db.getReference("Alerts").child("Core");
        addData();
        adapter();
        checkData();
        return view;
    }

    public void checkData(){
        if (list2.size()==0){
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            layout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    public void addData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    String uids = ds.child("users").getValue().toString();
                    if (uids.contains(uid)) {
                    alertdata ad = ds.getValue(alertdata.class);
                    String title = ad.getTitle();
                    String time = unixconvert(ad.getTime().toString());
                    String location = ad.getLocation();
                    String link = ad.getLink();
                    String id =ad.getId();
                    String type = ad.getType();
                    String type1="Meetings";
                    Long current = System.currentTimeMillis();
                    //Log.i("Current",String.valueOf(current));
                    Long date = Long.valueOf(ad.getTime())*1000+ 864000000L;
                    //Log.i("Date",String.valueOf(date));
                    if (current>=date){
                            //Log.i("Date","Date Matched");
                        }
                    else {
                        if(type.equals(type1)){
                            //Log.i("type",type);
                            list2.add(new alertcardviewitem(title,time,location,link,id));
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
        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list2);
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
        String json = gson.toJson(list2);
        editor.putString("core2",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.alert",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("core2","");
        Type type = new TypeToken<ArrayList<alertcardviewitem>>() {}.getType();
        list2 =gson.fromJson(json,type);
        if(list2==null){
            list2 =new ArrayList<>();
        }
    }

}