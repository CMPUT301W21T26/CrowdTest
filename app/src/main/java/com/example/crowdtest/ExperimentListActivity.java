package com.example.crowdtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * Display the owned and subscribed experiments associated to the signed in user.
 */
public class ExperimentListActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabMyExp;
    TabItem tabSubExp;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        //initialize the TabLayout and TabItem views
        tabLayout = findViewById(R.id.tab_bar);
        tabMyExp = findViewById(R.id.my_exp_tab);
        tabSubExp = findViewById(R.id.sub_exp_tab);
        viewPager = findViewById(R.id.view_pager);

        //create and set adapter for viewPager
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        //Set action to be taken when a tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}