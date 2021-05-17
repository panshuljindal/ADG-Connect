package com.adgvit.internals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.adgvit.internals.Fragments.AlertsFragment.Misc.AlertsFragment;
import com.adgvit.internals.Fragments.HomeFragment.HomeFragment;
import com.adgvit.internals.Fragments.MomPage.MomFragment;
import com.adgvit.internals.Fragments.MomPage.MomDialogFragment;
import com.adgvit.internals.Fragments.ProfilePage.ProfileFragment;
import com.adgvit.internals.Fragments.ProfilePage.AboutUs;
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
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;
    DatabaseReference myref;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();
        //sendToken();
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
        datasave();

    }
    private void datasave(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
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
                    //Toast.makeText(MainActivity.this, "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(MainActivity.this, "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if (task.isSuccessful()){
                    String token = task.getResult();
                    // Log.i("token",token);
                    if (token.equals("")){

                    }
                    else {
                        String uid1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference myreference= db.getReference("Users");
                        myreference.child(uid1).child("fcm").setValue(token);
                    }

                }
            }
        });
    }
    Boolean doubleback=false;
    @Override
    public void onBackPressed() {

        tellFragments();
    }
    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof AboutUs)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
            else if (f!=null && f instanceof MomDialogFragment)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MomFragment()).commit();
            else if (f!=null && f instanceof MomFragment){
                HomeFragment home = new HomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                smoothBottomBar.setItemActiveIndex(0);
            }
            else if (f!=null && f instanceof ProfileFragment){
                HomeFragment home = new HomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                smoothBottomBar.setItemActiveIndex(0);
            }
            else if (f!=null && f instanceof AlertsFragment){
                HomeFragment home = new HomeFragment();
                FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                smoothBottomBar.setItemActiveIndex(0);
            }
            else if (f!=null && f instanceof HomeFragment){
                if (doubleback) {
            //super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            //Log.i("doubleback", doubleback.toString());
        } else {
            doubleback = true;

            Toast.makeText(this, "Please once again BACK to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                    //Log.i("doubleback", doubleback.toString());
                }
            }, 2000);
            //Log.i("doubleback", doubleback.toString());
        }
            }
        }
    }
}