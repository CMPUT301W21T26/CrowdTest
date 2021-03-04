package com.example.crowdtest;

import java.util.ArrayList;

/**
 *
 */
public class Owner extends Experimenter{

    // Owner attributes
    ArrayList<String> ownedExperiments;
    private ExperimentManager experimentManager;

    /**
     * Owner constructor
     */
    public Owner(ExperimentManager experimentManager) {
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
        experimentManager.publishExperiment(experiment);
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
