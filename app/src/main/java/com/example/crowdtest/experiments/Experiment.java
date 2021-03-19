package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.Question;
import com.google.firebase.firestore.CollectionReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 */
public abstract class Experiment implements Serializable {

    // Experiment attributes
    protected String experimentID;
    protected String owner;
    protected String status;
    protected String title;
    protected String description;
    protected String region;
    protected ArrayList<String> subscribers; //array of subscriber usernames
    protected ArrayList<String> questions; //Array of question ids
    protected boolean geolocation;
    protected String type;
    protected Date datePublished;
    protected int minTrials;


    public Experiment() {

    }

    /**
     * Experiment constructor
     *
     * @param owner        Owner of the experiment
     * @param experimentID A unique ID for this experiment
     */
    public Experiment(String owner, String experimentID) {
        this.owner = owner;
        this.experimentID = experimentID;
        datePublished = new Date();
        status = "open";
        this.subscribers = new ArrayList<>();
        this.questions = new ArrayList<>();

    }

    /**
     *
     * @param type
     */
    public void setType(String type) {

        this.type = type;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSubscribers(ArrayList<String> subscribers) {

        this.subscribers = subscribers;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public boolean isGeoLocation() {
        return geolocation;
    }

    public void setGeoLocation(boolean geoLocation) {
        this.geolocation = geoLocation;
    }

    public boolean getGeoLocation() { return this.geolocation;}

    /**
     * Get the type of experiment for insertion into the database
     */
    public String getType() {
        return this.type;
    }

    /**
     * Function for returning experimentID
     *
     * @return
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Function for getting experiment owner
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Function for getting experiment title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function for getting experiment title
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting experiment description
     *
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting experiment region
     *
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting experiment region
     *
     * @return
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting experiment status
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting experiment status
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    /**
     * @param username
     */
    public void addSubscriber(String username) {
        subscribers.add(username);
    }

    /**
     * @return
     */
    public ArrayList<String> getQuestions() {
        return questions;
    }

    /**
     * @param questionID
     */
    public void addQuestion(String questionID) {
        questions.add(questionID);
    }

    public Date getDatePublished() {
        return datePublished;
    }

    /**
     * @param trialCount
     */
    public void setMinTrials(int trialCount) {
        minTrials = trialCount;
    }

    /**
     * @return
     */
    public int getMinTrials() {
        return minTrials;
    }
}
