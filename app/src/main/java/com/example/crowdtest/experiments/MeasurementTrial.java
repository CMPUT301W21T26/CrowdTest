package com.example.crowdtest.experiments;

public class MeasurementTrial extends Trial {

    private double measurement;

    /**
     * Empty constructor for MeasurementTrial object
     * Necessary for converting Firestore documents to MeasurementTrial objects
     */
    public MeasurementTrial(){

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

    public double getMeasurement() {
        return measurement;
    }
}
