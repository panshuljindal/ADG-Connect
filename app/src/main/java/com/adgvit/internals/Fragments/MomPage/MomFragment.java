package com.adgvit.internals.Fragments.MomPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adgvit.internals.Activity.BestOfLuck;
import com.adgvit.internals.Model.MomDetails;
import com.adgvit.internals.Adapter.RecyclerView.MyAdapter;
import com.adgvit.internals.R;
import com.adgvit.internals.Model.MomItem;
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
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class MomFragment extends Fragment {
    RecyclerView recyclerView;//String t[],d[];

    ArrayList<MomItem> momItems;
    EditText momSearchBar;
    MyAdapter myAdapter;
    DatabaseReference myref;
    String team;
    ArrayList<String> mylist;
    View view;
    String uid,admin;
    ConstraintLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mom, container, false);
        bestOfLuck();

        recyclerView = (RecyclerView) view.findViewById(R.id.mom_recycler);
        momItems = new ArrayList<>();
        layout = view.findViewById(R.id.emptyMomLayout);

        loadData();
        Resources res = getResources();
        //fetch from firebase and add here
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        myref = db.getReference("MOMS");

        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        team = pref.getString("teams","");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        admin = pref.getString("isAdmin","");
        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        mylist= new ArrayList<>(Arrays.asList(team2.split(", ")));
        addData();
        adapter();
        momSearchBar = (EditText) view.findViewById(R.id.momSearch);
        momSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                        filter(s.toString());
            }
        });
        checkData();
         return  view;
    }
    public void bestOfLuck(){

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        String uid1 = pref1.getString("uid","");
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
                           //Log.i("User", "isMember");
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
    public void checkData(){
        if (momItems.size()==0){
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else {
            layout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void filter(String text) {
        ArrayList<MomItem> filteredList = new ArrayList<>();
        for(MomItem item : momItems){
            if(item.getDate().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        myAdapter.filterList(filteredList);

    }
    public void addData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                momItems.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    try {
                        String uids = ds.child("users").getValue().toString();
                        if (admin.equals("true")){
                            MomDetails momdetails = ds.getValue(MomDetails.class);
                            String header = momdetails.getHeader();
                            String time = unixconvert(momdetails.getTime().toString());
                            String title = momdetails.getTitle();
                            String team = momdetails.getTeam();
                            String mid = momdetails.getId();
                            Long current = System.currentTimeMillis();
                            Long date = Long.valueOf(momdetails.getTime()) * 1000 + 86400000L*30;

                            String point = snapshot.child(mid).child("points").getValue().toString();
                            String points1 = point.replace("[","");
                            String points2 = points1.replace("]","");
                            ArrayList<String> points= new ArrayList<>(Arrays.asList(points2.split(", ")));
                            if (current >= date) {

                            } else {
                                if (mylist.contains(team)) {

                                }
                                momItems.add(new MomItem(time, title, header, points2));
                            }
                        }
                        else {
                            if(uids.contains(uid)){
                                MomDetails momdetails = ds.getValue(MomDetails.class);
                                String header = momdetails.getHeader();
                                String time = unixconvert(momdetails.getTime().toString());
                                String title = momdetails.getTitle();
                                String team = momdetails.getTeam();
                                String mid = momdetails.getId();
                                Long current = System.currentTimeMillis();
                                Long date = Long.valueOf(momdetails.getTime()) * 1000 + 86400000L*30;

                                String point = snapshot.child(mid).child("points").getValue().toString();
                                String points1 = point.replace("[","");
                                String points2 = points1.replace("]","");
                                ArrayList<String> points= new ArrayList<>(Arrays.asList(points2.split(", ")));
                                if (current >= date) {

                                } else {
                                    if (mylist.contains(team)) {

                                    }
                                    momItems.add(new MomItem(time, title, header, points2));
                                }
                            }
                        }
                    }
                    catch (Exception e){

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
        myAdapter = new MyAdapter(getContext(),momItems);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM yyyy").format(df);
        return vv;
    }
    public void savaData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.mom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(momItems);
        editor.putString("mom",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.mom",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("mom","");
        Type type = new TypeToken<ArrayList<MomItem>>() {}.getType();
        momItems =gson.fromJson(json,type);
        if(momItems==null){
            momItems =new ArrayList<>();
        }
    }
}

