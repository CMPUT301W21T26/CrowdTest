package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an app user (an Experimenter)
 */
public class Experimenter implements Serializable {

    // Experimenter attributes
    private String status;
    private UserProfile userProfile;
    private ArrayList<String> subscribedExperiments;
    private ArrayList<Comment> comments;

    /**
     * Constructor method
     * @param userProfile
     *    Initializes class attribute userProfile
     */
    public Experimenter(UserProfile userProfile) {
        // Initialize Experimenter attributes
        this.userProfile = userProfile;
        this.status = "experimenter";
        this.subscribedExperiments = new ArrayList<>();
    }

    /**
     * Sets subscribedExperiments based on a given ArrayList
     * @param subscribedExperiments
     *     ArrayList of experiments user is subscribed to
     */
    public void setSubscribedExperiments(ArrayList<String> subscribedExperiments) {
        this.subscribedExperiments = subscribedExperiments;
    }

    /**
     * Function for subscribing to an experiment
     * @param experiment
     */
    public void subscribe(Experiment experiment) {
        subscribedExperiments.add(experiment.getExperimentID());
    }

    /**
     *
     */
    public void addComment() {

    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getSubscribedExperiments() {
        return subscribedExperiments;
    }

    /**
     *
     * @return
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }
}
