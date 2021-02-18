package com.adgvit.internals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<momItem> mItemsMom = new ArrayList<>();
    String day , month ;

    Context context;
    public MyAdapter(Context ct , ArrayList<momItem> MomItem) {

        mItemsMom = MomItem;
          context = ct;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.momcardview,parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() { return mItemsMom.size();


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        momItem currentmom = mItemsMom.get(position);

        holder.myText1.setText(currentmom.getTitle());
        holder.myText2.setText(currentmom.getDate());

        //get the data for mini card
        month = extractMonth(currentmom.getDate());
        day = extractDay(currentmom.getDate());
        holder.points.setText(currentmom.getPoints());
        holder.carddateText1.setText(day);
        holder.carddateText2.setText(month);
        // item click action --> dialog

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment momDiagFrag = new momDialogFragment(currentmom);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,momDiagFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });

    }
    //searchbar filter
    public void filterList(ArrayList<momItem> filteredList){
        // need to make custom object for mom
        mItemsMom = filteredList;
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
        TextView myText1 , myText2  , points;
        TextView carddateText1 , carddateText2;


        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            myText1 = itemView.findViewById(R.id.momTitle);
            myText2 = itemView.findViewById(R.id.momDate);
            points = itemView.findViewById(R.id.textViewPoints);

            //to be extracted from myText2 i.e date
            carddateText1 = itemView.findViewById(R.id.dayText);
            carddateText2 = itemView.findViewById(R.id.monthText);
        }
    }

}

