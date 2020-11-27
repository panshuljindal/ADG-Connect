package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class core3fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_core3fragment, container, false);

        recyclerView=view.findViewById(R.id.recylcerView1_3);

        list3=new ArrayList<>();
        addData();

        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list3);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(alertcardviewadapter);
        recyclerView.setLayoutManager(manager);

        return view;
    }
    public void addData(){
        list3.add(new alertcardviewitem("Gravitas Work","06 Aug, 12PM","SMV Portico","Room 101",""));
    }
}