package com.example.crowdtest.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.MyExpFragment;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.ui.SubscribedExpFragment;

import java.util.ArrayList;

/**
 * Page adapter for tabbed view of ExperimentListActivity
 */
public class PagerAdapter extends FragmentPagerAdapter {


    private int numOfTabs;
    private Experimenter user;
    private ArrayList<Experiment> ownedExperiments;

    /**
     * constructor for PageAdapter
     * @param fm
     * @param numOfTabs
     *     Set the total number of tabs for the tabbed view
     */
    public PagerAdapter (FragmentManager fm, int numOfTabs, Experimenter user) {

        super(fm);
        this.numOfTabs = numOfTabs;
        this.user = user;

    }

    /**
     *
     * @param position
     *     The position of the tab that is currently selected
     * @return
     *     The fragment corresponding to the selected tab.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:

                return (new MyExpFragment()).newInstance(user);
            case 1:
                return (new SubscribedExpFragment()).newInstance(user);
            default:
                return null;
        }
    }

    /**
     * Returns the number of tabs of the tabbed view
     * @return
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }
}
