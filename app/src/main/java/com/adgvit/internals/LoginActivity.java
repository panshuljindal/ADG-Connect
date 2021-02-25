package com.adgvit.internals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText email,password;
    Button login, login1btn;
    ImageView bgImg;
    TextView forgettext;
    String emailid,pass,uid;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///toggleFullscreen(true); // toggling fulllscreen of half

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
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
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

    private void toggleFullscreen(boolean fullscreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullscreen)
        {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        else
        {
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        getWindow().setAttributes(attrs);
    }


    public void clicklisteners(){
        forgettext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotpassword dialog = new forgotpassword();
                dialog.show(getSupportFragmentManager(), "Reset Password");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkempty()){
                    if(checkemail()){
                        emailid = email.getText().toString();
                        pass = password.getText().toString();
                        mauth.signInWithEmailAndPassword(emailid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(myIntent);
                                    datasave();
                                    Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                }
                                else if(!task.isSuccessful()){
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
    public void datasave(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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

                String team = snapshot.child(uid).child("teams").getValue().toString();
                String team1 = team.replace("[", "");
                String team2 = team1.replace("]", "");
                List<String> mylist = new ArrayList<>(Arrays.asList(team2.split(", ")));
                editor.putString("teams",team);
                editor.apply();


                //Log.i("UserData", emaili + " " + fcm + " " + name + " " + phone + " " + regNo + " " + mylist);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean checkempty(){
        if(email.getText().length()==0){
            email.setError("Please enter an email id");
            return false;

        }
        else if(password.getText().length()==0){
            password.setError("Please enter and password");
            return false;
        }
        return true;

    }
    public boolean checkemail(){
        String tempemail=email.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            return true;
        }
        email.setError("Please enter a valid email id");
        email.requestFocus();
        return false;
    }
}