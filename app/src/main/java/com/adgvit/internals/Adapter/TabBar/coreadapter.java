package com.adgvit.internals.Adapter.TabBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.adgvit.internals.Fragments.AlertsFragment.Core.core1fragment;
import com.adgvit.internals.Fragments.AlertsFragment.Core.core2fragment;
import com.adgvit.internals.Fragments.AlertsFragment.Core.core3fragment;

public class coreadapter  extends FragmentStateAdapter {

    public coreadapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new core1fragment();
            case 1:
                return new core2fragment();
            case 2:
                return new core3fragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
