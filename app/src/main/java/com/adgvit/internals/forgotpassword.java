package com.adgvit.internals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgotpassword extends AppCompatActivity {
    FirebaseAuth mauth;
    EditText email;
    String emailid;
    Button forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        mauth=FirebaseAuth.getInstance();
        email=findViewById(R.id.editTextForgot);
        forgot = findViewById(R.id.buttonForgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkempty()){
                    if(checkemail()){
                        emailid = email.getText().toString();
                        mauth.sendPasswordResetEmail(emailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(forgotpassword.this, "Check email", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(forgotpassword.this,LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(forgotpassword.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    email.requestFocus();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    public boolean checkempty(){
        if(email.getText().length()==0){
            Toast.makeText(this, "Please enter an email id", Toast.LENGTH_SHORT).show();
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