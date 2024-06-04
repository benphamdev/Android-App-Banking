package com.example.demoapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demoapp.Fragments.BalanceAlertFragment;
import com.example.demoapp.Fragments.PersonalAlertFragment;
import com.example.demoapp.Fragments.PromotionAlertFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(
            @NonNull FragmentManager fm, int behavior
    ) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PersonalAlertFragment();
        } else if (position == 1) {
            return new PromotionAlertFragment();
        }  else {
            return new PersonalAlertFragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (position == 0) {
            title = "Balance Alert";
        } else if (position == 1) {
            title = "Promotion Alert";
        }
        return title;
    }

}
