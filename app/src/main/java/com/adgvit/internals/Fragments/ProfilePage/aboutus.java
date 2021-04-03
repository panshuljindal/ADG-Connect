package com.adgvit.internals.Fragments.ProfilePage;

import android.content.Intent;
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
import android.widget.Button;

import com.adgvit.internals.Activity.BestOfLuck;
import com.adgvit.internals.R;
import com.adgvit.internals.Adapter.RecyclerView.aboutusAdapter;
import com.adgvit.internals.Model.aboutusitem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class aboutus extends Fragment {
    RecyclerView ios,android,web;
    ArrayList<aboutusitem> listios,listandroid,listweb;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_aboutus, container, false);
        bestOfLuck();

        android = view.findViewById(R.id.recyclerViewAndroid);
        ios= view.findViewById(R.id.recyclerViewiOS);
        web = view.findViewById(R.id.recyclerViewWeb);
        listios = new ArrayList<>();
        listandroid = new ArrayList<>();
        listweb = new ArrayList<>();
        addData();

        aboutusAdapter adapter = new aboutusAdapter(getContext(),listios);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        ios.setAdapter(adapter);
        ios.setLayoutManager(manager);

        aboutusAdapter adapter1 = new aboutusAdapter(getContext(),listandroid);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager1.setOrientation(RecyclerView.HORIZONTAL);
        android.setAdapter(adapter1);
        android.setLayoutManager(manager1);

        aboutusAdapter adapter2 = new aboutusAdapter(getContext(),listweb);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        manager2.setOrientation(RecyclerView.HORIZONTAL);
        web.setAdapter(adapter2);
        web.setLayoutManager(manager2);

        Button back = view.findViewById(R.id.buttonaboutusback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
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
                if (uid1.isEmpty()){
                    Log.i("uid","Empty");
                }
                else {
                    String bestluck = snapshot.child(uid1).child("bestFuture").getValue().toString();
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
        listandroid.add(new aboutusitem(R.drawable.mom_cardbg,"Panshul Jindal",
                "https://www.linkedin.com/in/panshul-jindal-392746199",
                "https://github.com/panshuljindal",
                "panshuljindal@gmail.com"));
        listandroid.add(new aboutusitem(R.drawable.mom_cardbg,"Arupam Saha",
                "https://www.linkedin.com/in/arupam-kumar-saha-310653191/",
                "https://github.com/arupam",
                "sahaarupam37@gmail.com"));
        listios.add(new aboutusitem(R.drawable.mom_cardbg,"Riddhi Gupta",
                "http://www.linkedin.com/in/riddhi-gupta-95858b1b3",
                "https://github.com/riddhi-30",
                "riddhigupta01@gmail.com"));
        listios.add(new aboutusitem(R.drawable.mom_cardbg,"Utkarsh Dixit",
                "http://www.linkedin.com/in/fakeyudi",
                "http://github.com/fakeyudi",
                "udixit419@gmail.com"));
        listios.add(new aboutusitem(R.drawable.mom_cardbg,"Aryan Kothari",
                "https://www.linkedin.com/in/aaryankotharii",
                "https://github.com/aaryankotharii",
                "aaryan.kothari@gmail.com"));
        listios.add(new aboutusitem(R.drawable.mom_cardbg,"Harsh Londhekar",
                "https://www.linkedin.com/in/harshlondhekar",
                "https://github.com/Harsh4601",
                "londhekarh4601@gmail.com"));
        listweb.add(new aboutusitem(R.drawable.mom_cardbg,"Anmol Bansal"
        ,"https://www.linkedin.com/in/anmolbansal7","https://github.com/anmolbansal7",
                "abanmolbansal5@gmail.com"));
        listweb.add(new aboutusitem(R.drawable.mom_cardbg,"Dev Sharma"
                ,"https://www.linkedin.com/in/cryptus-neoxys/",
                "https://github.com/cryptus-neoxys",
                "sharma.dev4242@gmail.com"));
        listweb.add(new aboutusitem(R.drawable.mom_cardbg,"Vidushi Gupta"
                ,"https://www.linkedin.com/in/vidushiguptaa/",
                "https://github.com/vidushig08",
                "vidushigupta08@gmail.com"));
    }
}