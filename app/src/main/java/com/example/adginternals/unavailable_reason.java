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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class unavailable_reason extends Fragment {
    EditText reason;
    Button post,cancel;
    TextView text1,text2;
    DatabaseReference myref,myref1;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String mid,name,uid,title,time;
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
        text1 = view.findViewById(R.id.alertcardtext1_1);
        text2 = view.findViewById(R.id.alertcardtext2_1);

        pref = view.getContext().getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
        editor = pref.edit();
        mid=pref.getString("mid","");
        title=pref.getString("title","");
        time=pref.getString("time","");
        editor.clear();
        editor.apply();
        Log.i("mid",mid);

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        name=pref1.getString("name","");
        uid=pref1.getString("uid","");
        editor1.clear();
        editor1.apply();

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        myref = db.getReference("AlertAttendace");
        myref1=db.getReference("Users");

        text1.setText(title);
        text2.setText(time);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasons = reason.getText().toString();
                myref.child(mid).child(name).setValue(reasons);
                myref1.child(uid).child("Meetings").child(mid).setValue(reasons);
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }
}