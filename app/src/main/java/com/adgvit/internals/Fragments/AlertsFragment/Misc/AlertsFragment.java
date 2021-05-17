package com.adgvit.internals.Fragments.AlertsFragment.Misc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adgvit.internals.Activity.BestOfLuck;
import com.adgvit.internals.Adapter.TabBar.AlertAdapter;
import com.adgvit.internals.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class AlertsFragment extends Fragment {
    ViewPager2 viewPager1;
    TabLayout tabLayout1;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_alerts, container, false);

        tabLayout1= view.findViewById(R.id.tabLayout);
        viewPager1=view.findViewById(R.id.viewPager);

        viewPager1.setAdapter(new AlertAdapter(getActivity()));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout1, viewPager1, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Core");
                        break;
                    case 1:
                        tab.setText("Team");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        bestOfLuck();
        return view;
    }

    public void bestOfLuck(){

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.adgvit.com.userdata",MODE_PRIVATE);
        String uid1 = pref1.getString("uid","");
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref3 = database.getReference("Users");



        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (uid1.isEmpty()){
                }
                else {
                    String bestluck = snapshot.child(uid1).child("bestFuture").getValue().toString();
                    if (bestluck.equals("false")) {
                    } else if (bestluck.equals("true")) {
                        mauth.signOut();
                        Intent intent = new Intent(view.getContext(), BestOfLuck.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}