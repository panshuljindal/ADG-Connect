package com.adgvit.internals.Adapter.RecyclerView;

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

import com.adgvit.internals.R;
import com.adgvit.internals.Model.AboutUsItem;

import java.util.ArrayList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<AboutUsItem> maboutusitem;
    private String linkdein,github,emails;

    public AboutUsAdapter(Context mcontext, ArrayList<AboutUsItem> maboutusitem) {
        this.mcontext = mcontext;
        this.maboutusitem = maboutusitem;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView ln,git,email,image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            ln = itemView.findViewById(R.id.Linkedin);
            git = itemView.findViewById(R.id.Github);
            email = itemView.findViewById(R.id.Email);
            image = itemView.findViewById(R.id.imageView4);
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

        AboutUsItem at = maboutusitem.get(position);
        holder.name.setText(at.getName());

        holder.image.setImageResource(at.getImage());
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(maboutusitem.get(position).getLinkedin());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });
        holder.git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(maboutusitem.get(position).getGithub());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                mcontext.startActivity(intent);
            }
        });
        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {maboutusitem.get(position).getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                intent.setType("message/rfc822");
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return maboutusitem.size();
    }


}