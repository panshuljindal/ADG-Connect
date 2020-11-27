package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class team2fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list1_2;
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
        addData();

        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list1_2);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(alertcardviewadapter);
        recyclerView.setLayoutManager(manager);
        return view;
    }
    public void addData(){
        list1_2.add(new alertcardviewitem("Android","12 Aug, 10PM","Google Meet","https://meet.google.com/ksb-cymm-gez",""));
    }
}