package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a generic Trial result
 */
public class Trial {

    // Trial attributes
    private Date timestamp;
    private Location geolocation;

    /**
     * Trial constructor to be used for experiments that don't require geo locations
     */
    public Trial() {
        timestamp = new Date(); // The date is set to the current date by default
    }

    /**
     * Constructor to be used  for geo location required experiments
     * @param geolocation
     *  Geolocation of the trial
     */
    public Trial(Location geolocation) {
        timestamp = new Date(); // The date is set to the current date by default
        this.geolocation = geolocation;
    }

    /**
     * Function for getting the timestamp of the trial
     * @return
     *  Timestamp of trial
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Function for getting the timestamp of the trial
     * @param timestamp
     *  Timestamp of trial
     */
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
