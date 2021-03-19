package com.example.crowdtest.experiments;


import com.example.crowdtest.DatabaseManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Binomial extends Experiment {
    private ArrayList<BinomialTrial> trials;
    private int successCount;
    private int failCount;

    /**
     * Database addition constructor
     *
     * @param owner of the experiment
     * @param experimentID A unique ID for this experiment
     * @param trials The ArrayList of trials
     */

    /**
     * Constructor for uploading from the database
     *
     * @param owner of the experiment
     * @param experimentID A unique ID for this experiment
     * @param status
     * @param title
     * @param description
     * @param region
     * @param subscribers
     * @param questions
     * @param geoLocation
     * @param datePublished
     * @param minTrials
     * @param trials The ArrayList of trials
     */
    public Binomial(String owner, String experimentID, String status, String title,
                    String description, String region, ArrayList<String> subscribers,
                    ArrayList<String> questions, boolean geoLocation, Date datePublished,
                    int minTrials, ArrayList<BinomialTrial> trials) {
        super(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, minTrials);
        this.trials = trials;
        successCount = 0;
        failCount = 0;
        for (BinomialTrial trial : trials) {
            if (trial.isSuccess()) {
                successCount += 1;
            } else {
                failCount += 1;
            }
        }
    }

    /**
     * Experiment constructor
     *
     * @param owner        Owner of the experiment
     * @param experimentID A unique ID for this experiment
     */
    public Binomial(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
        successCount = 0;
        failCount = 0;
    }

    public Binomial() {

    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(boolean trialInput) {
        BinomialTrial trial = new BinomialTrial(trialInput);
        trials.add(trial);
        if (trialInput) {
            successCount += 1;
        } else {
            failCount += 1;
        }
        addTrialToDB(trial);
    }

    public void addTrialToDB(BinomialTrial trial) {
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("context", trial);
        db.addDataToCollection("Experiments/" + experimentID + "/trials", "trial#" + (trials.size() - 1), trialData);
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<BinomialTrial> getTrials() {
        return trials;
    }
}
