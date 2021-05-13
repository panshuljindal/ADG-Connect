package com.adgvit.internals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login, login1btn;
    private ImageView bgImg;
    private TextView forgettext;
    private String emailid,pass,uid;
    private FirebaseAuth mauth;
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


        CardView loginCard = findViewById(R.id.loginCard);
        login1btn = findViewById(R.id.loginBtn1);
        bgImg = findViewById(R.id.landingimg);
        forgettext = findViewById(R.id.textViewForget);

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
                loginCard.setAnimation(Anim);
                //bgImg.setAnimation(imgAnim2);
                loginCard.setVisibility(View.VISIBLE);
                login1btn.setVisibility(View.GONE);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkempty()){
                    if(checkemail()){
                        login.setEnabled(false);
                        emailid = email.getText().toString();
                        pass = password.getText().toString();
                        mauth.signInWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    login.setEnabled(true);
                                    Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(myIntent);
                                    datasave();
                                    Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                }
                                else if(!task.isSuccessful()){
                                    login.setEnabled(true);
                                    email.setText("");
                                    password.setText("");
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
                    String team = snapshot.child(uid).child("teams").getValue().toString();
                    String team1 = team.replace("[", "");
                    String team2 = team1.replace("]", "");
                    List<String> mylist = new ArrayList<>(Arrays.asList(team2.split(", ")));
                    editor.putString("teams",team);
                    editor.apply();

                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Error Occurred. Please try again later", Toast.LENGTH_SHORT).show();
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
            return false;

        }
        else if(password.getText().length()==0){
            Toast.makeText(this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    private boolean checkemail(){
        String tempemail=email.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("^[a-z]+.[a-z]*[0-9]?20[0-9][0-9]@vitstudent.ac.in$");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            return true;
        }
        Toast.makeText(this, "Please Enter a Valid Email ID", Toast.LENGTH_SHORT).show();
        email.requestFocus();
        return false;
    }
}