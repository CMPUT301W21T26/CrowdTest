package com.example.crowdtest;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.example.crowdtest.experiments.Trial;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 */
public class ExperimentManager extends DatabaseManager {

    final private String collectionPath = "Experiments";
    private String TAG = "GetExperiments";

    /**
     * ExperimentManager constructor
     */
    public ExperimentManager() {
        super();
    }

    /**
     * Function for generating a unique experiment ID
     * @return
     */
    public String generateExperimentID() {
        return generateDocumentID("experiment", collectionPath);
    }

    /**
     * Converts a document obtained from Firestore into an Experiment object
     * @param document
     *    The firestore document to be converted
     * @return
     *     Returns the experiment object
     */
    public Experiment getFirestoreExperiment(QueryDocumentSnapshot document) {

        Experiment experiment;

        String experimentID = document.getId();
        String owner = (String) document.getData().get("owner");
        String type = (String) document.getData().get("type");
        Date datePublished = ((Timestamp) document.getData().get("datePublished")).toDate();
        String description = (String) document.getData().get("description");
        boolean geoLocation = (boolean) document.getData().get("geolocation");
//        int minTrialCount = Integer.parseInt((String) document.getData().get("min trial count"));
        String status = (String) document.getData().get("status");
        String title = (String) document.getData().get("title");
        String region = (String) document.getData().get("region");
        ArrayList<String> questions = (ArrayList<String>) document.getData().get("questions");
        ArrayList<String> subscribers = (ArrayList<String>) document.getData().get("subscribers");
        database.collection(collectionPath).document(document.getId()).collection("trials").get();

        //TODO: make it so that each experiment created uses actual user (ie add code for actual user here)
        if (type.equals("binomial")) {
            ArrayList<BinomialTrial> trials = (ArrayList<BinomialTrial>) database.collection(document.getDocumentReference());
            experiment = new Binomial(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<BinomialTrial> trials = document.toObject(((Binomial)experiment).getClass()).getTrials();
//            ((Binomial) experiment).setTrials(trials);
        }

        else if (type.equals("count")) {

            ArrayList<CountTrial> trials = (ArrayList<CountTrial>) document.get("trials");
            experiment = new Count(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<CountTrial> trials = document.toObject(((Count)experiment).getClass()).getTrials();
//            ((Count) experiment).setTrials(trials);
        }

        else if (type.equals("measurement")) {
            ArrayList<MeasurementTrial> trials = (ArrayList<MeasurementTrial>) document.get("trials");
            experiment = new Measurement(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<MeasurementTrial> trials = document.toObject(((Measurement)experiment).getClass()).getTrials();
//            ((Measurement) experiment).setTrials(trials);
        }
        else {
            ArrayList<NonNegativeTrial> trials = (ArrayList<NonNegativeTrial>) document.get("trials");
            experiment = new NonNegative(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);
//            ArrayList<NonNegativeTrial> trials = document.toObject(((NonNegative)experiment).getClass()).getTrials();
//            ((NonNegative) experiment).setTrials(trials);
        }

        return experiment;
    }


    public Boolean experimentContainsKeyword(String searchString, Experiment experiment) {

        if (experiment.getTitle().toLowerCase().contains(searchString.toLowerCase())) {

            return true;
        }
        else if (experiment.getDescription().toLowerCase().contains(searchString.toLowerCase())) {

            return true;
        }

        return false;

    }

    /**
     *
     * @param user
     * @param experiment
     * @return
     */
    public Boolean experimentIsOwned(Experimenter user, Experiment experiment) {

        String ownerName = experiment.getOwner();

        String userName = user.getUserProfile().getUsername();

        if (ownerName.equals(userName)){
            return true;
        }

        return false;

    }

    public Boolean experimentIsSubscribed(Experimenter user, Experiment experiment) {

        String userName = user.getUserProfile().getUsername();

        ArrayList<String> subscribedUsers = experiment.getSubscribers();

        if (subscribedUsers.contains(userName)) {

            return true;
        }

        return false;
    }

    /**
     * Function for adding an experiment to the database
     */
    public void publishExperiment(Experiment experiment) {
        // Generate unique experiment ID and create experiment
        String experimentID = experiment.getExperimentID();

        // Retrieve experiment owner's profile
        //UserProfile ownerProfile = experiment.getOwner().getUserProfile();

        experiment.addSubscriber(experiment.getOwner());

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        //experimentData.put("owner", ownerProfile.getUsername());

        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("geolocation", experiment.isGeoLocation());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("questions",experiment.getQuestions());
        experimentData.put("type", experiment.getClass().toString());
        experimentData.put("datePublished", experiment.getDatePublished());
        experimentData.put("min trial count", experiment.getMinTrials());
        if (experiment instanceof Measurement){
            experimentData.put("trials", ((Measurement)experiment).getTrials());
        }
        else if (experiment instanceof NonNegative){
            experimentData.put("trials", ((NonNegative)experiment).getTrials());
        }
        else if (experiment instanceof Count){
            experimentData.put("trials", ((Count)experiment).getTrials());
        }
        else if (experiment instanceof Binomial){
            experimentData.put("trials", ((Binomial)experiment).getTrials());
        }

        experimentData.put("owner", experiment.getOwner());

        // Add experiment to database
        // TODO: add questions as a sub-collection
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Function for updating a given experiment in the database
     * @param experiment
     */
    public void updateExperiment(Experiment experiment) {

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("owner", experiment.getOwner());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("type", experiment.getClass().toString());

        // Add experiment to database
        addDataToCollection(collectionPath, experiment.getExperimentID(), experimentData);
    }

    public void endExperiment(Experiment experiment) {
        HashMap<String, Object> experimentStatus = new HashMap<>();

        experiment.setStatus("closed");
        experimentStatus.put("status", experiment.getStatus());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentStatus);

    }

    /**
     * Function for deleting an experiment from the database
     * @param experiment
     */
    public void unpublishExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();

        // Remove experiment from database
        removeDataFromCollection(collectionPath, experimentID);
    }
}
