package com.adgvit.internals;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dialogreasonFragment extends DialogFragment {
    EditText reason;
    Button post,cancel;
    TextView text1,text2;
    DatabaseReference myref,myref1;
    SharedPreferences pref,pref1;
    String mid,name,uid,title,time;
    String reasons;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogreasonfragment,container,false);

        post = view.findViewById(R.id.buttonpostreason);
        cancel = view.findViewById(R.id.buttonreasoncancel);
        reason = view.findViewById(R.id.editTextReason);
        text1 = view.findViewById(R.id.alertcardtext1_1);
        text2 = view.findViewById(R.id.alertcardtext2_1);

        pref = view.getContext().getSharedPreferences("com.adgvit.com.mid", Context.MODE_PRIVATE);
        mid=pref.getString("mid","");
        title=pref.getString("title","");
        time=pref.getString("time","");
        Log.i("mid",mid);

        pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        name=pref1.getString("name","");
        uid=pref1.getString("uid","");

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        myref = db.getReference("AlertAttendace");
        myref1=db.getReference("Users");

        text1.setText(title);
        text2.setText(time);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkempty()) {
                    if(isNetworkAvailable(view.getContext())) {
                        reasons = reason.getText().toString();
                        myref.child(mid).child(name).setValue(reasons);
                        myref1.child(uid).child("Meetings").child(mid).setValue(reasons);
                        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editoralert = preferences.edit();
                        editoralert.putString(mid, reasons);
                        editoralert.apply();
                        Toast.makeText(view.getContext(),"Posted" , Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }
                    else{
                        Toast.makeText(view.getContext(),"Please connect to the internet",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
    public boolean checkempty(){
        if(reason.getText().length()==0){
            Toast.makeText(getContext(),"Please enter a reason",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
