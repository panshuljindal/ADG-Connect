package com.adgvit.internals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AlertsFragment extends Fragment {
    ViewPager2 viewPager1;
    TabLayout tabLayout1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_alerts, container, false);

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
        return view;
    }
}