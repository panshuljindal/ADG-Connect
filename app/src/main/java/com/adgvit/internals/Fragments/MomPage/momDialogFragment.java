package com.adgvit.internals.Fragments.MomPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adgvit.internals.Adapter.RecyclerView.mompointadapter;
import com.adgvit.internals.R;
import com.adgvit.internals.Model.momItem;
import com.adgvit.internals.Model.mompoint;

import java.util.ArrayList;
import java.util.Arrays;

public class momDialogFragment extends Fragment {

    private momItem argItem = null;
    RecyclerView pointRecycler;
    ArrayList<mompoint> list1;
    ArrayList<String> pointlist;
    public  momDialogFragment(momItem momItem){
        argItem = momItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mom_dialog, container, false);

        //connecting all the views
        Button okBtn = view.findViewById(R.id.momDialogOkBtn);
        pointRecycler = view.findViewById(R.id.pointView);
        TextView title = view.findViewById(R.id.momDialogTitle);
        TextView date = view.findViewById(R.id.momDialogDate);
        TextView header = view.findViewById(R.id.momDialogHeader);

        list1 = new ArrayList<>();
        if(argItem!=null){

            title.setText(argItem.getTitle());
            date.setText(argItem.getDate());
            header.setText(argItem.getHeader());
            String points=argItem.getPoints();
            pointlist= new ArrayList<>(Arrays.asList(points.split(", ")));
        }
        for (int i = 0; i < pointlist.size(); i++) {
            String po= pointlist.get(i);
            Log.i("Point",po);
            list1.add(new mompoint(po));
        }

        Log.i("list1",list1.toString());
        mompointadapter adapter = new mompointadapter(getContext(),list1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        pointRecycler.setAdapter(adapter);
        pointRecycler.setLayoutManager(manager);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back
                getFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }
}