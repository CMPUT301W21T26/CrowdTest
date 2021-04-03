package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a NonNegativeTrial result
 */
public class NonNegativeTrial extends Trial {

    private long count;

    /**
     * Constructor for non-negative trials
     * Empty constructor for NonNegativeTrial object
     * Necessary for converting Firestore documents to NonNegativeTrial objects
     */
    public NonNegativeTrial() {

        //Empty constructor required

    }

    /**
     * Constructor for non-negative trials
     *
     * @param inputCount input count
     */
    public NonNegativeTrial(long inputCount) {
        if (inputCount > 0) {
            count = inputCount;
        } else {
            throw new IllegalArgumentException("Input value must be greater than 0");
        }
    }

    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp
     * @param location
     */
    public NonNegativeTrial(Date timestamp, Location location, long count) {
        super(timestamp, location);
        this.count = count;
    }

    /**
     * Function for getting the non-negative trial count
     *
     * @return Non-negative trial count
     */
    public long getCount() {
        return count;
    }
}

