package com.example.crowdtest.experiments;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Class to represent a Count Experiment
 */
public class Count extends Experiment {
    private ArrayList <CountTrial> trials;

    /**
     * Count constructor
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Count(String owner, String experimentID) {
        super(owner, experimentID);
        this.trials = new ArrayList<>();
    }

    public Count() {
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
    public Count(String owner, String experimentID, String status, String title,
                 String description, String region, ArrayList<String> subscribers,
                 ArrayList<String> questions, boolean geoLocation, Date datePublished,
                 int minTrials, ArrayList<CountTrial> trials) {
        super(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, minTrials);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     */
    public void addTrial() {
        CountTrial trial = new CountTrial();
        trials.add(trial);
        addTrialToDB(trial);
    }

    public void addTrialToDB (CountTrial trial){
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("context", trial);
        db.addDataToCollection("Experiments/"+experimentID+"/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Function for setting the trials of the count experiment
     * @param trials
     *  ArrayList of count trials
     */
    public void setTrials(ArrayList<CountTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials of the count experiment
     * @return
     *  ArrayList of count trials
     */
    public ArrayList<CountTrial> getTrials() {
        return trials;
    }
}
