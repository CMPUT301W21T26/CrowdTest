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
public class ExperimentTest extends MockClassCreator {

    // TODO: add tests here

    /**
     * Function to test adding a binomial trial to an experiment
     */
    @Test
    void testAddBinomialTrial() {
        Binomial binomialExperiment = mockBinomialExperiment();
        BinomialTrial binomialTrial = mockBinomialTrial(true);
    }

    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddCountTrial() {
        Count countExperiment = mockCountExperiment();
        CountTrial countTrial = mockCountTrial();
    }

    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddMeasurementTrial() {
        Measurement measurementExperiment = mockMeasurementExperiment();
        MeasurementTrial measurementTrial = mockMeasurementTrial(5.5);
    }

    /**
     * Function to test adding a count trial to an experiment
     */
    @Test
    void testAddNonNegativeTrial() {
        NonNegative nonNegativeExperiment = mockNonNegativeExperiment();
        NonNegativeTrial nonNegativeTrial = mockNonNegativeTrial(5);
    }
}
