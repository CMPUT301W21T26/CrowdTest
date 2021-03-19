package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Experimenter class that represents a user of the app
 */
public class Experimenter implements Serializable {

    // Experimenter attributes
    private String status;
    private UserProfile userProfile;
    private ArrayList<String> subscribedExperimentIDs;
    private ArrayList<String> commentIDs;

    /**
     * Experimenter constructor
     * @param userProfile
     *  User profile of experimenter
     */
    public Experimenter(UserProfile userProfile) {
        // Initialize Experimenter attributes
        this.userProfile = userProfile;
        this.status = "experimenter";
        this.subscribedExperimentIDs = new ArrayList<>();
    }

    /**
     * Function for setting the subscribed experiments of the experimenter
     * @param subscribedExperimentIDs
     *  ArrayList of experiment IDs
     */
    public void setSubscribedExperimentIDs(ArrayList<String> subscribedExperimentIDs) {
        this.subscribedExperimentIDs = subscribedExperimentIDs;
    }

    /**
     * Function for getting the subscribed experiments of the experimenter
     * @return
     *  Experimenter's subscribed experiments
     */
    public ArrayList<String> getSubscribedExperimentIDs() {
        return subscribedExperimentIDs;
    }

    /**
     * Function for subscribing to an experiment if not already subscribed to
     * @param experiment
     *     The experiment that is being subscribed to by the Experimenter
     */
    public void subscribe(Experiment experiment) {
        if (!subscribedExperimentIDs.contains(experiment.getExperimentID())) {
            subscribedExperimentIDs.add(experiment.getExperimentID());
        } else {
            throw new IllegalArgumentException("Experiment has already been subscribed to!");
        }
    }

    /**
     * Function for unsubscribing to an experiment if previously subscribed to
     * @param experiment
     *  Experiment to unsubscribe from
     */
    public void unsubscribe(Experiment experiment) {
        if (subscribedExperimentIDs.contains(experiment.getExperimentID())) {
            subscribedExperimentIDs.remove(experiment.getExperimentID());
        } else {
            throw new IllegalArgumentException("Experiment has not been subscribed to!");
        }
    }

    /**
     * Function for setting the comments of the experimenter
     * @param commentIDs
     *  ArrayList of comment IDs
     */
    public void setCommentIDs(ArrayList<String> commentIDs) {
        this.commentIDs = commentIDs;
    }

    /**
     * Function for getting the comments of the experimenter
     * @return
     *  Experimenter's comments
     */
    public ArrayList<String> getCommentIDs() {
        return commentIDs;
    }

    /**
     * Function for getting the status of the experimenter
     * @return
     *  Experimenter's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting the status of the experimenter
     * @param status
     *  Experimenter's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Function for getting the user profile of the experimenter
     * @return
     *  Experimenter's user profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }
}
