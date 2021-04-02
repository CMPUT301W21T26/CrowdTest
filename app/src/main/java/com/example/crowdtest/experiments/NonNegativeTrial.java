package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a NonNegativeTrial result
 */
public class NonNegativeTrial extends Trial {

    private int count;

    /**
     * Constructor for non-negative trials
     *
     * @param inputCount Input count
     * Empty constructor for NonNegativeTrial object
     * Necessary for converting Firestore documents to NonNegativeTrial objects
     */
    public NonNegativeTrial(){

        //Empty constructor required

    }

    /**
     * NonNegativeTrial constructor
     * @param inputCount
     *  Non-negative input count
     */
    public NonNegativeTrial(int inputCount) {
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
    public NonNegativeTrial(Date timestamp, Location location, int count) {
        super(timestamp, location);
        this.count = count;
    }

    /**
     * Function for getting the non-negative trial count
     * @return
     *  Non-negative trial count
     */
    public int getCount() {
        return count;
    }
}

