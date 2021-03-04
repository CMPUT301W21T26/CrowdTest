package com.example.crowdtest;

import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class ExperimentManager extends DatabaseManager {

    // ExperimentManager attributes
    final private String collectionPath = "Experiments";

    /**
     * ExperimentManager constructor
     */
    public ExperimentManager() {
        super();
    }

    /**
     * Function for generating a unique experiment ID
     * @return
     */
    public String generateExperimentID() {
        return generateDocumentID("experiment", collectionPath);
    }

    /**
     * Function for adding an experiment to the database
     * @param experiment
     */
    public void publishExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();
        UserProfile experimentOwnerProfile = experiment.getOwner().getUserProfile();

        // Add user data to HashMap
        HashMap<String, String> experimentData = new HashMap<>();
        experimentData.put("owner", experimentOwnerProfile.getUsername());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());

        // Add experiment to database
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Function for deleting an experiment from the database
     * @param experiment
     */
    public void unpublishExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();

        // Remove experiment from database
        removeDataFromCollection(collectionPath, experimentID);
    }
}
