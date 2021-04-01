package com.adgvit.internals.Adapter.TabBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.adgvit.internals.Fragments.AlertsFragment.Core.coreFragement;
import com.adgvit.internals.Fragments.AlertsFragment.Team.team1fragment;

public class AlertAdapter extends FragmentStateAdapter{

    public AlertAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new coreFragement();

            default:
                return new team1fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
