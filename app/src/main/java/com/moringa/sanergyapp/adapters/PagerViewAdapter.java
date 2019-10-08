package com.moringa.sanergyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringa.sanergyapp.ui.EmployeesFragment;
import com.moringa.sanergyapp.ui.NotificationsFragment;
import com.moringa.sanergyapp.ui.ProfileFragment;

public class PagerViewAdapter extends FragmentPagerAdapter {

    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                EmployeesFragment employeesFragment = new EmployeesFragment();
                return employeesFragment;
            case 2:
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
