package com.example.crowdtest.experiments;

/**
 * Class to represent a MeasurementTrial result
 */
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
     * MeasurementTrial constructor
     * @param inputMeasurement
     *  Double representing the user inputted measurement value
     */
    public MeasurementTrial(double inputMeasurement) {
        measurement = inputMeasurement;
    }

    /**
     * Function for getting the measurement value of the measurement trial
     * @return
     *  Returns the measurement value for the Trial
     */
    public double getMeasurement() {
        return measurement;
    }
}
