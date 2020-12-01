package com.example.adginternals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class aboutusAdapter extends RecyclerView.Adapter<aboutusAdapter.MyViewHolder> {
    Context mcontext;
    ArrayList<aboutusitem> maboutusitem;
    String linkdein,github,emails;

    public aboutusAdapter(Context mcontext, ArrayList<aboutusitem> maboutusitem) {
        this.mcontext = mcontext;
        this.maboutusitem = maboutusitem;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView ln,git,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            ln = itemView.findViewById(R.id.Linkedin);
            git = itemView.findViewById(R.id.Github);
            email = itemView.findViewById(R.id.Email);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.aboutuscardview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        aboutusitem at = maboutusitem.get(position);
        holder.name.setText(at.getName());
        linkdein = at.getLinkedin();
        github = at.getGithub();
        emails = at.getEmail();
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(linkdein);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });
        holder.git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(github);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                mcontext.startActivity(intent);
            }
        });
        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(emails);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return maboutusitem.size();
    }


}