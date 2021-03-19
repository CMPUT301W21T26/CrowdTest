package com.example.crowdtest.experiments;

/**
 * Measurement trial class
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
     *  Input measurment
     */
    public MeasurementTrial(double inputMeasurement) {
        measurement = inputMeasurement;
    }

    /**
     * Function for getting the measurement value of the measurement trial
     * @return
     *  Measurement value
     */
    public double getMeasurement() {
        return measurement;
    }
}
