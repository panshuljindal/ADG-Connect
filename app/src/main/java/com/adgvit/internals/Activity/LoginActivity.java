package com.adgvit.internals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adgvit.internals.Fragments.ProfilePage.ForgotPassword;
import com.adgvit.internals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login, login1btn;
    private ImageView bgImg;
    private TextView forgettext,cancel;
    private String emailid,pass,uid;
    private FirebaseAuth mauth;
    private TextView guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //toggleFullscreen(true); // toggling fulllscreen of half

        Animation Anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein1);
        Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.buttonup);
        Animation imgAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        Animation imgAnim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);


        ConstraintLayout loginCard = findViewById(R.id.loginCardConst);
        login1btn = findViewById(R.id.loginBtn1);
        bgImg = findViewById(R.id.landingimg);
        forgettext = findViewById(R.id.textViewForget);
        guest = findViewById(R.id.loginAsGuest);
        cancel = findViewById(R.id.cancel);

        bgImg.setAnimation(imgAnim);
        login1btn.setAnimation(buttonAnim);


        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.buttonLogin);

        mauth = FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()!=null){
            FirebaseUser user = mauth.getCurrentUser();
            uid = user.getUid();
            datasave();
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(myIntent);
        }

        clicklisteners();

        login1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toggleFullscreen(false);
                loginCard.setVisibility(View.VISIBLE);
                loginCard.setAnimation(Anim);
                //bgImg.setAnimation(imgAnim2);
                login1btn.setVisibility(View.INVISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCard.setAnimation(imgAnim2);
                loginCard.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login1btn.setVisibility(View.VISIBLE);
                    }
                },500);
//                bgImg.setAnimation(imgAnim);
//                login1btn.setAnimation(buttonAnim);
            }
        });
    }
    private void clicklisteners(){
        forgettext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword dialog = new ForgotPassword();
                dialog.show(getSupportFragmentManager(), "Reset Password");
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guest.setEnabled(false);
                mauth.signInWithEmailAndPassword("appledevelopersgroup@gmail.com","guest1234").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            guest.setEnabled(true);
                            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Guest login Successful", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            datasave();
                            try {
                                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("os").setValue("Android");
                            }
                            catch (Exception e){

                            }
                            sendToken();
                            startActivity(myIntent);
                        }
                        else {
                            guest.setEnabled(true);
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkempty()){
                    if(checkemail()){
                        if (isNetworkAvailable(v.getContext())){
                            login.setEnabled(false);
                            emailid = email.getText().toString();
                            pass = password.getText().toString();
                            mauth.signInWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if (mauth.getCurrentUser().isEmailVerified()){
                                            login.setEnabled(true);
                                            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                                            datasave();
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                            try {
                                                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("os").setValue("Android");
                                            }
                                            catch (Exception e){

                                            }
                                            sendToken();
                                            startActivity(myIntent);
                                        }
                                        else {
                                            mauth.signOut();
                                            Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else if(!task.isSuccessful()){
                                        login.setEnabled(true);
                                        Toast.makeText(LoginActivity.this, "Invalid email id or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Please connect to Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private void sendToken(){
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
                    uid = user.getUid();
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
                   // Toast.makeText(LoginActivity.this, "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private boolean checkempty(){
        if(email.getText().length()==0){
            Toast.makeText(this, "Please Enter a Email ID", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
            return false;

        }
        else if(password.getText().length()==0){
            Toast.makeText(this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
            return false;
        }
        return true;

    }
    public boolean checkemail(){
        return true;
    }
    private  boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}