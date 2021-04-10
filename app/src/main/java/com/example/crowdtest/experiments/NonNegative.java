package com.example.crowdtest.experiments;

import android.location.Location;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Class to represent a NonNegative experiment
 */
public class NonNegative extends Experiment {
    private ArrayList<NonNegativeTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner        Owner of the experiment
     * @param experimentID A unique ID for this experiment
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
    public NonNegative(String owner, String experimentID, String status, String title,
                       String description, String region, ArrayList<String> subscribers,
                       ArrayList<String> blackListedUsers, ArrayList<Question> questions,
                       boolean geoLocation, Date datePublished, int minTrials,
                       ArrayList<NonNegativeTrial> trials, boolean published) {
        super(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, minTrials, published);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(int trialInput, String userName, Location location) throws Exception {
        NonNegativeTrial trial = new NonNegativeTrial(trialInput);
        if (geolocationEnabled) trial.setLocation(location);
        trial.setPoster(userName);
        trials.add(trial);
        addTrialToDB(trial);
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trial The trial that is going to be submitted in the experiment
     */
    public void addTrialToDB(NonNegativeTrial trial) {
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("locationLong", trial.getLocationLong());
        trialData.put("locationLat", trial.getLocationLat());
        trialData.put("timestamp", trial.getTimestamp());
        trialData.put("count", trial.getCount());
        trialData.put("user", trial.getPoster());
        db.addDataToCollection("Experiments/" + experimentID + "/trials", "trial#" + String.format("%05d", trials.size() - 1), trialData);
    }

    /**
     * Function for setting the trials for the non-negative experiment
     *
     * @param trials ArrayList of non-negative trials
     */
    public void setTrials(ArrayList<NonNegativeTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<NonNegativeTrial> getTrials() {

        return this.trials;
    }

    /**
     * Function for getting the trials for the non-negative experiment
     *
     * @return ArrayList of non-negative trials
     */
    public ArrayList<NonNegativeTrial> getValidTrials() {
        ArrayList<NonNegativeTrial> validTrials = new ArrayList<>();

        for (NonNegativeTrial trial : trials) {

            if (!blackListedUsers.contains(trial.getPoster())) {

                validTrials.add(trial);
            }
        }

        return validTrials;

    }

    /**
     * Method for getting the success rate of the experiment
     *
     * @return The success rate
     */
    public Hashtable<String, Double> getStatistics() {

        Hashtable<String, Double> statistics = new Hashtable<>();

        statistics.put("Mean", 4.0);

        return statistics;
    }

    ;

}
