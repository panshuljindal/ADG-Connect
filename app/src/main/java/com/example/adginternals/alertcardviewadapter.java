package com.example.adginternals;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class alertcardviewadapter extends RecyclerView.Adapter<alertcardviewadapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<alertcardviewitem> malertcardviewitem;

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
            }
        });
        holder.un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ack.setVisibility(View.INVISIBLE);
                holder.un.setVisibility(View.INVISIBLE);
                holder.postedun.setVisibility(View.VISIBLE);
                Dialog reasondialog = new Dialog(mcontext,R.style.Theme_Dialog);
                reasondialog.setContentView(R.layout.unavaiable_dailog);
                reasondialog.show();
                Button cancelreason = reasondialog.findViewById(R.id.buttonreasoncancel);
                Button postreason = reasondialog.findViewById(R.id.buttonpostreason);
                cancelreason.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mcontext, "Cancel", Toast.LENGTH_SHORT).show();
                        reasondialog.dismiss();
                    }
                });
                postreason.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mcontext, "Reason Posted", Toast.LENGTH_SHORT).show();
                        reasondialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return malertcardviewitem.size();
    }

}
