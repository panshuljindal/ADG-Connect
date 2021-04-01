package com.adgvit.internals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.adgvit.internals.Fragments.AlertsFragment.Misc.AlertsFragment;
import com.adgvit.internals.Fragments.HomeFragment.HomeFragment;
import com.adgvit.internals.Fragments.MomPage.MomFragment;
import com.adgvit.internals.Fragments.ProfilePage.ProfileFragment;
import com.adgvit.internals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;
    DatabaseReference myref;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        String token = pref.getString("Token","");
        String uid = pref.getString("uid","");
        mauth = FirebaseAuth.getInstance();
        if (uid.equals("")){

        }else {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
             myref= db.getReference("Users");
            myref.child(uid).child("fcm").setValue(token);
        }
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String bestluck = snapshot.child(uid).child("bestFuture").getValue().toString();
                if (bestluck.equals("false")){
                    Log.i("User","isMember");
                }
                else if(bestluck.equals("true")){
                    mauth.signOut();
                    Intent intent = new Intent(MainActivity.this, BestOfLuck.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //intent.putExtra("EXIT", true);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        smoothBottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);

        smoothBottomBar.setOnItemSelectedListener(i -> {
            Fragment selectedFragment = null;
            switch (i){
                case 0:
                    //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                    selectedFragment = new HomeFragment();
                    setTitle("Home");
                    break;
                case 1:
                    //Toast.makeText(this, "alerts", Toast.LENGTH_SHORT).show();
                    selectedFragment = new AlertsFragment();
                    setTitle("Alerts");
                    break;
                case 2:
                    //Toast.makeText(this, "mom", Toast.LENGTH_SHORT).show();
                    selectedFragment = new MomFragment();
                    setTitle("M.O.M");
                    break;
                case 3:
                    //Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                    selectedFragment = new ProfileFragment();
                    setTitle("Profile");
                    break;
            }

            //Frag Transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();


        return true;
        });
        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }
    Boolean doubleback=false;
    @Override
    public void onBackPressed() {
        if (doubleback) {
            //super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            Log.i("doubleback", doubleback.toString());
        } else {
            doubleback = true;

            Toast.makeText(this, "Please once again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                    Log.i("doubleback", doubleback.toString());
                }
            }, 2000);
            Log.i("doubleback", doubleback.toString());
        }
    }
}