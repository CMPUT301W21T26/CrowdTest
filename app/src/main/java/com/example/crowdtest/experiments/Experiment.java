package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.Question;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public abstract class Experiment {

    // Experiment attributes
    protected String experimentID;
    protected Experimenter owner;
    protected String status;
    protected String title;
    protected String description;
    protected String region;
    protected ArrayList<String> subscribers;
    protected ArrayList<Question> questions;
    protected boolean geoLocation;
    protected String type;
    protected ArrayList<Trial> trials;

    /**
     * Experiment constructor
     *
     * @param owner        Owner of the experiment
     * @param experimentID A unique ID for this experiment
     */
    public Experiment(Experimenter owner, String experimentID) {
        this.status = "active";
        this.owner = owner;
        this.experimentID = experimentID;
        subscribers = new ArrayList<>();

        //TODO: remove the following initializations:
        this.status = "Open";
        this.title = "Experiment " + experimentID;
        this.description = "Description";
        this.region = "Region";
    }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    };

    public ArrayList<Trial> getTrials() {

        return trials;
    };

    public void setType(String type) {

        this.type = type;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }
    
    public void setOwner(Experimenter owner) {
        this.owner = owner;
    }

    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public boolean isGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(boolean geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * Get the type of experiment for insertion into the database
     */
    public String getType() {
        return this.type;
    };

    /**
     * Adds a new trial to the experiment
     *
     * @param trial The trial that is going to be submitted in the experiment
     */
    public abstract void addTrial(Trial trial);

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
    public Experimenter getOwner() {
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
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * @param question
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }


}
