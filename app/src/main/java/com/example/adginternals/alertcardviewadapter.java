package com.example.adginternals;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class alertcardviewadapter extends RecyclerView.Adapter<alertcardviewadapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<alertcardviewitem> malertcardviewitem;
    DatabaseReference myref;
    String uid,id1;

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

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("AlertAttendace");

        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());
        holder.text3.setText(item.getText3());
        holder.text4.setText(item.getText4());
        holder.id.setText(item.getId());
        holder.ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ack.setVisibility(View.INVISIBLE);
                holder.un.setVisibility(View.INVISIBLE);
                holder.postedack.setVisibility(View.VISIBLE);
                SharedPreferences pref = mcontext.getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                uid = pref.getString("uid","");
                myref.child(holder.id.getText().toString()).child(uid).setValue("available");
            }
        });
        holder.un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.ack.setVisibility(View.INVISIBLE);
                holder.un.setVisibility(View.INVISIBLE);
                holder.postedun.setVisibility(View.VISIBLE);

                String mid = holder.id.getText().toString();
                SharedPreferences pref = mcontext.getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("mid",mid);
                editor.apply();

                Fragment unreason = new unavailable_reason();
                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,unreason);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return malertcardviewitem.size();
    }

}
