package com.example.mandaleeyp.teamrawrapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mandaleeyp.teamrawrapp.R;

public class UpcomingFragment extends Fragment {

    Context context;


    public UpcomingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcoming, container, false);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new UpcomingPagerAdapter(getChildFragmentManager(), getContext()));

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
//        setupViewPager(viewPager);
//        // Set Tabs inside Toolbar
//        TabLayout tabs = (TabLayout) view.findViewById(R.id.fixture_tabs);
//        tabs.setupWithViewPager(viewPager);

        return v;
    }

    private void setupViewPager(ViewPager viewPager) {

//        Adapter adapter = new Adapter(getChildFragmentManager());
//        adapter.addFragment(new TripsFragment(), "My Trips");
//        adapter.addFragment(new RequestsFragment(), "My Requests");
//        viewPager.setAdapter(adapter);
    }
}
