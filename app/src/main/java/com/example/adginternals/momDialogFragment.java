package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class momDialogFragment extends Fragment {
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mom_dialog, container, false);

        Button okBtn = view.findViewById(R.id.momDialogOKBtn);
        TextView pointsDis = view.findViewById(R.id.momDialogPointsDiscussed);

        CharSequence bulletedList = BulletTextUtils.makeBulletList(5,"Everyone has to get atleast 5 participants from their end.",
                "Valid reason has to be provided for not attending the meeting in the ADG Internals app.",
            "Desk duties will be alloted and everyone is asked to report on time.");
        pointsDis.setText(bulletedList);

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