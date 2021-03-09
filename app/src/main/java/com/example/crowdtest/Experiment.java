package com.example.crowdtest;

import java.util.ArrayList;

/**
 *
 */
public class Experiment {

    // Experiment attributes
    private String experimentID;
    private Experimenter owner;
    private String status;
    private String title;
    private String description;
    private String region;
    private ArrayList<String> subscribers;
    private ArrayList<Question> questions;

    /**
     * Experiment constructor
     * @param owner
     * @param experimentID
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

    /**
     * Function for returning experimentID
     * @return
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Function for getting experiment owner
     * @return
     */
    public Experimenter getOwner() {
        return owner;
    }

    /**
     * Function for getting experiment title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function for getting experiment title
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function for setting experiment description
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for getting experiment region
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * Function for setting experiment region
     * @return
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Function for getting experiment status
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting experiment status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    /**
     *
     * @param username
     */
    public void addSubscriber(String username) {
        subscribers.add(username);
    }

    /**
     *
     * @return
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @param question
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }


}
