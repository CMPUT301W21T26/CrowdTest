package com.example.crowdtest.experiments;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

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
    protected ArrayList<String> blackListedUsers;
    protected ArrayList<String> questions; //Array of question ids
    protected Date datePublished;
    protected int minTrials;
    protected boolean geolocationEnabled;
    protected boolean published;

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
        status = "Open";
        this.subscribers = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.blackListedUsers = new ArrayList<>();
        this.published = true;
    }

    /**
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
     * @param published
     */
    public Experiment(String owner, String experimentID, String status,
                      String title, String description, String region,
                      ArrayList<String> subscribers, ArrayList<String> blackListedUsers,
                      ArrayList<String> questions, boolean geoLocation, Date datePublished,
                      int minTrials, boolean published) {
        this.experimentID = experimentID;
        this.owner = owner;
        this.status = status;
        this.title = title;
        this.description = description;
        this.region = region;
        this.subscribers = subscribers;
        this.blackListedUsers = blackListedUsers;
        this.questions = questions;
        this.geolocationEnabled = geoLocation;
        this.datePublished = datePublished;
        this.minTrials = minTrials;
        this.published = published;
    }

    /**
     *
     */
    protected Experiment() { }

    /**
     *
     * @return
     */
    public boolean isPublished() {
        return this.published;
    }

    /**
     *
     * @param published
     */
    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * Function for adding an experimenter to the collection of subscribers for the experiment
     *
     * @param username Unique username of new subscriber
     */
    public void addSubscriber(String username) {
        subscribers.add(username);
    }

    /**
     * Function for removing an experimenter from the collection of subscribers for the experiment
     *
     * @param username Unique username of new subscriber
     */
    public void removeSubscriber(String username) {
        subscribers.remove(username);
    }

    /**
     * Function for adding an experimenter to the collection of blacklisted users for the experiment
     *
     * @param username Unique username of user to blacklist
     */
    public void addBlackListedUser(String username) {
        blackListedUsers.add(username);
    }

    /**
     * Function for removing an experimenter from the collection of blacklisted users for the experiment
     *
     * @param username Unique username of blacklisted user
     */
    public void removeBlackListedUser(String username) {
        blackListedUsers.remove(username);
    }

    /**
     * Function for adding a question to the collection of question for the experiment
     *
     * @param question the question
     */
    public void addQuestion(String question) {
        questions.add(question);
    }

    /**
     * Function for getting the ID of the experiment
     *
     * @return Unique ID of experiment
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Function for setting the ID of the experiment
     *
     * @param experimentID Unique ID of experiment
     */
    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public boolean isGeoLocationEnabled() {
        return geolocationEnabled;
    }

    public void setGeoLocation(boolean geoLocation) {
        this.geolocationEnabled = geoLocation;
    }

    /**
     * Function for getting the ID of the experiment's owner
     *
     * @return Unique ID of experiment owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Function for setting the ID of the experiment's owner
     *
     * @param owner Unique ID of experiment owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Function for getting the IDs of the experiment's subscribers
     *
     * @return ArrayList of subscriber IDs
     */
    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    /**
     * Function for setting the IDs of the experiment's subscribers
     *
     * @param subscribers ArrayList of subscriber IDs
     */
    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }


    /**
     * Function fro getting the IDs of the experiment's blacklisted users
     * @return
     */
    public ArrayList<String> getBlackListedUsers() {
        return blackListedUsers;
    }

    /**
     * Function for setting the IDs of the experiment's blacklsited users
     * @param blackListedUsers
     */
    public void setBlackListedUsers(ArrayList<String> blackListedUsers) {
        this.blackListedUsers = blackListedUsers;
    }

    /**
     * Function for checking whether geolocation is enabled for an experiment
     *
     * @return True if geolocation is enabled, false otherwise
     */
    public boolean isGeolocationEnabled() {
        return geolocationEnabled;
    }

    /**
     * Function for setting whether geolocation is enabled for an experiment
     *
     * @param geolocationEnabled Boolean defining whether geolocation is enabled or not
     */
    public void setGeolocationEnabled(boolean geolocationEnabled) {
        this.geolocationEnabled = geolocationEnabled;
    }

    /**
     * Function for getting the title of the experiment
     *
     * @return Title of experiment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function for setting the title of the experiment
     *
     * @param title Title of experiment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function for getting the description of the experiment
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting experiment description
     *
     * @return Description of experiment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting experiment region
     *
     * @return Region of experiment
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting experiment region
     *
     * @return Region of experiment
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting experiment status
     *
     * @return Status of experiment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting experiment status
     *
     * @param status Status of experiment
     */
    public void setStatus(String status) {
        this.status = status;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Experiments").document(experimentID).update("status", status);
    }


    public ArrayList<String> getQuestions() {
        return questions;
    }

    /**
     * Function for getting the publish date of the experiment
     *
     * @return Publish date of experiment
     */
    public Date getDatePublished() {
        return datePublished;
    }

    /**
     * Function for setting the minimum number of trials for the experiment
     *
     * @param trialCount Minimum number of trials of experiment
     */
    public void setMinTrials(int trialCount) {
        minTrials = trialCount;
    }

    /**
     * Function for getting the minimum number of trials for the experiment
     *
     * @return Minimum number of trials of experiment
     */
    public int getMinTrials() {
        return minTrials;
    }
}
