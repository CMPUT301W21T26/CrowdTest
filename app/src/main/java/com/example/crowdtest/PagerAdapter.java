package com.example.crowdtest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Page adapter for tabbed view of ExperimentListActivity
 */
public class PagerAdapter extends FragmentPagerAdapter {


    private int numOfTabs;

    /**
     * constructor for PageAdapter
     * @param fm
     * @param numOfTabs
     *     Set the total number of tabs for the tabbed view
     */
    public PagerAdapter (FragmentManager fm, int numOfTabs) {

        super(fm);
        this.numOfTabs = numOfTabs;

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
                return new MyExpFragment();
            case 1:
                return new SubscribedExpFragment();
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
