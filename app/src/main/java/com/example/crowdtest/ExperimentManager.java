package com.example.crowdtest;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.BinomialTrialList;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.CountTrialList;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.MeasurementTrialList;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.example.crowdtest.experiments.NonNegativeTrialList;
import com.example.crowdtest.experiments.Trial;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays; //TODO: remove this
import java.util.HashMap;
import java.util.List;

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
        if (type.equals("binomial")) {

            experiment = new Binomial(owner, experimentID);

            ArrayList<BinomialTrial> trials = document.toObject(BinomialTrialList.class).trials;

            ((Binomial) experiment).setTrials(trials);
        }

        else if (type.equals("count")) {

            experiment = new Count(owner, experimentID);

            ArrayList<CountTrial> trials = document.toObject(CountTrialList.class).trials;

            ((Count) experiment).setTrials(trials);
        }

        else if (type.equals("measurement")) {
            experiment = new Measurement(owner, experimentID);

            ArrayList<MeasurementTrial> trials = document.toObject(MeasurementTrialList.class).trials;
            ((Measurement) experiment).setTrials(trials);
        }
        else {
            experiment = new NonNegative(owner, experimentID);

            ArrayList<NonNegativeTrial> trials = document.toObject(NonNegativeTrialList.class).trials;
            ((NonNegative) experiment).setTrials(trials);
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
        experimentData.put("geolocation", experiment.getGeoLocation());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("questions",experiment.getQuestions());
        experimentData.put("type", experiment.getType());
        experimentData.put("datePublished", experiment.getDatePublished());
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
        experimentData.put("type", experiment.getType());

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
