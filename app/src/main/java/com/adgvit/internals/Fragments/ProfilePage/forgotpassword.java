package com.adgvit.internals.Fragments.ProfilePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adgvit.internals.Activity.LoginActivity;
import com.adgvit.internals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgotpassword extends DialogFragment {
    FirebaseAuth mauth;
    EditText email;
    String emailid;
    Button forgot,Cancel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_forgotpassword,container,false);

        mauth=FirebaseAuth.getInstance();
        Cancel=view.findViewById(R.id.buttonForgotCancel);
        email=view.findViewById(R.id.editTextForgot);
        forgot = view.findViewById(R.id.buttonForgot);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

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
                                    Toast.makeText(view.getContext(), "Check email", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(view.getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    email.requestFocus();
                                }
                            }
                        });
                    }
                }
            }
        });
        return view;
    }
    public boolean checkempty(){
        if(email.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter an email id", Toast.LENGTH_SHORT).show();
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