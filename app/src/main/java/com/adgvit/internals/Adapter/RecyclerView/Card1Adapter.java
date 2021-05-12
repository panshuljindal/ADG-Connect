package com.adgvit.internals.Adapter.RecyclerView;

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

import com.adgvit.internals.R;
import com.adgvit.internals.Model.Card1Item;

import java.util.ArrayList;

public class Card1Adapter extends RecyclerView.Adapter<Card1Adapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Card1Item> mCard1item;

    public Card1Adapter(Context mContext, ArrayList<Card1Item> mCard1item) {
        this.mContext = mContext;
        this.mCard1item = mCard1item;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView image2;
        private LinearLayout linear;
        private TextView text1,text2;
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

        Card1Item item = mCard1item.get(position);
        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());
        holder.linear.setBackground(ContextCompat.getDrawable(mContext,item.getImage1()));
        holder.image2.setImageResource(item.getImage2());

    }
    @Override
    public int getItemCount() {
        return mCard1item.size();
    }
}
