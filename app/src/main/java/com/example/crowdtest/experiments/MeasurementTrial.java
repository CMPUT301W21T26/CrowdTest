package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

/**
 * Class to represent a MeasurementTrial result
 */
public class MeasurementTrial extends Trial {

    private double measurement;

    /**
     * Empty constructor for MeasurementTrial object
     * Necessary for converting Firestore documents to MeasurementTrial objects
     */
    public MeasurementTrial() {

        //Empty constructor required

    }

    /**
     * Constructor for measurement trials
     *
     * @param inputMeasurement Input measurement
     */
    public MeasurementTrial(double inputMeasurement) {

        measurement = inputMeasurement;
    }

    /**
     * Constructor for getting trials from the database
     *
     * @param timestamp The date and time of the trial submission
     * @param location  The location where the trial was submitted from
     */
    public MeasurementTrial(Date timestamp, Location location, double measurement, String poster) {
        super(timestamp, location, poster);
        this.measurement = measurement;
    }

    /**
     * Function for getting the measurement value of the measurement trial
     *
     * @return Returns the measurement value for the Trial
     */
    public double getMeasurement() {
        return measurement;
    }
}
