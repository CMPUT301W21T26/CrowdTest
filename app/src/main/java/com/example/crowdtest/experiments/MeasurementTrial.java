package com.example.crowdtest.experiments;

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

    public double getMeasurement() {
        return measurement;
    }
}
