package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
public class MomFragment extends Fragment {
    RecyclerView recyclerView;String t[] , d[] ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mom, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mom_recycler);

        t = getResources().getStringArray(R.array.momTitleStrings);
        d = getResources().getStringArray(R.array.momDateStrings);

        MyAdapter myAdapter = new MyAdapter(getContext() , t , d);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         return  view;
    }
}