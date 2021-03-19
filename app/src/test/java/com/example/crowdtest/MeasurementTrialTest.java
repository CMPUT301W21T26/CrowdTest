package com.example.crowdtest;

import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.MeasurementTrial;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test methods unique to the MeasurementTrial class
 */
public class MeasurementTrialTest {


    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Test that the correct measurement is recorded for the Measurement Trial
     */
    @Test
    public void testCorrectMeasurement() {

        // Create a mock measurementTrial with measurement value 3.457
        MeasurementTrial measurementTrial = mockClassCreator.mockMeasurementTrial(3.457);

        //check that the measurement is correct
        assertEquals(measurementTrial.getMeasurement(), 3.457);
    }

}
