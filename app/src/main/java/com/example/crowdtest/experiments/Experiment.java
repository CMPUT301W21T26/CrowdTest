package com.example.crowdtest.experiments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class to represent a generic Experiment
 */
public class Experiment implements Serializable {

    // Experiment attributes
    protected String experimentID;
    protected String ownerID;
    protected String status;
    protected String type;
    protected String title;
    protected String description;
    protected String region;
    protected int minTrials;
    protected Date datePublished;
    protected boolean geolocationEnabled;
    protected ArrayList<String> subscriberIDs;
    protected ArrayList<String> questionIDs;

    /**
     * Experiment constructor
     * @param ownerID
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public Experiment(String ownerID, String experimentID) {
        this.ownerID = ownerID;
        this.experimentID = experimentID;
        datePublished = new Date();
        status = "open";
        this.subscriberIDs = new ArrayList<>();
        this.questionIDs = new ArrayList<>();
    }

    /**
     * Function for adding an experimenter's ID to the collection of subscriber IDs for the experiment
     * @param username
     *  Unique username of new subscriber
     */
    public void addSubscriber(String username) {
        subscriberIDs.add(username);
    }

    /**
     * Function for adding a question's ID to the collection of question IDs for the experiment
     * @param questionID
     *  Unique ID of new question
     */
    public void addQuestion(String questionID) {
        questionIDs.add(questionID);
    }

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

    /**
     * Function for getting the ID of the experiment's owner
     * @return
     *  Unique ID of experiment owner
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Function for setting the ID of the experiment's owner
     * @param ownerID
     *  Unique ID of experiment owner
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Function for getting the IDs of the experiment's subscribers
     * @return
     *  ArrayList of subscriber IDs
     */
    public ArrayList<String> getSubscriberIDs() {
        return subscriberIDs;
    }

    /**
     * Function for setting the IDs of the experiment's subscribers
     * @param subscriberIDs
     *  ArrayList of subscriber IDs
     */
    public void setSubscriberIDs(ArrayList<String> subscriberIDs) {
        this.subscriberIDs = subscriberIDs;
    }

    /**
     * Function for getting the IDs of the experiment's questions
     * @return
     *  ArrayList of question IDs
     */
    public ArrayList<String> getQuestionIDs() {
        return questionIDs;
    }

    /**
     * Function for setting the IDs of the experiment's questions
     * @param questionIDs
     *  ArrayList of questions IDs
     */
    public void setQuestionIDs(ArrayList<String> questionIDs) {
        this.questionIDs = questionIDs;
    }

    /**
     * Function for checking whether geolocation is enabled for an experiment
     * @return
     *  True if geolocation is enabled, false otherwise
     */
    public boolean getGeolocationEnabled() { return geolocationEnabled;}

    /**
     * Function for setting whether geolocation is enabled for an experiment
     * @param geolocationEnabled
     *  Boolean defining whether geolocation is enabled or not
     */
    public void setGeolocationEnabled(boolean geolocationEnabled) {
        this.geolocationEnabled = geolocationEnabled;
    }

    /**
     * Function for getting the experiment type
     * @return
     *  Type of experiment
     */
    public String getType() {
        return this.type;
    }

    /**
     * Function for setting the experiment type
     * @param type
     *  Type of experiment
     */
    public void setType(String type) {
        this.type = type;
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
     *  Description of experiment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting the description of the experiment
     * @return
     *  Description of experiment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting the region of the experiment
     * @return
     *  Region of experiment
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting the region of the experiment
     * @return
     *  Region of experiment
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting the status of the experiment
     * @return
     *  Status of experiment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting the status of the experiment
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
