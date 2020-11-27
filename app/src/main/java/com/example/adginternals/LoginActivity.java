package com.example.adginternals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button login;
    String emailid,pass,uid;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    }
    public void clicklisteners(){
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
        editor.putString("uid",uid);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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


                Log.i("UserData", emaili + " " + fcm + " " + name + " " + phone + " " + regNo + " " + mylist);



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