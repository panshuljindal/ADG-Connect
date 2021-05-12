package com.adgvit.internals.Adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.internals.R;
import com.adgvit.internals.Model.Card2Item;

import java.util.ArrayList;

public class Card2Adapter extends RecyclerView.Adapter<Card2Adapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Card2Item> mcard2Item;

    public Card2Adapter(Context mContext, ArrayList<Card2Item> mcard2Item) {
        this.mContext = mContext;
        this.mcard2Item = mcard2Item;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text1,text2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.card2text1);
            text2=itemView.findViewById(R.id.card2text2);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notificationcardview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Card2Item item = mcard2Item.get(position);
        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());

    }

    @Override
    public int getItemCount() {
        return mcard2Item.size();
    }


}
