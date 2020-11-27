package com.example.adginternals;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MomFragment extends Fragment {
    RecyclerView recyclerView;//String t[] , d[] ;

    ArrayList<momItem> momItems = new ArrayList<>();
    EditText momSearchBar;
    MyAdapter myAdapter;
    DatabaseReference myref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mom, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mom_recycler);

        Resources res = getResources();
        //fetch from firebase and add here
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        myref = db.getReference("MOMS");
        momItems.clear();
        addData();
        momItems.add(new momItem("24 October 2020","Core Meeting MOM",
                "A meeting was called by the Board regarding work related to the upcoming event that is to be held on 20 Oct 2020.",
                new String[]{"Everyone has to get atleast 5 participants from their end.",
                        "Desk duties will be alloted and everyone is asked to report on time.",
                "Valid reason has to be provided for not attending the meeting in the ADG Internals app."}));

        momItems.add(new momItem("02 November 2020","Board Meeting MOM",
                "A meeting was called by the Board regarding work related to the upcoming event that is to be held on 20 Oct 2020.",
                new String[]{"Everyone has to get atleast 5 participants from their end.",
                        "Desk duties will be alloted and everyone is asked to report on time.",
                        "Valid reason has to be provided for not attending the meeting in the ADG Internals app."}));



        myAdapter = new MyAdapter(getContext(),momItems);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

         return  view;
    }

    private void filter(String text) {
        ArrayList<momItem> filteredList = new ArrayList<>();

        for(momItem item : momItems){
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
                for(DataSnapshot ds:snapshot.getChildren()){
                   //getMomdetails mom = ds.getValue(getMomdetails.class);
                    //String header = mom.getHeader();
                    //Log.i("header",header);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

