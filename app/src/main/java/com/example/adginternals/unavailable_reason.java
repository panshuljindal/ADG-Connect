package com.example.adginternals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class unavailable_reason extends Fragment {
    EditText reason;
    Button post,cancel;
    DatabaseReference myref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String mid,uid;
    String reasons;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_unavailable_reason, container, false);
        post = view.findViewById(R.id.buttonpostreason);
        cancel = view.findViewById(R.id.buttonreasoncancel);
        reason = view.findViewById(R.id.editTextReason);

        pref = view.getContext().getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
        mid=pref.getString("mid","");
        Log.i("mid",mid);

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        uid=pref1.getString("uid","");
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        myref = db.getReference("AlertAttendace");



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasons = reason.getText().toString();
                Log.i("reason",reasons);
                myref.child(mid).child(uid).setValue(reasons);
            }
        });
        return view;
    }
}