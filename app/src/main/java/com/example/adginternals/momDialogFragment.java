package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class momDialogFragment extends Fragment {

    private momItem argItem = null;
    
    public  momDialogFragment(momItem momItem){
        argItem = momItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mom_dialog, container, false);

        //connecting all the views
        Button okBtn = view.findViewById(R.id.momDialogOKBtn);
        TextView pointsDis = view.findViewById(R.id.momDialogPointsDiscussed);
        TextView title = view.findViewById(R.id.momDialogTitle);
        TextView date = view.findViewById(R.id.momDialogDate);
        TextView header = view.findViewById(R.id.momDialogHeader);

        if(argItem!=null){
            title.setText(argItem.getTitle());
            date.setText(argItem.getDate());
            header.setText(argItem.getHeader());

            //to get strings in bulletform
            CharSequence bulletedList = BulletTextUtils.makeBulletList(5,"Everyone has to get atleast 5 participants from their end.",
                    "Valid reason has to be provided for not attending the meeting in the ADG Internals app.",
                    "Desk duties will be alloted and everyone is asked to report on time.");
            pointsDis.setText(bulletedList);
        }



        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}