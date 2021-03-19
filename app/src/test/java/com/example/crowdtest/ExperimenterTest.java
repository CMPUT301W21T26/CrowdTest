package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ExperimenterTest extends MockClassCreator {

    /**
     * Function to test that the subscribed experiments collection of an experimenter can be set
     */
    @Test
    void testSetSubscribedExperiments() {
        // Create a mock experimenter
        Experimenter experimenter = mockExperimenter();

        // Create ArrayList of 10 subscribed experiment ID's
        ArrayList<String> subscribedExperiments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            subscribedExperiments.add("test_experiment" + String.valueOf(i));
        }

        // Set subscribedExperiments of mock experimenter
        experimenter.setSubscribedExperiments(subscribedExperiments);

        // Check that the experiments have been successfully added to the mock experimenter
        assertEquals(10, experimenter.getSubscribedExperiments().size());
        for (int i = 0; i < 10; i++) {
            assertEquals("test_experiment" + String.valueOf(i), experimenter.getSubscribedExperiments().get(i));
        }
    }

    /**
     * Function to test that a user can subscribe to an experiment
     */
    @Test
    void testSubscribe() {
        // Create a mock experimenter and experiment
        Experimenter experimenter = mockExperimenter();
        Experiment experiment = mockBinomialExperiment();

        // Subscribe to experiment twice
        experimenter.subscribe(experiment);

        // Check that experiment has been successfully subscribed to only once
        assertEquals(1, experimenter.getSubscribedExperiments().size());
        assertEquals(experiment.getExperimentID(), experimenter.getSubscribedExperiments().get(0));

        // Check that exception is thrown when trying to subscribe to the same experiment
        assertThrows(IllegalArgumentException.class, () -> {
            experimenter.subscribe(experiment);
        });
    }

    /**
     * Function to test that a user can unsubscribe from an experiment
     */
    @Test
    void testUnsubscribe() {
        // Create a mock experimenter and experiment
        Experimenter experimenter = mockExperimenter();
        Experiment experiment = mockBinomialExperiment();

        // Subscribe to experiment
        experimenter.subscribe(experiment);

        // Unsubscribe to experiment twice
        experimenter.unsubscribe(experiment);

        // Check that experiment has been successfully unsubscribed from
        assertEquals(0, experiment.getSubscribers().size());

        // Check that exception is thrown when trying to unsubscribe from a non-subscribed experiment
        assertThrows(IllegalArgumentException.class, () -> {
            experimenter.unsubscribe(experiment);
        });
    }

    /**
     * Function to test getter and setter methods for experimenter's status
     */
    @Test
    void testStatus() {
        // Create a mock experimenter
        Experimenter experimenter = mockExperimenter();

        // Set status of experimenter
        experimenter.setStatus("experimenter");

        // Check status of experimenter
        assertEquals("experimenter", experimenter.getStatus());
    }

    /**
     * Function to test getter method for experimenter's user profile
     */
    @Test
    void testUserProfile() {
        // Create a mock experimenter and user profile
        UserProfile userProfile = mockUserProfile();
        Experimenter experimenter = mockExperimenter(userProfile);

        // Check that user profile has been successfully set
        assertEquals(userProfile.getUsername(), experimenter.getUserProfile().getUsername());
    }
}
