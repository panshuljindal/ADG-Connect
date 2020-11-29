package com.example.adginternals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
public class HomeFragment extends Fragment {
    RecyclerView recyclerView1,recyclerViewNotification;
    ArrayList<card1item> list1;
    ArrayList<card2item> list2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerview1);
        recyclerViewNotification= (RecyclerView) view.findViewById(R.id.recyclerviewNotifications);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        addData();

        card1adapter adapter = new card1adapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(manager);

        card2adapter adapter1= new card2adapter(getContext(),list2);
        LinearLayoutManager manager1= new LinearLayoutManager(getContext());
        manager1.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotification.setAdapter(adapter1);
        recyclerViewNotification.setLayoutManager(manager1);

        return view;
    }
    public void addData(){
        list1.add(new card1item(R.drawable.androidback,R.drawable.andicon,"ANDROID","New Event Coming Up","Development of an App is underway","Meeting Scheduled on Monday"));
        list1.add(new card1item(R.drawable.iosback,R.drawable.andicon,"IOS","New Event Coming Up","Development of an App is underway","Meeting Scheduled on Tuesday"));
        list1.add(new card1item(R.drawable.androidback,R.drawable.andicon,"MACHINE LEARNING","New Event Coming Up","Development of an App is underway","Meeting Scheduled on Wednesday"));
        list2.add(new card2item("Core Meeting","06 Aug, 10PM"));
        list2.add(new card2item("iOS Team Meeting","10 Aug, 09PM"));
        list2.add(new card2item("Task-1 Deadline","12 Aug, 12PM"));
        list2.add(new card2item("Event Poster ","12 Aug, 10PM"));
        list2.add(new card2item("Android Meeting ","12 Nov, 7PM"));
        Log.i("list1",list1.toString());
        Log.i("list2",list2.toString());
    }
}