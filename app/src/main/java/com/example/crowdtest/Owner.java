package com.example.crowdtest;

import java.util.ArrayList;

/**
 *
 */
public class Owner extends Experimenter {

    // Owner attributes
    ArrayList<String> ownedExperiments;
    ExperimentManager experimentManager;

    /**
     * Owner constructor
     * @param username
     * @param experimentManager
     */
    public Owner(String username, ExperimentManager experimentManager) {
        super(username);

        // Initialize Owner attributes
        setStatus("owner");
        ownedExperiments = new ArrayList<>();
        this.experimentManager = experimentManager;
    }

    /**
     * Function for publishing an experiment to the database
     * @param experiment
     */
    public void publishExperiment(Experiment experiment) {
        ownedExperiments.add(experiment.getExperimentID());
        experimentManager.publishExperiment(this);
    }

    /**
     * Function for un-publishing an experiment from the database
     * @param experiment
     */
    public void unpublishExperiment(Experiment experiment) {
        ownedExperiments.remove(experiment.getExperimentID());
        experimentManager.unpublishExperiment(experiment);
    }

    /**
     *
     */
    public void archiveExperiment() {

    }
}
