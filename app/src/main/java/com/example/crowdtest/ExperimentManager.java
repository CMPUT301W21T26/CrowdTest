package com.example.crowdtest;

import android.location.Location;
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
import com.example.crowdtest.experiments.MeasurementTrialList;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.example.crowdtest.experiments.Trial;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Nonnegative;

/**
 * ExperimentManager class for interfacing with Firestore database to publish, unpublish, and
 * archive experiments
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
     *  Unique experiment ID
     */
    public String generateExperimentID() {
        return generateDocumentID("experiment", collectionPath);
    }

    /**
     * Converts a document obtained from Firestore into an Experiment object
     * @param document
     *  The firestore document to be converted
     * @return
     *  Returns the experiment object
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

        //TODO: make it so that each experiment created uses actual user (ie add code for actual user here)
        if (type.equals("binomial")) {
//            ArrayList<BinomialTrial> trials = (ArrayList<BinomialTrial>) database.collection(document.getDocumentReference());
             ArrayList<BinomialTrial> trials = new ArrayList<>();
             database.collection(collectionPath).document(document.getId()).collection("trials").get().addOnCompleteListener(task -> {
                 if (task.isSuccessful()){
                     for (QueryDocumentSnapshot doc :
                             task.getResult()) {
                         HashMap<String,Object> trialAttributes = (HashMap<String,Object>) doc.getData().get("context");;
                         trials.add(new BinomialTrial(((Timestamp)trialAttributes.get("timestamp")).toDate(), (Location) trialAttributes.get("location"), (boolean) trialAttributes.get("success")));
                     }
                 }
             });
            experiment = new Binomial(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<BinomialTrial> trials = document.toObject(((Binomial)experiment).getClass()).getTrials();
//            ((Binomial) experiment).setTrials(trials);
        }

        else if (type.equals("count")) {

//            ArrayList<CountTrial> trials = (ArrayList<CountTrial>) document.get("trials");
//            ArrayList<CountTrial> trials = (ArrayList<CountTrial>)database.collection(collectionPath).document(document.getId()).collection("trials").get().getResult().toObjects(CountTrial.class);
            ArrayList<CountTrial> trials = new ArrayList<>();
            database.collection(collectionPath).document(document.getId()).collection("trials").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document :
                                task.getResult()) {
                            HashMap<String,Object> trialAttributes = (HashMap<String,Object>) document.getData().get("context");;
                            trials.add(new CountTrial(((Timestamp)trialAttributes.get("timestamp")).toDate(), (Location) trialAttributes.get("location")));
                        }
                    }
                }
            });
            experiment = new Count(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<CountTrial> trials = document.toObject(((Count)experiment).getClass()).getTrials();
//            ((Count) experiment).setTrials(trials);
        }

        else if (type.equals("measurement")) {
//            ArrayList<MeasurementTrial> trials = (ArrayList<MeasurementTrial>) document.get("trials");
//            ArrayList<MeasurementTrial> trials = (ArrayList<MeasurementTrial>)database.collection(collectionPath).document(document.getId()).collection("trials").get().getResult().toObjects(MeasurementTrial.class);
            ArrayList<MeasurementTrial> trials = new ArrayList<>();
            database.collection(collectionPath).document(document.getId()).collection("trials").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document :
                                task.getResult()) {
                            trials.add((MeasurementTrial)document.getData().get("context"));
                        }
                    }
                }
            });
            experiment = new Measurement(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);

//            ArrayList<MeasurementTrial> trials = document.toObject(((Measurement)experiment).getClass()).getTrials();
//            ((Measurement) experiment).setTrials(trials);
        }
        else {
//            ArrayList<NonNegativeTrial> trials = (ArrayList<NonNegativeTrial>) document.get("trials");
//            ArrayList<NonNegativeTrial> trials = (ArrayList<NonNegativeTrial>)database.collection(collectionPath).document(document.getId()).collection("trials").get().getResult().toObjects(NonNegativeTrial.class);
            ArrayList<NonNegativeTrial> trials = new ArrayList<>();
            database.collection(collectionPath).document(document.getId()).collection("trials").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document :
                                task.getResult()) {
                            trials.add((NonNegativeTrial)document.getData().get("context"));
                        }
                    }
                }
            });
            experiment = new NonNegative(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials);
//            ArrayList<NonNegativeTrial> trials = document.toObject(((NonNegative)experiment).getClass()).getTrials();
//            ((NonNegative) experiment).setTrials(trials);
        }

        return experiment;
    }

    /**
     * Function to check whether an experiment matches a searched keyword
     * @param searchString
     *  Keyword provided during experiment search
     * @param experiment
     *  Experiment to check
     * @return
     *  True if keyword is present in the experiment's title or description, false otherwise
     */
    public Boolean experimentContainsKeyword(String searchString, Experiment experiment) {
        // Check if keyword is within experiment title or description
        if (experiment.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
            return true;
        }
        else if (experiment.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
            return true;
        }

        return false;
    }

    /**
     * Function to check whether an experiment is owned by a given experimenter
     * @param experimenter
     *  Experimenter to check against owner of given experiment
     * @param experiment
     *  Experiment to check
     * @return
     *  True if experimenter owns the given experimenter, false otherwise
     */
    public Boolean experimentIsOwned(Experimenter experimenter, Experiment experiment) {
        // Get owner and user IDs
        String ownerName = experiment.getOwnerID();
        String userName = experimenter.getUserProfile().getUsername();

        // Compare owner and user IDs
        if (ownerName.equals(userName)){
            return true;
        }

        return false;
    }

    /**
     * Function to check whether an experiment is subscribed to by a given experimenter
     * @param experimenter
     *  Experimenter to check against subscribers list of given experiment
     * @param experiment
     *  Experiment to check
     * @return
     *  True if experimenter is subscribed to the given experiment, false otherwise
     */
    public Boolean experimentIsSubscribed(Experimenter experimenter, Experiment experiment) {
        // Get user ID and list of experiment's subscribers
        String userName = experimenter.getUserProfile().getUsername();
        ArrayList<String> subscribedUsers = experiment.getSubscriberIDs();

        // Check whether user ID is within subscribed users collection
        if (subscribedUsers.contains(userName)) {
            return true;
        }

        return false;
    }

    /**
     * Function for adding an experiment to the database
     * @param experiment
     *  Experiment to add to the database
     */
    public void publishExperiment(Experiment experiment) {
        // Generate unique experiment ID and create experiment
        String experimentID = experiment.getExperimentID();

        // Add the experiment's owner as a subscriber
        experiment.addSubscriber(experiment.getOwnerID());

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("owner", ownerProfile.getUsername());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("geolocation", experiment.isGeoLocationEnabled());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("questions",experiment.getQuestions());
        experimentData.put("type", experiment.getClass().toString());
        experimentData.put("datePublished", experiment.getDatePublished());
        experimentData.put("min trial count", experiment.getMinTrials());
        experimentData.put("published", experiment.isPublished());

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

        // Add experiment to database
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Sets the experiment's status to 'closed' in both firestore and for the object itself
     * @param experiment
     *  Experiment to end in the database
     */
    public void endExperiment(Experiment experiment) {
        // Change experiment status
        experiment.setStatus("closed");

        // Add experiment data to HasMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("status", experiment.getStatus());

        // Update experiment in database
        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);
    }

    /**
     * Method to update the subscribers of an experiment by adding another subscriber
     * @param experiment
     *     The experiment with the added subscriber
     * @param subscriber
     *     The id of the subscriber being added to the experiment
     */
    public void addSubscriber(Experiment experiment, String subscriber) {

        experiment.addSubscriber(subscriber);

        HashMap<String, Object> experimentData = new HashMap<>();

        experimentData.put("subscribers", experiment.getSubscriberIDs());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);

    }

    /**
     * Function for changing whether an expeirment is published or not
     * @param experiment
     *     The experiment that is having their 'published' status changed'
     * @param published
     *     A boolean representing whether the experiment is published or not
     */
    public void updatePublishExperiment(Experiment experiment, boolean published) {

        experiment.setPublished(published);

        // Add experiment data to HasMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("published", experiment.isPublished());

        // Update experiment in database
        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);
    }



    /**
     * Function for deleting an experiment from the database
     * @param experiment
     *  Experiment to unpublish from database
     */
    public void deleteExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();

        // Remove experiment from database
        removeDataFromCollection(collectionPath, experimentID);
    }
}
