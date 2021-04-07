package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExperimenterTest class for unit testing Experimenter class and its children
 */
public class ExperimenterTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Function to test that the subscribed experiments collection of an experimenter can be set
     */
    @Test
    void testSetSubscribedExperiments() {
        // Create a mock experimenter
        Experimenter experimenter = mockClassCreator.mockExperimenter();

        // Create ArrayList of 10 subscribed experiment ID's
        ArrayList<String> subscribedExperiments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            subscribedExperiments.add("test_experiment" + String.valueOf(i));
        }

        // Set subscribedExperiments of mock experimenter
        experimenter.setSubscribedExperimentIDs(subscribedExperiments);

        // Check that the experiments have been successfully added to the mock experimenter
        assertEquals(10, experimenter.getSubscribedExperimentIDs().size());
        for (int i = 0; i < 10; i++) {
            assertEquals("test_experiment" + String.valueOf(i), experimenter.getSubscribedExperimentIDs().get(i));
        }
    }

    /**
     * Function to test that an experimenter can subscribe to an experiment
     */
    @Test
    void testSubscribe() {
        // Create a mock experimenter and experiment
        Experimenter experimenter = mockClassCreator.mockExperimenter();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Subscribe to experiment
        experimenter.subscribe(experiment);

        // Check that experiment has been successfully subscribed to
        assertEquals(1, experimenter.getSubscribedExperimentIDs().size());
        assertEquals(experiment.getExperimentID(), experimenter.getSubscribedExperimentIDs().get(0));

        // Check that exception is thrown when trying to subscribe to the same experiment
        assertThrows(IllegalArgumentException.class, () -> {
            experimenter.subscribe(experiment);
        });
    }

    /**
     * Function to test that an experimenter can unsubscribe from an experiment
     */
    @Test
    void testUnsubscribe() {
        // Create a mock experimenter and experiment
        Experimenter experimenter = mockClassCreator.mockExperimenter();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Subscribe to experiment
        experimenter.subscribe(experiment);

        // Unsubscribe to experiment
        experimenter.unsubscribe(experiment);

        // Check that experiment has been successfully unsubscribed from
        assertEquals(0, experiment.getSubscribers().size());

        // Check that exception is thrown when trying to unsubscribe from a non-subscribed experiment
        assertThrows(IllegalArgumentException.class, () -> {
            experimenter.unsubscribe(experiment);
        });
    }

    /**
     * Function to test that the owned experiments collection of an owner can be set
     */
    @Test
    void testSetOwnedExperiments() {
        // Create a mock owner
        Owner owner = mockClassCreator.mockOwner();

        // Create ArrayList of 10 subscribed experiment ID's
        ArrayList<String> ownedExperiments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ownedExperiments.add("test_experiment" + String.valueOf(i));
        }

        // Set subscribedExperiments of mock experimenter
        owner.setOwnedExperiments(ownedExperiments);

        // Check that the experiments have been successfully added to the mock experimenter
        assertEquals(10, owner.getOwnedExperiments().size());
        for (int i = 0; i < 10; i++) {
            assertEquals("test_experiment" + String.valueOf(i), owner.getOwnedExperiments().get(i));
        }
    }

    /**
     * Function to test that an owner can publish an experiment
     */
    @Test
    void testPublishExperiment() {
        // Create a mock owner and experiment
        Owner owner = mockClassCreator.mockOwner();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Publish experiment
        owner.publishExperiment(experiment);

        // Check that experiment has been successfully published
        assertEquals(1, owner.getOwnedExperiments().size());
        assertEquals(experiment.getExperimentID(), owner.getOwnedExperiments().get(0));
    }

    /**
     * Function to test that an owner can unpublish an experiment
     */
    @Test
    void testUnpublishExperiment() {
        // Create a mock owner and experiment
        Owner owner = mockClassCreator.mockOwner();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Publish experiment
        owner.publishExperiment(experiment);

        // Unpublish experiment
        owner.unpublishExperiment(experiment);

        // Check that experiment has been successfully unpublished
        assertEquals(0, owner.getOwnedExperiments().size());
    }

    /**
     * Function to test that an owner can archive an experiment
     */
    @Test
    void testArchiveExperiment() {
        // Create a mock owner and experiment
        Owner owner = mockClassCreator.mockOwner();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Publish experiment
        owner.publishExperiment(experiment);

        // Archive experiment
        owner.closeExperiment(experiment);

        // Check that experiment has been successfully archived
        assertEquals("closed", experiment.getStatus());
    }

    /**
     * Function to test getter and setter methods for common experimenter attributes
     */
    @Test
    void testAttributes() {
        // Create a mock experimenter and user profile
        UserProfile userProfile = mockClassCreator.mockUserProfile();
        Experimenter experimenter = mockClassCreator.mockExperimenter(userProfile);

        // Set attributes of experimenter
        experimenter.setStatus("experimenter");

        // Check that defined attributes have been successfully set
        assertEquals("experimenter", experimenter.getStatus());
        assertEquals(userProfile.getUsername(), experimenter.getUserProfile().getUsername());
    }
}
