package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import java.util.ArrayList;

/**
 * Owner class that represents an owner to an experiment
 */
public class Owner extends Experimenter {

    // Owner attributes
    ArrayList<String> ownedExperiments;

    /**
     * Owner constructor
     * @param userProfile
     *  User profile of owner
     */
    public Owner(UserProfile userProfile) {
        super(userProfile);

        // Initialize Owner attributes
        setStatus("owner");
        ownedExperiments = new ArrayList<>();
    }

    /**
     * Function for publishing an experiment
     * @param experiment
     *  Experiment to publish
     */
    public void publishExperiment(Experiment experiment) {
        ownedExperiments.add(experiment.getExperimentID());
    }

    /**
     * Function for removing experiment from owner's list
     * @param experiment
     *  Experiment to un-publish
     */
    public void unpublishExperiment(Experiment experiment) {
        ownedExperiments.remove(experiment.getExperimentID());
    }

    /**
     * Function for archiving an experiment
     * @param experiment
     *  Experiment to archive
     */
    public void archiveExperiment(Experiment experiment) {
        experiment.setStatus("archived");
    }

    /**
     * Function for getting the experiments of the owner
     * @return
     *  Experiments of owner
     */
    public ArrayList<String> getOwnedExperiments() {
        return ownedExperiments;
    }

    /**
     * Function for setting the experiments of the owner
     * @param ownedExperiments
     *  Experiments of owner
     */
    public void setOwnedExperiments(ArrayList<String> ownedExperiments) {
        this.ownedExperiments = ownedExperiments;
    }
}
