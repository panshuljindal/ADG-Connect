package com.adgvit.internals.Adapter.TabBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.adgvit.internals.Fragments.AlertsFragment.Core.Core1Fragment;
import com.adgvit.internals.Fragments.AlertsFragment.Core.Core2Fragment;
import com.adgvit.internals.Fragments.AlertsFragment.Core.Core3Fragment;

public class CoreAdapter extends FragmentStateAdapter {

    public CoreAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Core1Fragment();
            case 1:
                return new Core2Fragment();
            case 2:
                return new Core3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
