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
     * @param timestamp The date and time of submission
     * @param location The location where the trial was submitted
     */
    public CountTrial(Date timestamp, Location location, String poster) {
        super(timestamp, location, poster);
    }

    /**
     * Empty constructor for CountTrial objects
     * Necessary for converting Firestore documents to CountTrial objects
     */
    public CountTrial(){

        //empty constructor required
    }


}
