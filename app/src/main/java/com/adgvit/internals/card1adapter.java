package com.adgvit.internals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        ImageView image2;
        LinearLayout linear;
        TextView text1,text2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linearlayout);
            image2=itemView.findViewById(R.id.card1image2);
            text1=itemView.findViewById(R.id.card1text1);
            text2=itemView.findViewById(R.id.card1text2);
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
        holder.linear.setBackground(ContextCompat.getDrawable(mContext,item.getImage1()));
        holder.image2.setImageResource(item.getImage2());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return mCard1item.size();
    }
}
