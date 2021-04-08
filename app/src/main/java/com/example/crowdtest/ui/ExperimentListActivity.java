package com.example.crowdtest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.R;
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
    Experimenter user;
    ExperimentManager experimentManager = new ExperimentManager();

    /**
     * Customized OnCreate method.
     * Sets the tabbed layout to display a tab with Owned Experiment and one with Subscribed Experiments
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        //initialize the TabLayout and TabItem views
        tabLayout = findViewById(R.id.tab_bar);
        tabMyExp = findViewById(R.id.my_exp_tab);
        tabSubExp = findViewById(R.id.sub_exp_tab);
        viewPager = findViewById(R.id.view_pager);


        user = (Experimenter) getIntent().getSerializableExtra("USER");


        //create and set adapter for viewPager
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                                tabLayout.getTabCount(), user);

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