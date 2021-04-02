package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.Question;
import com.google.firebase.firestore.CollectionReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Class to represent a generic Experiment
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
    protected Date datePublished;
    protected int minTrials;
    protected boolean geolocationEnabled;
    protected boolean published;

    /**
     * Experiment constructor
     * @param ownerID
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public Experiment(String owner, String experimentID) {
        this.owner = owner;
        this.experimentID = experimentID;
        datePublished = new Date();
        status = "open";
        this.subscribers = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.published = true;
    }

    public boolean isPublished(){

        return this.published;

    }

    public void setPublished(boolean published) {

        this.published = published;
    }

    /**
     * Function for adding an experimenter's ID to the collection of subscriber IDs for the experiment
     * @param username
     *  Unique username of new subscriber
     * Constructor for uploading from the database
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
    public void addSubscriber(String username) {
        subscribers.add(username);
    }
    public Experiment(String owner, String experimentID,  String status,
                      String title, String description, String region,
                      ArrayList<String> subscribers, ArrayList<String> questions,
                      boolean geoLocation, Date datePublished, int minTrials) {
        this.experimentID = experimentID;
        this.owner = owner;
        this.status = status;
        this.title = title;
        this.description = description;
        this.region = region;
        this.subscribers = subscribers;
        this.questions = questions;
        this.geoLocation = geoLocation;
        this.datePublished = datePublished;
        this.minTrials = minTrials;
    }

    /**
     * Function for adding a question to the collection of question for the experiment
     * @param question
     *  the question
     */
    public void addQuestion(String question) {
        questionIDs.add(question);
    /**
     * Function for getting the ID of the experiment
     * @return
     *  Unique ID of experiment
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Function for setting the ID of the experiment
     * @param experimentID
     *  Unique ID of experiment
     */
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

    /**
     * Function for getting the ID of the experiment's owner
     * @return
     *  Unique ID of experiment owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Function for setting the ID of the experiment's owner
     * @param ownerID
     *  Unique ID of experiment owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Function for getting the IDs of the experiment's subscribers
     * @return
     *  ArrayList of subscriber IDs
     */
    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    /**
     * Function for setting the IDs of the experiment's subscribers
     * @param subscriberIDs
     *  ArrayList of subscriber IDs
     */
    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }
    /**
     * Function for checking whether geolocation is enabled for an experiment
     * @return
     *  True if geolocation is enabled, false otherwise
     */
    public boolean isGeolocationEnabled() { return geolocationEnabled;}

    /**
     * Function for setting whether geolocation is enabled for an experiment
     * @param geolocationEnabled
     *  Boolean defining whether geolocation is enabled or not
     */
    public void setGeolocationEnabled(boolean geolocationEnabled) {
        this.geolocationEnabled = geolocationEnabled;
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
         * Function for getting the title of the experiment
         * @return
         *  Title of experiment
         */
    public String getTitle() {
        return title;
    }

    /**
     * Function for setting the title of the experiment
     * @param title
     *  Title of experiment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function for getting the description of the experiment
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting experiment description
     *
     * @return
     *  Description of experiment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting experiment region
     *
     * @return
     *  Region of experiment
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting experiment region
     *
     * @return
     *  Region of experiment
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting experiment status
     *
     * @return
     *  Status of experiment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting experiment status
     *
     * @param status
     *  Status of experiment
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Function for getting the publish date of the experiment
     * @return
     *  Publish date of experiment
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
     * Function for setting the minimum number of trials for the experiment
     * @param trialCount
     *  Minimum number of trials of experiment
     */
    public void setMinTrials(int trialCount) {
        minTrials = trialCount;
    }

    /**
     * Function for getting the minimum number of trials for the experiment
     * @return
     *  Minimum number of trials of experiment
     */
    public int getMinTrials() {
        return minTrials;
    }
}
