package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

public class NonNegativeTrial extends Trial {
    private int count;

    /**
     * Constructor for non-negative trials
     *
     * @param inputCount Input count
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

    public int getCount() {
        return count;
    }
}

