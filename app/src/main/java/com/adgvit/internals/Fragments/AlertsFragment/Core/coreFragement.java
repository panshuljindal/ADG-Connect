package com.adgvit.internals.Fragments.AlertsFragment.Core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adgvit.internals.Adapter.TabBar.coreadapter;
import com.adgvit.internals.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class coreFragement extends Fragment {

    TabLayout tabLayout2;
    ViewPager2 viewPager2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_core_fragement,container,false);

        tabLayout2=view.findViewById(R.id.tablayout2);
        viewPager2=view.findViewById(R.id.viewPager2);

        viewPager2 .setAdapter(new coreadapter(getActivity()));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout2, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("All");
                        break;
                    case 1:
                        tab.setText("Meetings");
                        break;
                    case 2:
                        tab.setText("Duties");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        return view;
    }
}