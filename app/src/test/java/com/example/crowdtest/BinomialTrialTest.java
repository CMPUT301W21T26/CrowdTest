package com.example.crowdtest;

import com.example.crowdtest.experiments.BinomialTrial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test methods unique to the BinomialTrial class
 */
public class BinomialTrialTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Function to test successful binomial trial has the correct attribute
     */
    @Test
    public void testSuccess() {

        // Create a mock binomial trial that is a success
        BinomialTrial binomialTrial = mockClassCreator.mockBinomialTrial(true);

        //check that binomial trial is in fact successful
        assertTrue(binomialTrial.isSuccess());
    }

    /**
     * Function to test successful binomial trial has the correct attribute
     */
    @Test
    public void testFailure() {

        // Create a mock binomial trial that is a failure
        BinomialTrial binomialTrial = mockClassCreator.mockBinomialTrial(false);

        //check binomial trial is properly  recorded as a failure
        assertFalse(binomialTrial.isSuccess());
    }


}
