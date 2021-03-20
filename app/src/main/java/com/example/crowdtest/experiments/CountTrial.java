package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

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
     * Constructor for experiments that don't require geo locations
     */
    public CountTrial() {
    }
}
