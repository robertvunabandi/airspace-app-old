package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mandaleeyp.teamrawrapp.fragments.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public static final int ADDITIONAL_DETAILS_CODE = 1;
    public ViewPager vpPager;
    public View parentLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        parentLayout = findViewById(R.id.parentLayout);

        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), this));
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_android);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADDITIONAL_DETAILS_CODE) {
            vpPager.setCurrentItem(4);
            Snackbar.make(parentLayout, String.format("Your travel notice has been saved successfully!"), Snackbar.LENGTH_LONG).show();
        }
    }
}
