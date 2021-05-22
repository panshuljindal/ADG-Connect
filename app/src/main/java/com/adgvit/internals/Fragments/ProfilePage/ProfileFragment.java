package com.adgvit.internals.Fragments.ProfilePage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adgvit.internals.Activity.BestOfLuck;
import com.adgvit.internals.Activity.LoginActivity;
import com.adgvit.internals.Activity.MainActivity;
import com.adgvit.internals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button logoutbtn;
    private Button logoutlogoutbtn,logoutReset;
    private Button logoutcancelbtn,cancelReset;
    private TextView initials;
    private String initials1="";
    private Button resetPw;
    private Button Team;
    private FirebaseAuth mauth;
    private String name,regNo,email,phone,team,domain,position;
    private TextView profileName,regNoText,userEmail,userContact,textDomain;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<String> teamlist;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_profile, container, false);
        bestOfLuck();
        initials1="";

        logoutbtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutbtn.setOnClickListener((View.OnClickListener) this);
        initials = view.findViewById(R.id.initialsText);

        resetPw = (Button) view.findViewById(R.id.resetpwBtn);
        resetPw.setOnClickListener((View.OnClickListener) this);

        Team = (Button) view.findViewById(R.id.knowMoreBtn);
        Team.setOnClickListener((View.OnClickListener) this);


        profileName=view.findViewById(R.id.profileName);
        regNoText = view.findViewById(R.id.regNo);
        userEmail = view.findViewById(R.id.userEmail);
        userContact = view.findViewById(R.id.userContact);
        textDomain = view.findViewById(R.id.domain);

        mauth=FirebaseAuth.getInstance();

        pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        editor = pref.edit();
        name = pref.getString("name","");
        regNo = pref.getString("regNo","");
        email = pref.getString("emailid","");
        phone = pref.getString("phone","");
        team = pref.getString("teams","");
        position = pref.getString("position","");
        String name1 = name;
        name1 = name1.trim();
        String words[] = name1.split(" ");
        for(String word : words) {
            try {
                //Log.i("Initial",Character.toUpperCase(word.charAt(0)) + " ");
                initials1 = initials1 +Character.toUpperCase(word.charAt(0));
            }
            catch (StringIndexOutOfBoundsException e){
                //Log.i("Exception","String Index Out of bounds");
            }

        }
        initials.setText(initials1);


        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        teamlist= new ArrayList<>(Arrays.asList(team2.split(", ")));
        domain = teamlist.get(0);
        //Log.i("Domain",domain);
        profileName.setText(name);
        regNoText.setText(regNo);
        userEmail.setText(email);
        userContact.setText("+91 "+phone);
        if (position.equals("")){
            if(domain.equals("0")){
                textDomain.setText("iOS Development");
            }
            else if(domain.equals("1")){
                textDomain.setText("Web Development");
            }
            else if(domain.equals("2")){
                textDomain.setText("Android Development");
            }
            else if(domain.contains("3")){
                textDomain.setText("Machine Learning ");
            }
            else if(domain.contains("4")){
                textDomain.setText("Logistics");
            }
            else if(domain.contains("5")){
                textDomain.setText("Sponsorship");
            }
            else if(domain.contains("6")){
                textDomain.setText("Marketing");
            }
            else if(domain.contains("7")){
                textDomain.setText("Design");
            }
            else if(domain.contains("8")){
                textDomain.setText("Video Editing");
            }
        }
        else {
            textDomain.setText(position);
        }
        datasave();
        return view;
    }
    private void bestOfLuck(){

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        String uid1 = pref1.getString("uid","");
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref3 = database.getReference("Users");
        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (uid1.isEmpty()){
                        //Log.i("uid","Empty");
                    }
                    else {
                        String bestluck = snapshot.child(uid1).child("bestFuture").getValue().toString();
                        if (bestluck.equals("false")) {
                            //Log.i("User", "isMember");
                        } else if (bestluck.equals("true")) {
                            mauth.signOut();
                            Intent intent = new Intent(view.getContext(), BestOfLuck.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetpwBtn) {
            Dialog resetDialog = new Dialog(getContext(),R.style.Theme_Dialog);
            resetDialog.setContentView(R.layout.reset_password_dialog);
            // add functions for button
            logoutReset = resetDialog.findViewById(R.id.logoutResetBtn);
            cancelReset = resetDialog.findViewById(R.id.cancelResetBtn);

            resetDialog.show();

            logoutReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   SharedPreferences pref = v.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
                   String emailID = pref.getString("emailid","");
                   mauth.sendPasswordResetEmail(emailID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(v.getContext(), "Check email", Toast.LENGTH_LONG).show();
                                resetDialog.dismiss();
                                //startActivity(new Intent(v.getContext(),LoginActivity.class));
                            }
                            else{
                                Toast.makeText(v.getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                                resetDialog.dismiss();

                            }
                        }
                    });

                }
            });

            cancelReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    resetDialog.dismiss();
                }
            });


        }

        if (v.getId() == R.id.knowMoreBtn) {
            Fragment aboutus1 = new AboutUs();
            FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,aboutus1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

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
                    clearData();
                    Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    logoutDialog.dismiss();

                }
            });

            logoutcancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    logoutDialog.dismiss();
                }
            });
        }
    }
    private void clearData(){
        SharedPreferences userdata = getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser = userdata.edit();
        editoruser.clear();
        editoruser.apply();
        SharedPreferences userdata1 = getContext().getSharedPreferences("com.adgvit.com.alert",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser1 = userdata1.edit();
        editoruser1.clear();
        editoruser1.apply();
        SharedPreferences userdata2 = getContext().getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser2 = userdata2.edit();
        editoruser2.clear();
        editoruser2.apply();
        SharedPreferences preferences = getContext().getSharedPreferences("com.adgvit.com.mom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = preferences.edit();
        editor3.clear();
        editor3.apply();
    }
    private void datasave(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    FirebaseUser user = mauth.getCurrentUser();
                    String uid = user.getUid();

                    editor.putString("uid",uid);
                    String emaili = snapshot.child(uid).child("email").getValue().toString();
                    editor.putString("emailid", emaili);

                    String fcm = snapshot.child(uid).child("fcm").getValue().toString();
                    editor.putString("fcm", fcm);

                    String name = snapshot.child(uid).child("name").getValue().toString();
                    editor.putString("name", name);

                    String phone = snapshot.child(uid).child("phone").getValue().toString();
                    editor.putString("phone", phone);

                    String regNo = snapshot.child(uid).child("regNo").getValue().toString();
                    editor.putString("regNo", regNo);

                    String bestLuck = snapshot.child(uid).child("bestFuture").getValue().toString();
                    editor.putString("bestLuck",bestLuck);

                    String isAdmin = snapshot.child(uid).child("isAdmin").getValue().toString();
                    editor.putString("isAdmin",isAdmin);
                    String position = snapshot.child(uid).child("position").getValue().toString();
                    editor.putString("position",position);
                    String team = snapshot.child(uid).child("teams").getValue().toString();
                    String team1 = team.replace("[", "");
                    String team2 = team1.replace("]", "");
                    List<String> mylist = new ArrayList<>(Arrays.asList(team2.split(", ")));
                    editor.putString("teams",team);
                    editor.apply();

                }
                catch (Exception e){
                    //Toast.makeText(view.getContext(), "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(view.getContext(), "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}