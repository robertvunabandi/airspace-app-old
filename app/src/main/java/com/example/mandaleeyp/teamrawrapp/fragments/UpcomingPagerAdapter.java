package com.example.mandaleeyp.teamrawrapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mandaleeyp on 7/17/17.
 */

public class UpcomingPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"My Trips", "My Requests"};
    private Context context;

    private TripsFragment tripsFragment;
    private RequestsFragment requestsFragment;

    public UpcomingPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        tripsFragment = new TripsFragment();
        requestsFragment = new RequestsFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }


    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return tripsFragment;
        } else if (position == 1) {
            return requestsFragment;
        } else {
            return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
