package com.example.crowdtest.experiments;

import android.location.Location;

import android.location.Location;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Class to represent a Count Experiment
 */
public class Count extends Experiment {
    private ArrayList<CountTrial> trials;

    /**
     * Count constructor
     *
     * @param owner        Owner of the experiment
     * @param experimentID A unique ID for this experiment
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
                 ArrayList<String> blackListedUsers, ArrayList<Question> questions,
                 boolean geoLocation, Date datePublished, int minTrials,
                 ArrayList<CountTrial> trials, boolean published) {
        super(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, minTrials, published);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     */
    public void addTrial(String userName, Location location) {
        CountTrial trial = new CountTrial();
        if (geolocationEnabled) trial.setLocation(location);
        trial.setPoster(userName);
        trials.add(trial);
        addTrialToDB(trial);
    }

    /**
     * Method for adding trials to the database
     *
     * @param trial The trial to be added to the database
     */
    public void addTrialToDB(CountTrial trial) {
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("locationLong", trial.getLocationLong());
        trialData.put("locationLat", trial.getLocationLat());
        trialData.put("timestamp", trial.getTimestamp());
        trialData.put("user", trial.getPoster());
        db.addDataToCollection("Experiments/" + experimentID + "/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Function for setting the trials of the count experiment
     *
     * @param trials ArrayList of count trials
     */
    public void setTrials(ArrayList<CountTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<CountTrial> getTrials() {

        return this.trials;
    }

    /**
     * Function for getting the trials of the count experiment
     *
     * @return ArrayList of count trials
     */
    public ArrayList<CountTrial> getValidTrials() {
        ArrayList<CountTrial> validTrials = new ArrayList<>();

        for (CountTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster())) {

                validTrials.add(trial);
            }
        }

        return validTrials;
    }

    /**
     * Method for getting the number of recorded trials for the experiment
     *
     * @return Number of trials submitted
     */
    public int getCount() {
        return trials.size();
    }

    public Integer getValidCount() {

        Integer count = 0;

        for (CountTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster())) {

                count++;
            }
        }

        return count;

    }

    /**
     * Method for getting the success rate of the experiment
     *
     * @return The success rate
     */
    public Hashtable<String, Double> getStatistics() {

        Hashtable<String, Double> statistics = new Hashtable<>();

        statistics.put("Total Trials", new Double(trials.size()));

        return statistics;
    }

    ;
}
