package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class core1fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<alertcardviewitem> list1;
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
        addData();

        alertcardviewadapter alertcardviewadapter = new alertcardviewadapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(alertcardviewadapter);
        recyclerView.setLayoutManager(manager);
        return view;
    }
    public void addData(){
        list1.add(new alertcardviewitem("Core Meeting","06 Aug, 10PM","Google meet","https://meet.google.com/sso-cymm-gez"));
        list1.add(new alertcardviewitem("Gravitas Work","06 Aug, 12PM","SMV Portico","Room 101"));
        list1.add(new alertcardviewitem("Core Meeting","06 Aug, 10PM","Google meet","https://meet.google.com/sso-cymm-gez"));
        list1.add(new alertcardviewitem("Gravitas Work","06 Aug, 12PM","SMV Portico","Room 101"));

    }

}