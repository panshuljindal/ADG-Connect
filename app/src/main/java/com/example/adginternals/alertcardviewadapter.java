package com.example.adginternals;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class alertcardviewadapter extends RecyclerView.Adapter<alertcardviewadapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<alertcardviewitem> malertcardviewitem;
    DatabaseReference myref,myref1;
    String name,uid;
    String state="";
    SharedPreferences pref;

    public alertcardviewadapter(Context mcontext, ArrayList<com.example.adginternals.alertcardviewitem> malertcardviewitem) {
        this.mcontext = mcontext;
        this.malertcardviewitem = malertcardviewitem;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2,text3,text4,id;
        Button ack,un,postedack,postedun;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.alertcardtext1);
            text2=itemView.findViewById(R.id.alertcardtext2);
            text3=itemView.findViewById(R.id.alertcardtext3);
            text4=itemView.findViewById(R.id.alertcardtext4);
            id=itemView.findViewById(R.id.alertcardid);
            ack=itemView.findViewById(R.id.buttonack);
            un=itemView.findViewById(R.id.buttonun);
            postedun=itemView.findViewById(R.id.buttonpostedun);
            postedack=itemView.findViewById(R.id.buttonpostedack);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.alertcardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull alertcardviewadapter.MyViewHolder holder, int position) {

        alertcardviewitem item = malertcardviewitem.get(position);

        pref = mcontext.getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        name = pref.getString("name", "");
        uid = pref.getString("uid", "");

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("AlertAttendace");
        myref1 = db.getReference("Users");

        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());
        holder.text3.setText(item.getText3());
        holder.text4.setText(item.getText4());
        holder.id.setText(item.getId());
        SharedPreferences ps = mcontext.getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
        state = ps.getString(holder.id.getText().toString(), "");
        Log.i("state", state);
        if (state.equals("available")) {
            holder.postedun.setVisibility(View.INVISIBLE);
            holder.ack.setVisibility(View.INVISIBLE);
            holder.un.setVisibility(View.INVISIBLE);
            holder.postedack.setVisibility(View.VISIBLE);
        } else if (state.equals("java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference")) {

            holder.postedun.setVisibility(View.INVISIBLE);
            holder.ack.setVisibility(View.VISIBLE);
            holder.un.setVisibility(View.VISIBLE);
            holder.postedack.setVisibility(View.INVISIBLE);
        } else {
            holder.postedun.setVisibility(View.VISIBLE);
            holder.ack.setVisibility(View.INVISIBLE);
            holder.un.setVisibility(View.INVISIBLE);
            holder.postedack.setVisibility(View.INVISIBLE);
        }
        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    state = snapshot.child(uid).child("Meetings").child(holder.id.getText().toString()).getValue().toString();
                } catch (NullPointerException exception) {
                    state = exception.toString();
                }

                if (state.equals("available")) {
                    holder.postedun.setVisibility(View.INVISIBLE);
                    holder.ack.setVisibility(View.INVISIBLE);
                    holder.un.setVisibility(View.INVISIBLE);
                    holder.postedack.setVisibility(View.VISIBLE);
                } else if (state.equals("java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference")) {

                    holder.postedun.setVisibility(View.INVISIBLE);
                    holder.ack.setVisibility(View.VISIBLE);
                    holder.un.setVisibility(View.VISIBLE);
                    holder.postedack.setVisibility(View.INVISIBLE);
                } else {
                    holder.postedun.setVisibility(View.VISIBLE);
                    holder.ack.setVisibility(View.INVISIBLE);
                    holder.un.setVisibility(View.INVISIBLE);
                    holder.postedack.setVisibility(View.INVISIBLE);
                }
                SharedPreferences preferences = mcontext.getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
                SharedPreferences.Editor editoralert = preferences.edit();
                editoralert.putString(holder.id.getText().toString(), state);
                editoralert.apply();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Cancelled", "Cancel");
            }
        });

            holder.ack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable(mcontext)) {
                        holder.ack.setVisibility(View.INVISIBLE);
                        holder.un.setVisibility(View.INVISIBLE);
                        holder.postedack.setVisibility(View.VISIBLE);

                        myref1.child(uid).child("Meetings").child(holder.id.getText().toString()).setValue("available");
                        myref.child(holder.id.getText().toString()).child(name).setValue("available");
                    }
                    else {
                        Toast.makeText(mcontext,"Please connect to the internet",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.un.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mid = holder.id.getText().toString();
                    String title = holder.text1.getText().toString();
                    String time = holder.text2.getText().toString();
                    SharedPreferences pref = mcontext.getSharedPreferences("com.adgvit.com.mid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("mid", mid);
                    editor.putString("title", title);
                    editor.putString("time", time);
                    editor.apply();

                    Fragment unreason = new unavailable_reason();
                    FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, unreason);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

    }

    @Override
    public int getItemCount() {
        return malertcardviewitem.size();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}