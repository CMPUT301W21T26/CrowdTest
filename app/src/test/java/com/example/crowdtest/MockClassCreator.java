package com.example.crowdtest;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;

public class MockClassCreator {

    /**
     * Function for creating a mock binomial experiment
     * @return
     *  Binomial experiment instance
     */
    protected Binomial mockBinomialExperiment() {
        String owner = "test_owner";
        String experimentID = "test_experimentID";
        Binomial binomialExperiment = new Binomial(owner, experimentID);
        return binomialExperiment;
    }

    /**
     * Function for creating a mock count experiment
     * @return
     *  Count experiment instance
     */
    protected Count mockCountExperiment() {
        String owner = "test_owner";
        String experimentID = "test_experimentID";
        Count countExperiment = new Count(owner, experimentID);
        return countExperiment;
    }

    /**
     * Function for creating a mock measurement experiment
     * @return
     *  Measurement experiment instance
     */
    protected Measurement mockMeasurementExperiment() {
        String owner = "test_owner";
        String experimentID = "test_experimentID";
        Measurement measurementExperiment = new Measurement(owner, experimentID);
        return measurementExperiment;
    }

    /**
     * Function for creating a mock non-negative experiment
     * @return
     *  Non-negative experiment instance
     */
    protected NonNegative mockNonNegativeExperiment() {
        String owner = "test_owner";
        String experimentID = "test_experimentID";
        NonNegative nonNegativeExperiment = new NonNegative(owner, experimentID);
        return nonNegativeExperiment;
    }

    /**
     * Function for creating a mock binomial trial
     * @param inputSuccess
     *  Binomial trial input
     * @return
     *  Binomial trial instance
     */
    protected BinomialTrial mockBinomialTrial(boolean inputSuccess) {
        BinomialTrial binomialTrial = new BinomialTrial(inputSuccess);
        return binomialTrial;
    }

    /**
     * Function for creating a mock count trial
     * @param inputCount
     *  Count trial input
     * @return
     *  Count trial instance
     */
    protected CountTrial mockCountTrial(int inputCount) {
        CountTrial countTrial = new CountTrial(inputCount);
        return countTrial;
    }

    /**
     * Function for creating a mock measurement trial
     * @param inputMeasurement
     *  Measurement trial input
     * @return
     *  Measurement trial instance
     */
    protected MeasurementTrial mockMeasurementTrial(double inputMeasurement) {
        MeasurementTrial measurementTrial = new MeasurementTrial(inputMeasurement);
        return measurementTrial;
    }

    /**
     * Function for creating a mock non-negative trial
     * @param inputCount
     *  Non-negative trial input
     * @return
     *  Non-negative trial instance
     * @throws Exception
     *
     */
    protected NonNegativeTrial mockNonNegativeTrial(int inputCount) {
        NonNegativeTrial nonNegativeTrial = new NonNegativeTrial(inputCount);
        return nonNegativeTrial;
    }

    /**
     * Function for creating a mock UserProfile object instance
     * @return
     *  UserProfile object instance
     */
    protected UserProfile mockUserProfile() {
        String username = "test_username";
        String installationID = "test_installationID";
        UserProfile userProfile = new UserProfile(username, installationID);
        return userProfile;
    }

    /**
     * Function for creating a mock Experimenter object instance
     * @return
     *  Experimenter object instance
     */
    protected Experimenter mockExperimenter() {
        UserProfile userProfile = mockUserProfile();
        Experimenter experimenter = new Experimenter(userProfile);
        return experimenter;
    }

    /**
     * Second function for creating a mock Experimenter object instance
     * @param userProfile
     *  UserProfile object instance
     * @return
     *  Experimenter object instance
     */
    protected Experimenter mockExperimenter(UserProfile userProfile) {
        Experimenter experimenter = new Experimenter(userProfile);
        return experimenter;
    }
}
