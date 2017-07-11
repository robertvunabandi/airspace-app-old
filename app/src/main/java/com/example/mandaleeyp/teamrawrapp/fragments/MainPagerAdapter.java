package com.example.mandaleeyp.teamrawrapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mandaleeyp on 7/11/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Profile", "Travel", "Send/Receive", "Messages", "Upcoming"};
    private Context context;

    private ProfileFragment profileFragment;
    private TravelFragment travelFragment;
    private SendReceiveFragment sendReceiveFragment;
    private MessagesFragment messagesFragment;
    private UpcomingFragment upcomingFragment;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        profileFragment = new ProfileFragment();
        travelFragment = new TravelFragment();
        sendReceiveFragment = new SendReceiveFragment();
        messagesFragment = new MessagesFragment();
        upcomingFragment = new UpcomingFragment();

    }
    // return the total # of fragments

    @Override
    public int getCount() {
        return 5;
    }


    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return profileFragment;
        } else if (position == 1) {
            return travelFragment;
        } else if (position == 2){
            return sendReceiveFragment;
        } else if (position == 3) {
            return messagesFragment;
        } else if (position == 4) {
            return upcomingFragment;
        } else {
            return null;
        }
    }

    // return title
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
