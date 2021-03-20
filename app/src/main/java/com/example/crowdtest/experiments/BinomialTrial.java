package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

public class BinomialTrial extends Trial {
    private boolean success;

    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp
     * @param location
     */
    public BinomialTrial(Date timestamp, Location location, boolean success) {
        super(timestamp, location);
        this.success = success;
    }

    public BinomialTrial(){

        //empty constructor required in order to fetch object
    }

    /**
     * Constructor for binomial trials
     *
     * @param inputSuccess Input Success/failure
     */
    public BinomialTrial(boolean inputSuccess) {
        success = inputSuccess;
    }

    public boolean isSuccess() {
        return success;
    }
}
