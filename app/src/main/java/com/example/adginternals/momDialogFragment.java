package com.example.adginternals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link momDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class momDialogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public momDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment momDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static momDialogFragment newInstance(String param1, String param2) {
        momDialogFragment fragment = new momDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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