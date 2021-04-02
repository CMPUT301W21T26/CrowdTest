package com.example.crowdtest.experiments;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class Trial implements Serializable {
    private Date timestamp;
    private Location location;

    /**
     * Constructor for getting trials from the database
     * @param timestamp
     * @param location
     */
    public Trial(Date timestamp, Location location) {
        this.timestamp = timestamp;
        this.location = location;
    }

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

    /**
     * Function for getting the timestamp of the trial
     * @return
     *  Timestamp of trial
     */
    public Date getTimestamp() {
        return timestamp;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Function for getting the timestamp of the trial
     * @param timestamp
     *  Timestamp of trial
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Function for getting the geolocation of the trial
     * @return
     *  Geolocation of trial
     */
    public Location getGeolocation() {
        return geolocation;
    }

    /**
     * Function for setting the geolocation of the trial if corresponding
     * experiment has geolocation is enabled
     * @param geolocation
     *  Geolocation of trial
     */
    public void setGeolocation(Location geolocation) {
        this.geolocation = geolocation;
    }


}
