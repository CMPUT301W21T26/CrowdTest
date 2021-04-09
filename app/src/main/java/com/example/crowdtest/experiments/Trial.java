package com.example.crowdtest.experiments;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class Trial implements Serializable {
    private Date timestamp;
    private double locationLong;
    private double locationLat;

    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp
     * @param location
     */
    public Trial(Date timestamp, Location location) {
        this.timestamp = timestamp;
        this.locationLong = location.getLongitude();
        this.locationLat = location.getLatitude();
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
        this.locationLong = location.getLongitude();
        this.locationLat = location.getLatitude();
    }

    /**
     * Function for getting the timestamp of the trial
     *
     * @return Timestamp of trial
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Function for setting the geolocation of the trial if corresponding
     * experiment has geolocation enabled
     *
     * @param location Geolocation of trial
     */
    public void setLocation(Location location) {
        this.locationLong = location.getLongitude();
        this.locationLat = location.getLatitude();
    }

    /**
     * Function for setting the timestamp of the trial
     *
     * @param timestamp Timestamp of trial
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Function for getting the latitude of where the trial was submitted
     *
     * @return Latitude of trial
     */
    public double getLocationLat() {
        return locationLat;
    }

    /**
     * Function for getting the longitude of where the trial was submitted
     *
     * @return Longitude of trial
     */
    public double getLocationLong() {
        return locationLong;
    }

}
