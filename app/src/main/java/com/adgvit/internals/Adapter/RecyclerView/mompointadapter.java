package com.adgvit.internals.Adapter.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.internals.Model.mompoint;
import com.adgvit.internals.R;

import java.util.ArrayList;

public class mompointadapter extends RecyclerView.Adapter<mompointadapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<mompoint> mMompoint;

    public mompointadapter(Context mcontext, ArrayList<mompoint> mMompoint) {
        this.mcontext = mcontext;
        this.mMompoint = mMompoint;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView point1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            point1 = itemView.findViewById(R.id.momPointList);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.pointscardview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mompoint point = mMompoint.get(position);
        holder.point1.setText(point.getText1());
    }

    @Override
    public int getItemCount() {
        return mMompoint.size();
    }

}
