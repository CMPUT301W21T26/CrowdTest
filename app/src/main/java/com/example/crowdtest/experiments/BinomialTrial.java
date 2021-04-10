package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a BinomialTrial result
 */
public class BinomialTrial extends Trial {
    private boolean success;

    /**
     * Empty BinomialTrial constructor
     */
    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp
     * @param location
     */
    public BinomialTrial(Date timestamp, Location location, boolean success, String poster) {
        super(timestamp, location, poster);
        this.success = success;
    }


    /**
     * Empty constructor needed for the databse
     */
    public BinomialTrial(){

        //Required for firestore access
    }

    /**
     * BinomialTrial constructor
     * @param inputSuccess
     *  Input success/failure
     */
    public BinomialTrial(boolean inputSuccess) {
        success = inputSuccess;
    }

    /**
     * Function to check whether a binomial trial is a success or failure
     * @return
     *  True if success, false if failure
     */
    public boolean isSuccess() {
        return success;
    }
}
