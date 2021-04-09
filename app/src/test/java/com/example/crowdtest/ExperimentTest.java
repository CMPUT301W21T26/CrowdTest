package com.example.crowdtest;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ExperimentTest class for unit testing Experiment and its children
 */
public class ExperimentTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    // TODO: add tests here

    /**
     * Function to test adding a binomial trial to an experiment
     */
    @Test
    void testAddBinomialTrial() {
        Binomial binomialExperiment = mockClassCreator.mockBinomialExperiment();
        BinomialTrial binomialTrial = mockClassCreator.mockBinomialTrial(true);

        binomialExperiment.addTrial(binomialTrial.isSuccess());
        assertEquals(binomialExperiment.getTrials().size(), 1);
    }

    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddCountTrial() {
        Count countExperiment = mockClassCreator.mockCountExperiment();

        countExperiment.addTrial();
        assertEquals(countExperiment.getTrials().size(), 1);
    }

    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddMeasurementTrial() {
        Measurement measurementExperiment = mockClassCreator.mockMeasurementExperiment();
        MeasurementTrial measurementTrial = mockClassCreator.mockMeasurementTrial(5.5);

        measurementExperiment.addTrial(measurementTrial.getMeasurement());
        assertEquals(measurementExperiment.getTrials().size(), 1);
    }
    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddNonNegativeTrial() {
        NonNegative nonNegativeExperiment = mockClassCreator.mockNonNegativeExperiment();
        NonNegativeTrial nonNegativeTrial = mockClassCreator.mockNonNegativeTrial(5);

        nonNegativeExperiment.addTrial(nonNegativeTrial.getCount());
        assertEquals(nonNegativeExperiment.getTrials().size(), 1);
    }
}

