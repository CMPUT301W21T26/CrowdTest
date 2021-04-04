package com.example.crowdtest.experiments;


import com.example.crowdtest.DatabaseManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class to represent a Binomial experiment
 */
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
                    ArrayList<String> blackListedUsers, ArrayList<String> questions,
                    boolean geoLocation, Date datePublished, int minTrials,
                    ArrayList<BinomialTrial> trials, boolean published) {
        super(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, minTrials, published);
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
     * @param owner
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
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

    /**
     * Adds a new trial to the experiment when getting the experiment from  the database
     *
     * @param binomialTrial The trial that is going to be submitted in the experiment
     */
    public void addTrialFromDb(BinomialTrial binomialTrial) {
        trials.add(binomialTrial);
        if (binomialTrial.isSuccess()) {
            successCount += 1;
        } else {
            failCount += 1;
        }
    }

    public void addTrialToDB(BinomialTrial trial) {
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("location", trial.getLocation());
        trialData.put("timestamp", trial.getTimestamp());
        trialData.put("success", trial.isSuccess());
        db.addDataToCollection("Experiments/" + experimentID + "/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Function for getting the number of recorded success trials for the binomial experiment
     * @return
     *  Number of success trials
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * Function for getting the number of recorded fail trials for the binomial experiment
     * @return
     *  Number of fail trials
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * Function for setting the trials for the binomial experiment
     * @param trials
     *  ArrayList of binomial trials
     */
    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials for the binomial experiment
     * @return
     *  ArrayList of binomial trials
     */
    public ArrayList<BinomialTrial> getTrials() {
        return trials;
    }
}
