package com.example.crowdtest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

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
        ArrayList<Integer> existingIntegerIDs = new ArrayList<>();
        ArrayList<String>  existingStringIDs = getExperimentIDs();

        // Get an integer list of all experiment IDs and find the maximum ID
        for (String stringID : existingStringIDs) existingIntegerIDs.add(Integer.valueOf(stringID));
        Integer maxID = Collections.max(existingIntegerIDs) + 1;

        return maxID.toString();
    }

    /**
     * Function for retrieving all experiment IDs from the database
     * @return
     */
    public ArrayList<String> getExperimentIDs() {
        return getAllDocuments(collectionPath);
    }

    /**
     * Function for adding an experiment to the database
     * @param experiment
     */
    public void publishExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();
        UserProfile experimentOwnerProfile = experiment.getOwner().getUserProfile();

        // Collect experiment data
        ArrayList<String> experimentDataList = new ArrayList();
        experimentDataList.add(experimentOwnerProfile.getUsername());
        experimentDataList.add(experiment.getDescription());
        experimentDataList.add(experiment.getRegion());

        // Add user data to HashMap
        HashMap<String, Collection> experimentData = new HashMap<>();
        experimentData.put(experimentID, experimentDataList);

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
