package com.example.adginternals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class card1adapter extends RecyclerView.Adapter<card1adapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<card1item> mCard1item;

    public card1adapter(Context mContext, ArrayList<card1item> mCard1item) {
        this.mContext = mContext;
        this.mCard1item = mCard1item;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image1,image2;
        TextView text1,text2,text3,text4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image1=itemView.findViewById(R.id.card1image1);
            image2=itemView.findViewById(R.id.card1image2);
            text1=itemView.findViewById(R.id.card1text1);
            text2=itemView.findViewById(R.id.card1text2);
            text3=itemView.findViewById(R.id.card1text3);
            text4=itemView.findViewById(R.id.card1text4);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview1,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        card1item item = mCard1item.get(position);
        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());
        holder.text3.setText(item.getText3());
        holder.text4.setText(item.getText4());
        holder.image1.setImageResource(item.getImage1());
        holder.image2.setImageResource(item.getImage2());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, item.getText1()+" clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return mCard1item.size();
    }
}
