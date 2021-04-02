package com.example.crowdtest.experiments;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Class to represent a Measurement Experiment
 */
public class Measurement extends Experiment {
    private ArrayList <MeasurementTrial> trials;

    /**
     * Experiment constructor
     * @param owner
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public Measurement(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    public Measurement() {
    }

    /**
     * Constructor for uploading from the database
     *
     * @param owner
     * @param experimentID
     * @param status
     * @param title
     * @param description
     * @param region
     * @param subscribers
     * @param questions
     * @param geoLocation
     * @param datePublished
     * @param minTrials
     */
    public Measurement(String owner, String experimentID, String status, String title,
                       String description, String region, ArrayList<String> subscribers,
                       ArrayList<String> questions, boolean geoLocation, Date datePublished,
                       int minTrials, ArrayList<MeasurementTrial> trials) {
        super(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, minTrials);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     * @param trialInput
     *  The trial that is going to be submitted in the experiment
     */
    public void addTrial(double trialInput) {
        MeasurementTrial trial = new MeasurementTrial(trialInput);
        trials.add(trial);
        addTrialToDB(trial);
    }

    public void addTrialToDB (MeasurementTrial trial){
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("context", trial);
        db.addDataToCollection("Experiments/"+experimentID+"/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Function for setting the trials for the measurement experiment
     * @param trials
     *  ArrayList of measurement trials
     */
    public void setTrials(ArrayList<MeasurementTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials for the measurement experiment
     * @return
     *  ArrayList of measurement trials
     */
    public ArrayList<MeasurementTrial> getTrials() {
        return trials;
    }
}
