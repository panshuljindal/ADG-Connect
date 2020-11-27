package com.example.adginternals;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MomFragment extends Fragment {
    RecyclerView recyclerView;//String t[] , d[] ;
    ArrayList<String> t = new ArrayList<String>();
    ArrayList<String> d = new ArrayList<String>();
    EditText momSearchBar;
    MyAdapter myAdapter;

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
        Collections.addAll(t, res.getStringArray(R.array.momTitleStrings));
        Collections.addAll(d, res.getStringArray(R.array.momDateStrings));


        myAdapter = new MyAdapter(getContext() , t , d);

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
        ArrayList<String> filteredList = new ArrayList<>();

        for(String item : d){
            if(item.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myAdapter.filterList(filteredList);
}
}

