package com.example.crowdtest.experiments;


import android.location.Location;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

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
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     * @param status        Whether the experiment is closed or open
     * @param title         The title of the experiment
     * @param description   The description of the experiment
     * @param region        The region of the experiment
     * @param subscribers   ArrayList of subscribers to this experiment
     * @param questions     ArrayList of Questions for this experiment
     * @param geoLocation   Whether this experiment requires geo location or not
     * @param datePublished The date the experiment was published
     * @param minTrials     The minimum number of trials needed to be done
     * @param trials        The ArrayList of trials
     * @param published     Whether the experiment should be published or not
     */
    public Binomial(String owner, String experimentID, String status, String title,
                    String description, String region, ArrayList<String> subscribers,
                    ArrayList<String> blackListedUsers, ArrayList<Question> questions,
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

    /**
     * Empty constructor for database
     */
    public Binomial() {

    }


    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(boolean trialInput, String userName, Location location) {
        BinomialTrial trial = new BinomialTrial(trialInput);
        if (geolocationEnabled) trial.setLocation(location);
        trial.setPoster(userName);
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

    /**
     * Method for adding trials to the database
     *
     * @param trial The trial to be added to the database
     */
    public void addTrialToDB(BinomialTrial trial) {
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("locationLong", trial.getLocationLong());
        trialData.put("locationLat", trial.getLocationLat());
        trialData.put("timestamp", trial.getTimestamp());
        trialData.put("success", trial.isSuccess());
        trialData.put("user", trial.getPoster());
        db.addDataToCollection("Experiments/" + experimentID + "/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Method for getting the number of recorded success trials for the binomial experiment
     *
     * @return Number of success trials
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * Method for getting the number of successes without the blacklisted experimenters
     *
     * @return Number of valid successes
     */
    public int getValidSuccessCount() {

        Integer validSuccessCount = 0;

        for (BinomialTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster()) && trial.isSuccess()) {

                validSuccessCount++;
            }
        }

        return validSuccessCount;
    }

    /**
     * Function for getting the number of recorded fail trials for the binomial experiment
     *
     * @return Number of fail trials
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * Method for getting the number of failures without the blacklisted experimenters
     *
     * @return Number of valid failures
     */
    public int getValidFailCount() {

        Integer validFailCount = 0;

        for (BinomialTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster()) && !trial.isSuccess()) {

                validFailCount++;
            }
        }

        return validFailCount;
    }

    /**
     * Function for setting the trials for the binomial experiment
     *
     * @param trials ArrayList of binomial trials
     */
    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }


    /**
     * Method for getting the trials ArrayList
     *
     * @return An ArrayList of trials
     */
    public ArrayList<BinomialTrial> getTrials() {

        return this.trials;
    }

    /**
     * Function for getting the trials for the binomial experiment
     *
     * @return ArrayList of binomial trials
     */
    public ArrayList<BinomialTrial> getValidTrials() {

        ArrayList<BinomialTrial> validTrials = new ArrayList<>();

        for (BinomialTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster())) {

                validTrials.add(trial);
            }
        }

        return validTrials;
    }

    /**
     * Method for getting the statistic data of the experiment
     *
     * @return A hash table of the statistic values
     */
    public Hashtable<String, Double> getStatistics() {

        Hashtable<String, Double> statistics = new Hashtable<>();

        statistics.put("Total Trials", new Double(trials.size()));
        statistics.put("Successes", new Double(getSuccessCount()));
        statistics.put("Failures", new Double(getFailCount()));
        statistics.put("SuccessRate", getSuccessRate());

        return statistics;
    }


    /**
     * Method for getting the success rate of the experiment
     *
     * @return The success rate
     */
    private Double getSuccessRate() {

        return (new Double(getSuccessCount()) / new Double(trials.size())) * 100;
    }


}
