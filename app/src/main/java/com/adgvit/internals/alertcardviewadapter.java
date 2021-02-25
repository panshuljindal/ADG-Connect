package com.adgvit.internals;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Handler;

public class alertcardviewadapter extends RecyclerView.Adapter<alertcardviewadapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<alertcardviewitem> malertcardviewitem;
    DatabaseReference myref,myref1;
    String name,uid;
    String state="";
    Boolean shimmer =true;
    SharedPreferences pref,preferences;
    SharedPreferences.Editor editoralert;
    ShimmerFrameLayout shimmerFrameLayout;
    int Shimmernumber = 5;

    public alertcardviewadapter(Context mcontext, ArrayList<com.adgvit.internals.alertcardviewitem> malertcardviewitem) {
        this.mcontext = mcontext;
        this.malertcardviewitem = malertcardviewitem;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2,text3,text4,id;
        Button ack,un,postedack,postedun, resetoptn;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView alert,alert1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.alertcardtext1);
            text2=itemView.findViewById(R.id.alertcardtext2);
            text3=itemView.findViewById(R.id.alertcardtext3);
            text4=itemView.findViewById(R.id.alertcardtext4);
            id=itemView.findViewById(R.id.alertcardid);
            ack=itemView.findViewById(R.id.buttonack);
            un=itemView.findViewById(R.id.buttonun);
            resetoptn = itemView.findViewById(R.id.resetOption);
            postedun=itemView.findViewById(R.id.buttonpostedun);
            postedack=itemView.findViewById(R.id.buttonpostedack);
            //shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);
            alert = itemView.findViewById(R.id.alertimageView);
            alert1 = itemView.findViewById(R.id.alertimageView1);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.alertcardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull alertcardviewadapter.MyViewHolder holder, int position) {

        /*if (shimmer) {
            holder.shimmerFrameLayout.startShimmer();
        } else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);
            holder.text1.setBackground(null);
            holder.text2.setBackground(null);
            holder.text3.setBackground(null);
            holder.text4.setBackground(null);
            holder.alert.setBackground(null);
            holder.alert1.setBackground(null);*/

            alertcardviewitem item = malertcardviewitem.get(position);

            pref = mcontext.getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            name = pref.getString("name", "");
            uid = pref.getString("uid", "");

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            myref = db.getReference("AlertAttendace");
            myref1 = db.getReference("Users");

            holder.text1.setText(item.getText1());
            holder.text2.setText(item.getText2());
            holder.text4.setText(Html.fromHtml("<u>" + item.getText4() + "</u>"));
            holder.text3.setText(item.getText3());
            holder.id.setText(item.getId());
            SharedPreferences ps = mcontext.getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
            state = ps.getString(holder.id.getText().toString(), "");
            //Log.i("state", state);
            if (state.equals("available")) {
                holder.postedun.setVisibility(View.INVISIBLE);
                holder.ack.setVisibility(View.INVISIBLE);
                holder.un.setVisibility(View.INVISIBLE);
                holder.postedack.setVisibility(View.VISIBLE);
            } else if (state.equals("null")) {

                holder.postedun.setVisibility(View.INVISIBLE);
                holder.ack.setVisibility(View.VISIBLE);
                holder.un.setVisibility(View.VISIBLE);
                holder.postedack.setVisibility(View.INVISIBLE);
            } else {
                holder.postedun.setVisibility(View.VISIBLE);
                holder.ack.setVisibility(View.INVISIBLE);
                holder.un.setVisibility(View.INVISIBLE);
                holder.postedack.setVisibility(View.INVISIBLE);
            }
            myref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        state = snapshot.child(uid).child("Meetings").child(holder.id.getText().toString()).getValue().toString();
                    } catch (NullPointerException exception) {
                        state = "null";
                    }

                    if (state.equals("available")) {
                        holder.postedun.setVisibility(View.INVISIBLE);
                        holder.ack.setVisibility(View.INVISIBLE);
                        holder.un.setVisibility(View.INVISIBLE);
                        holder.postedack.setVisibility(View.VISIBLE);
                    } else if (state.equals("null")) {

                        holder.postedun.setVisibility(View.INVISIBLE);
                        holder.ack.setVisibility(View.VISIBLE);
                        holder.un.setVisibility(View.VISIBLE);
                        holder.postedack.setVisibility(View.INVISIBLE);
                    } else {
                        holder.postedun.setVisibility(View.VISIBLE);
                        holder.ack.setVisibility(View.INVISIBLE);
                        holder.un.setVisibility(View.INVISIBLE);
                        holder.postedack.setVisibility(View.INVISIBLE);
                    }
                    preferences = mcontext.getSharedPreferences("com.adgvit.com.alert", Context.MODE_PRIVATE);
                    editoralert = preferences.edit();
                    editoralert.putString(holder.id.getText().toString(), state);
                    editoralert.apply();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i("Cancelled", "Cancel");
                }
            });

            holder.ack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable(mcontext)) {
                        holder.ack.setVisibility(View.INVISIBLE);
                        holder.un.setVisibility(View.INVISIBLE);
                        holder.postedack.setVisibility(View.VISIBLE);

                        myref1.child(uid).child("Meetings").child(holder.id.getText().toString()).setValue("available");
                        myref.child(holder.id.getText().toString()).child(uid).setValue("available");
                    } else {
                        Toast.makeText(mcontext, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.un.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mid = holder.id.getText().toString();
                    String title = holder.text1.getText().toString();
                    String time = holder.text2.getText().toString();
                    SharedPreferences pref = mcontext.getSharedPreferences("com.adgvit.com.mid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("mid", mid);
                    editor.putString("title", title);
                    editor.putString("time", time);
                    editor.apply();

                    dialogreasonFragment dialogreasonFragment = new dialogreasonFragment();
                    dialogreasonFragment.show(((FragmentActivity) mcontext).getFragmentManager(), "Fragment");
                }
            });
            holder.resetoptn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menupop = new PopupMenu(mcontext, holder.resetoptn);
                    menupop.getMenuInflater().inflate(R.menu.alertcardmenu, menupop.getMenu());

                    menupop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (isNetworkAvailable(v.getContext())) {
                                try {
                                    myref.child(holder.id.getText().toString()).child(name).removeValue();
                                    myref1.child(uid).child("Meetings").child(holder.id.getText().toString()).removeValue();
                                    state = "null";
                                    editoralert.putString(holder.id.getText().toString(), state);
                                    editoralert.apply();
                                } catch (Exception e) {
                                    Toast.makeText(v.getContext(), "Error occurred. Please try again", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(v.getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                    menupop.show();
                }
            });

        }

    @Override
    public int getItemCount() {
        return malertcardviewitem.size();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}