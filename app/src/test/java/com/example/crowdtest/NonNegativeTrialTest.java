package com.example.crowdtest;

import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegativeTrial;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test methods unique to the NonNegativeTrial class
 */
public class NonNegativeTrialTest {


    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Test that the correct measurement is recorded for the NonNegativeTrial
     */
    @Test
    public void testCorrectMeasurement() {

        // Create a mock measurementTrial with measurement value 104
        NonNegativeTrial nonNegativeTrial = mockClassCreator.mockNonNegativeTrial(104);

        //check that the count is correct
        assertEquals(nonNegativeTrial.getCount(), 104);

    }
}
