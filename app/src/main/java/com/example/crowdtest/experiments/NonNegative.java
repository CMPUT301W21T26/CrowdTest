package com.example.crowdtest.experiments;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class NonNegative extends Experiment {
    private ArrayList <NonNegativeTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public NonNegative(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    public NonNegative() {
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
    public NonNegative(String owner, String experimentID, String status, String title,
                       String description, String region, ArrayList<String> subscribers,
                       ArrayList<String> questions, boolean geoLocation, Date datePublished,
                       int minTrials, ArrayList<NonNegativeTrial> trials) {
        super(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, minTrials);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(int trialInput) throws Exception {
        NonNegativeTrial trial = new NonNegativeTrial(trialInput);
        trials.add(trial);
        addTrialToDB(trial);
    }

    public void addTrialToDB (NonNegativeTrial trial){
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("context", trial);
        db.addDataToCollection("Experiments/"+experimentID+"/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    public void setTrials(ArrayList<NonNegativeTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<NonNegativeTrial> getTrials() {
        return trials;
    }
}
