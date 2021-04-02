package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a CountTrial result
 */
public class CountTrial extends Trial {
    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp
     * @param location
     */
    public CountTrial(Date timestamp, Location location) {
        super(timestamp, location);
    }

    /**
     * Empty constructor for CountTrial objects
     * Necessary for converting Firestore documents to CountTrial objects
     */
    public CountTrial(){

        //empty constructor required
    }


}
