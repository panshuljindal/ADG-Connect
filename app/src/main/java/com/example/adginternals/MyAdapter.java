package com.example.adginternals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> data1 = new ArrayList<String>();
    ArrayList<String> data2 = new ArrayList<String>();


    //String data1[] , data2[];
    String day , month ;

    Button DialogOk ; TextView DialogDetailList; // need to bind these views mom dialog


    Context context;
    public MyAdapter(Context ct , ArrayList<String> t, ArrayList<String> d) {

        context = ct;
        data1 = t;
        data2 = d;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.momcardview,parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() { return data1.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        holder.myText2.setText(data2.get(position));

        day = extractDay(data2.get(position));
        month = extractMonth(data2.get(position));

        holder.carddateText1.setText(day);
        holder.carddateText2.setText(month);
        // item click action --> dialog
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "item clicked" + position, Toast.LENGTH_SHORT).show();
                //inflate the custom dialog to display mom
                //open new fragment
                Dialog momdialog = new Dialog(context,R.style.Theme_Dialog);
                momdialog.setContentView(R.layout.momdialog);
                momdialog.show();

                Button okDiag = momdialog.findViewById(R.id.momDialogOKBtn);


                CharSequence bulletedList = BulletTextUtils.makeBulletList(5,"Everyone has to get atleast 5 participants from their end.",
        "Valid reason has to be provided for not attending the meeting in the ADG Internals app.",
        "Desk duties will be alloted and everyone is asked to report on time.");
                TextView momPointsDiscussed = momdialog.findViewById(R.id.momDialogPointsDiscussed);
                momPointsDiscussed.setText(bulletedList);
                okDiag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        momdialog.dismiss();
                    }
                });




            }

        });

    }
    public void filterList(ArrayList<String> filteredList){
        // need to make custom object for mom
        
        data1 = filteredList;
        data2 = filteredList;
        notifyDataSetChanged();
    }


    private String extractDay(String s) {
        String d;
        d = s.substring(0,2);
        return  d;
    }

    private String extractMonth(String s){
        String m;
        m = s.substring(3,6);
        return m;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1 , myText2  ;
        TextView carddateText1 , carddateText2;


        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            myText1 = itemView.findViewById(R.id.momTitle);
            myText2 = itemView.findViewById(R.id.momDate);


            //to be extracted from myText2 i.e date
            carddateText1 = itemView.findViewById(R.id.dayText);
            carddateText2 = itemView.findViewById(R.id.monthText);
        }
    }

}

