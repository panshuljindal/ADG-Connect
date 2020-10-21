package com.example.adginternals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button logoutbtn;
    Button logoutlogoutbtn;
    Button logoutcancelbtn;

    Button resetPw;
    Button Team;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutbtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutbtn.setOnClickListener((View.OnClickListener) this);

        resetPw = (Button) view.findViewById(R.id.resetpwBtn);
        resetPw.setOnClickListener((View.OnClickListener) this);

        Team = (Button) view.findViewById(R.id.knowMoreBtn);
        Team.setOnClickListener((View.OnClickListener) this);

        return view;
        //return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutBtn) {
            //Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
            Dialog logoutDialog = new Dialog(getContext(),R.style.Theme_Dialog);
            logoutDialog.setContentView(R.layout.logout_dialog);
            // add functions for button
            logoutlogoutbtn = logoutDialog.findViewById(R.id.logoutLogoutBtn);
            logoutcancelbtn = logoutDialog.findViewById(R.id.cancelLogoutBtn);

            logoutDialog.show();

            logoutlogoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                    logoutDialog.dismiss();
                }
            });

            logoutcancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    logoutDialog.dismiss();
                }
            });


        }

        if (v.getId() == R.id.knowMoreBtn) {
            Toast.makeText(getContext(), "know more", Toast.LENGTH_SHORT).show();

        }

        if (v.getId() == R.id.resetpwBtn) {
            Toast.makeText(getContext(), "reset pw", Toast.LENGTH_SHORT).show();

        }
    }
}