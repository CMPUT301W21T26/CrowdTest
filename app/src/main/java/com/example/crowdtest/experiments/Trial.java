package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

public class Trial {
    private Date timestamp;
    private Location location;

    /**
     * Constructor for experiments that don't require geo locations
     */
    public Trial() {
        timestamp = new Date(); // The date is set to the current date by default
    }

    /**
     * Constructor with for geo location required experiments
     *
     * @param location geo location of the trial
     */
    public Trial(Location location) {
        timestamp = new Date(); // The date is set to the current date by default
        this.location = location;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * setLocation will be called within experiment if the experiment had location enabled
     *
     * @param location The new location for the trial
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
