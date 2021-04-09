package com.example.crowdtest;

import android.location.Location;
import android.util.Log;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;

import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public ExperimentManager(FirebaseFirestore db) {

        super(db);
    }

    /**
     * Function for generating a unique experiment ID
     *
     * @return Unique experiment ID
     */
    public String generateExperimentID() {
        return generateDocumentID("experiment", collectionPath);
    }

    /**
     * Converts a document obtained from Firestore into an Experiment object
     *
     * @param document The firestore document to be converted
     * @return Returns the experiment object
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
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<String> subscribers = (ArrayList<String>) document.getData().get("subscribers");
        ArrayList<String> blackListedUsers = (ArrayList<String>) document.getData().get("blacklisted");
        boolean isPublished = (boolean) document.getData().get("published");

        //TODO: make it so that each experiment created uses actual user (ie add code for actual user here)
        if (type.equals("Binomial")) {
            experiment = new Binomial(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, 0, new ArrayList<>(), isPublished);
        } else if (type.equals("Count")) {
            experiment = new Count(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, 0, new ArrayList<>(), isPublished);
        } else if (type.equals("Measurement")) {
            experiment = new Measurement(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, 0, new ArrayList<>(), isPublished);
        } else {
            experiment = new NonNegative(owner, experimentID, status, title, description, region, subscribers, blackListedUsers, questions, geoLocation, datePublished, 0, new ArrayList<>(), isPublished);
        }
        return experiment;
    }

    public void getTrials(String experimentID, String experimentType, TrialRetriever trialRetriever) {
        Query query = database.collection(collectionPath).whereEqualTo("installationID", experimentID);

        //run query to see if the installation id is already in the database
        final Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection(collectionPath).document(experimentID).collection("trials").get();
        task.addOnCompleteListener(task1 -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    if (experimentType.equals("Binomial")) {
                        Location location = (Location) documentSnapshot.getData().get("location");
                        Date timeStamp = ((Timestamp) documentSnapshot.getData().get("timestamp")).toDate();
                        boolean success = (boolean) documentSnapshot.getData().get("success");
                        trialRetriever.getBinomialTrials(new BinomialTrial(timeStamp, location, success));
                    } else if (experimentType.equals("Count")) {
                        Location location = (Location) documentSnapshot.getData().get("location");
                        Date timeStamp = ((Timestamp) documentSnapshot.getData().get("timestamp")).toDate();
                        trialRetriever.getCountTrials(new CountTrial(timeStamp, location));
                    } else if (experimentType.equals("Measurement")) {
                        Location location = (Location) documentSnapshot.getData().get("location");
                        Date timeStamp = ((Timestamp) documentSnapshot.getData().get("timestamp")).toDate();
                        double measurement = (double) documentSnapshot.getData().get("measurement");
                        trialRetriever.getMeasurementTrials(new MeasurementTrial(timeStamp, location, measurement));
                    } else {
                        Location location = (Location) documentSnapshot.getData().get("location");
                        Date timeStamp = ((Timestamp) documentSnapshot.getData().get("timestamp")).toDate();
                        long count = (long) documentSnapshot.getData().get("count");
                        trialRetriever.getNonNegativeTrials(new NonNegativeTrial(timeStamp, location, count));
                    }
                }

//                experiment = new NonNegative(owner, experimentID, status, title, description, region, subscribers, questions, geoLocation, datePublished, 0, trials, isPublished);
            } else {

                //if firestore query is unsuccessful, log an error and return
                Log.d(TAG, "Error getting documents: ", task.getException());

                return;

            }
        });
    }

    /**
     * Function to check whether an experiment matches a searched keyword
     *
     * @param searchString Keyword provided during experiment search
     * @param experiment   Experiment to check
     * @return True if keyword is present in the experiment's title or description, false otherwise
     */
    public Boolean experimentContainsKeyword(String searchString, Experiment experiment) {
        // Check if keyword is within experiment title or description
        if (experiment.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
            return true;
        } else if (experiment.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
            return true;
        }

        return false;
    }

    /**
     * Function to check whether an experiment is owned by a given experimenter
     *
     * @param experimenter Experimenter to check against owner of given experiment
     * @param experiment   Experiment to check
     * @return True if experimenter owns the given experimenter, false otherwise
     */
    public Boolean experimentIsOwned(Experimenter experimenter, Experiment experiment) {
        // Get owner and user IDs
        String ownerName = experiment.getOwner();
        String userName = experimenter.getUserProfile().getUsername();

        // Compare owner and user IDs
        if (ownerName.equals(userName)) {
            return true;
        }

        return false;
    }

    /**
     * Function to check whether an experiment is subscribed to by a given experimenter
     *
     * @param experimenter Experimenter to check against subscribers list of given experiment
     * @param experiment   Experiment to check
     * @return True if experimenter is subscribed to the given experiment, false otherwise
     */
    public Boolean experimentIsSubscribed(Experimenter experimenter, Experiment experiment) {
        // Get user ID and list of experiment's subscribers
        String userName = experimenter.getUserProfile().getUsername();
        ArrayList<String> subscribedUsers = experiment.getSubscribers();

        // Check whether user ID is within subscribed users collection
        if (subscribedUsers.contains(userName)) {
            return true;
        }

        return false;
    }

    /**
     * Function for adding an experiment to the database
     *
     * @param experiment Experiment to add to the database
     */
    public void publishExperiment(Experiment experiment) {
        // Generate unique experiment ID and create experiment
        String experimentID = experiment.getExperimentID();

        // Add the experiment's owner as a subscriber
        experiment.addSubscriber(experiment.getOwner());

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("owner", experiment.getOwner());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("geolocation", experiment.isGeolocationEnabled());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("blacklisted", experiment.getBlackListedUsers());
        experimentData.put("questions", experiment.getQuestions());
        experimentData.put("type", experiment.getClass().getSimpleName());
        experimentData.put("datePublished", experiment.getDatePublished());
        experimentData.put("min trial count", experiment.getMinTrials());
        experimentData.put("published", experiment.isPublished());

        if (experiment instanceof Measurement) {
            experimentData.put("trials", ((Measurement) experiment).getTrials());
        } else if (experiment instanceof NonNegative) {
            experimentData.put("trials", ((NonNegative) experiment).getTrials());
        } else if (experiment instanceof Count) {
            experimentData.put("trials", ((Count) experiment).getTrials());
        } else if (experiment instanceof Binomial) {
            experimentData.put("trials", ((Binomial) experiment).getTrials());
        }

        // Add experiment to database
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Sets the experiment's status to 'closed' in both firestore and for the object itself
     *
     * @param experiment Experiment to end in the database
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
     *
     * @param experiment The experiment with the added subscriber
     * @param subscriber The id of the subscriber being added to the experiment
     */
    public void addSubscriber(Experiment experiment, String subscriber) {

        experiment.addSubscriber(subscriber);

        HashMap<String, Object> experimentData = new HashMap<>();

        experimentData.put("subscribers", experiment.getSubscribers());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);

    }

    /**
     * Method to update the subscribers of an experiment by removing a subscriber
     *
     * @param experiment The experiment with the added subscriber
     * @param subscriber The id of the subscriber being added to the experiment
     */
    public void removeSubscriber(Experiment experiment, String subscriber) {

        experiment.removeSubscriber(subscriber);

        HashMap<String, Object> experimentData = new HashMap<>();

        experimentData.put("subscribers", experiment.getSubscribers());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);
    }

    /**
     *
     * @param experiment
     * @param username
     */
    public void addBlackListedUser(Experiment experiment, String username) {
        experiment.addBlackListedUser(username);

        HashMap<String, Object> experimentData = new HashMap<>();

        experimentData.put("blacklisted", experiment.getSubscribers());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);
    }

    /**
     *
     * @param experiment
     * @param username
     */
    public void removeBlackListedUser(Experiment experiment, String username) {
        experiment.removeBlackListedUser(username);

        HashMap<String, Object> experimentData = new HashMap<>();

        experimentData.put("blacklisted", experiment.getSubscribers());

        database.collection(collectionPath)
                .document(experiment.getExperimentID())
                .update(experimentData);
    }

    /**
     * Function for changing whether an expeirment is published or not
     *
     * @param experiment The experiment that is having their 'published' status changed'
     * @param published  A boolean representing whether the experiment is published or not
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
     *
     * @param experiment Experiment to unpublish from database
     */
    public void deleteExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();

        // Remove experiment from database
        removeDataFromCollection(collectionPath, experimentID);
    }
}
