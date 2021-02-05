package com.example.adginternals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class teamfragment extends Fragment {

    TabLayout tabLayout2_1;
    ViewPager2 viewPager2_1;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_teamfragment, container, false);

        tabLayout2_1=view.findViewById(R.id.tablayout2_1);
        viewPager2_1=view.findViewById(R.id.viewPager2_1);

        viewPager2_1.setAdapter(new teamadapter(getActivity()));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout2_1, viewPager2_1, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("All");
                            break;
                        case 1:
                            tab.setText("Android");
                            break;
                        case 2:
                            tab.setText("iOS");
                            break;

                    }
                }
        });
        tabLayoutMediator.attach();
        return view;
    }
}