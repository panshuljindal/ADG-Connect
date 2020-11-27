package com.example.adginternals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button logoutbtn;
    Button logoutlogoutbtn;
    Button logoutcancelbtn;

    Button resetPw;
    Button Team;
    FirebaseAuth mauth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutbtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutbtn.setOnClickListener((View.OnClickListener) this);

        resetPw = (Button) view.findViewById(R.id.resetpwBtn);
        resetPw.setOnClickListener((View.OnClickListener) this);

        Team = (Button) view.findViewById(R.id.knowMoreBtn);
        Team.setOnClickListener((View.OnClickListener) this);

        mauth=FirebaseAuth.getInstance();

        return view;
        //return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutBtn) {
            Dialog logoutDialog = new Dialog(getContext(),R.style.Theme_Dialog);
            logoutDialog.setContentView(R.layout.logout_dialog);
            // add functions for button
            logoutlogoutbtn = logoutDialog.findViewById(R.id.logoutLogoutBtn);
            logoutcancelbtn = logoutDialog.findViewById(R.id.cancelLogoutBtn);

            logoutDialog.show();

            logoutlogoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mauth.signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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