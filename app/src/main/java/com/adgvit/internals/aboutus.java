package com.adgvit.internals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class aboutus extends Fragment {
    RecyclerView ios,android;
    ArrayList<aboutusitem> listios,listandroid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_aboutus, container, false);

        android = view.findViewById(R.id.recyclerViewiOS);
        ios= view.findViewById(R.id.recyclerViewAndroid);
        listios = new ArrayList<>();
        listandroid = new ArrayList<>();
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

        Button back = view.findViewById(R.id.buttonaboutusback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
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
    }
}