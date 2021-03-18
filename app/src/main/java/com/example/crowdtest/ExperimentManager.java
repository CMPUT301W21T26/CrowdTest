package com.example.crowdtest;

import android.util.Log;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays; //TODO: remove this
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

        //TODO: make it so that each experiment created uses actual user (ie add code for actual user here)
        if (type == "binomial") {
            //call ExperimentManager.getExperimenter(ownerID)
            experiment = new Binomial(owner, experimentID);
            ((Binomial) experiment).setTrials((ArrayList<BinomialTrial>) document.getData().get("trials"));
        }
        else if (type == "count") {
            experiment = new Count(owner, experimentID);
            ((Count) experiment).setTrials((ArrayList<CountTrial>) document.getData().get("trials"));
        }
        else if (type =="measurement") {
            experiment = new Measurement(owner, experimentID);
            ((Measurement) experiment).setTrials((ArrayList<MeasurementTrial>) document.getData().get("trials"));
        }
        else {
            experiment = new NonNegative(owner, experimentID);
            ((NonNegative) experiment).setTrials((ArrayList<NonNegativeTrial>) document.getData().get("trials"));
        }

        experiment.setStatus((String) document.getData().get("status"));
        experiment.setTitle((String) document.getData().get("title"));
        experiment.setDescription((String) document.getData().get("description"));
        experiment.setRegion((String) document.getData().get("region"));
        experiment.setQuestions((ArrayList<String>) document.getData().get("questions"));
        experiment.setSubscribers((ArrayList<String>) document.getData().get("subscribers"));
        experiment.setGeoLocation((Boolean) document.getData().get("geolocation"));
        

        return experiment;
    }


    public Boolean experimentContainsKeyword(String searchString, Experiment experiment) {

        if (experiment.getTitle().contains(searchString)) {

            return true;
        }
        else if (experiment.getDescription().contains(searchString)) {

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
     *
     * @return
     */
    public ArrayList<String> getAllExperimentIDs() {
        return getAllDocuments(collectionPath);
    }

    /**
     *
     * @return
     *
     */
    public ArrayList<Experiment> getAllExperimentInfo() {
        return new ArrayList<Experiment>();
    }


    /**
     * Function for adding an experiment to the database
     */
    public Experiment publishExperiment(String owner, String type) {
        // Generate unique experiment ID and create experiment
        String experimentID = generateExperimentID();
        Experiment experiment;
        HashMap<String, Object> experimentData = new HashMap<>();
        if (type == "binomial") {
            experiment = new Binomial(owner, experimentID);
            experimentData.put("trials", ((Binomial)experiment).getTrials());
        }
        else if (type =="count"){
            experiment = new Count(owner, experimentID);
            experimentData.put("trials", ((Count)experiment).getTrials());
        }
        else if (type =="measurement") {
            experiment = new Measurement(owner, experimentID);
            experimentData.put("trials", ((Measurement)experiment).getTrials());
        }
        else {
            experiment = new NonNegative(owner, experimentID);
            experimentData.put("trials", ((NonNegative)experiment).getTrials());
        }

        //owners are automatically subscribed to their published experiments
        experiment.addSubscriber(owner);

        // Add experiment data to HashMap
        experimentData.put("owner", owner);
        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());



        // Add experiment to database
        // TODO: add questions as a sub-collection
        addDataToCollection(collectionPath, experimentID, experimentData);

        return experiment;
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
        experimentData.put("type", experiment.getType());

        // Add experiment to database
        addDataToCollection(collectionPath, experiment.getExperimentID(), experimentData);
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
