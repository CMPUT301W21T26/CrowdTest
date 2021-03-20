package com.example.crowdtest.experiments;

import android.location.Location;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private double measurement;


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
     * @param timestamp
     * @param location
     */
    public MeasurementTrial(Date timestamp, Location location, double measurement) {
        super(timestamp, location);
        this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }
}
