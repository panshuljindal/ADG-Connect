package com.adgvit.internals.Adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.internals.R;
import com.adgvit.internals.Model.card2item;

import java.util.ArrayList;

public class card2adapter extends RecyclerView.Adapter<card2adapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<card2item> mcard2item;

    public card2adapter(Context mContext, ArrayList<card2item> mcard2item) {
        this.mContext = mContext;
        this.mcard2item = mcard2item;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2;
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
        card2item item = mcard2item.get(position);
        holder.text1.setText(item.getText1());
        holder.text2.setText(item.getText2());

    }

    @Override
    public int getItemCount() {
        return mcard2item.size();
    }


}
