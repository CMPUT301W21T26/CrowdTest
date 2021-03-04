package com.example.crowdtest;

/**
 *
 */
public class Experiment {

    // Experiment attributes
    private String experimentID;
    private Experimenter owner;
    private String status;
    private String description;
    private String region;

    private ExperimentManager experimentManager;

    /**
     *
     * @param owner
     * @param experimentManager
     * @param addExperiment
     */
    public Experiment(Experimenter owner, ExperimentManager experimentManager, boolean addExperiment) {
        this.owner = owner;
        experimentID = experimentManager.generateExperimentID();
        this.experimentManager = experimentManager;

        if (addExperiment) {
            experimentManager.publishExperiment(this);
        }
    }

    /**
     * Function for returning experimentID
     * @return
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Function for setting experimentID
     * @return
     */
    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    /**
     * Function for getting experiment owner
     * @return
     */
    public Experimenter getOwner() {
        return owner;
    }

    /**
     * Function for setting experiment owner
     * @return
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Function for getting experiment description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting experiment description
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting experiment region
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting experiment region
     * @return
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting experiment status
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting experiment status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
