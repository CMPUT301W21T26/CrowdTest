package com.example.crowdtest.experiments;

import android.location.Location;

import com.example.crowdtest.DatabaseManager;
import com.example.crowdtest.Question;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

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
    public Measurement(String owner, String experimentID, String status, String title,
                       String description, String region, ArrayList<String> subscribers,
                       ArrayList<String> blackListedUsers, ArrayList<Question> questions,
                       boolean geoLocation, Date datePublished, int minTrials,
                       ArrayList<MeasurementTrial> trials, boolean published) {
        super(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, minTrials, published);
        this.trials = trials;
    }

    /**
     * Adds a new trial to the experiment
     * @param trialInput
     *  The trial that is going to be submitted in the experiment
     */
    public void addTrial(double trialInput, String username,  Location location) {
        MeasurementTrial trial = new MeasurementTrial(trialInput);
        trial.setPoster(username);
        if (geolocationEnabled) trial.setLocation(location);
        trials.add(trial);
        addTrialToDB(trial);
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trial The trial that is going to be submitted in the experiment
     */
    public void addTrialToDB (MeasurementTrial trial){
        DatabaseManager db = new DatabaseManager();
        HashMap<String, Object> trialData = new HashMap<>();
        trialData.put("locationLong", trial.getLocationLong());
        trialData.put("locationLat", trial.getLocationLat());
        trialData.put("timestamp", trial.getTimestamp());
        trialData.put("measurement", trial.getMeasurement());
        trialData.put("user", trial.getPoster());
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

    public ArrayList<MeasurementTrial> getTrials() {

        return this.trials;
    }
    /**
     * Function for getting the trials for the measurement experiment
     * @return
     *  ArrayList of measurement trials
     */
    public ArrayList<MeasurementTrial> getValidTrials() {
        ArrayList<MeasurementTrial> validTrials = new ArrayList<>();

        for (MeasurementTrial trial: trials) {

            if (!blackListedUsers.contains(trial.getPoster())) {

                validTrials.add(trial);
            }
        }

        return validTrials;
    }

}
