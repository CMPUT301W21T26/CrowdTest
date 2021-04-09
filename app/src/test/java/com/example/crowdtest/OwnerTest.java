package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OwnerTest class for unit testing Owner class
 */

public class OwnerTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    void initializeFireBase(){

    }
    /**
     * Function to test getter and setter methods for common Owner attributes
     */
    @Test
    void testAttributes() {
        // Create a mock owner
        Owner owner = mockClassCreator.mockOwner();

        // Set owner attributes
        owner.setOwnedExperiments(new ArrayList());

        // Check that defined attributes have been successfully set
        assertEquals(0, owner.getOwnedExperiments().size());
    }

    /**
     * Function to test that an owner can publish an experiment
     */
    @Test
    void testPublishExperiment() {
        // Create a mock owner and experiment
        Owner owner = mockClassCreator.mockOwner();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        // Publish an experiment
        owner.publishExperiment(experiment);

        // Check that experiment has been successfully published
        assertEquals(1, owner.getOwnedExperiments().size());

    }

     /**
     * Function to test that an owner can unpublish an experiment
     */
     @Test
     void testUnpublishExperiment() {
         // Create a mock owner and experiment
         Owner owner = mockClassCreator.mockOwner();
         Experiment experiment = mockClassCreator.mockBinomialExperiment();

         // Publish an experiment
         owner.publishExperiment(experiment);

         // Unpublish an experiment
         owner.unpublishExperiment(experiment);

         // Check that experiment has been successfully unpublished
         assertEquals(0, owner.getOwnedExperiments().size());

    }

    /**
     * Function to test that an experiment is closed
     */
    @Test
    void testCloseExperiment() {
        //Create a mock owner and experiment
        Owner owner = mockClassCreator.mockOwner();
        Experiment experiment = mockClassCreator.mockBinomialExperiment();

        //Close an experiment
        owner.closeExperiment(experiment);

        // Check that experiment has been successfully unsubscribed from
        assertEquals("closed", owner.getStatus());

    }

}
