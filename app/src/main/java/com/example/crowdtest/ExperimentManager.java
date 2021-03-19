package com.example.crowdtest;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ExperimentManager class for interfacing with Firestore database to publish, unpublish, and
 * archive experiments
 */
public class ExperimentManager extends DatabaseManager {

    // ExperimentManager attributes
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
        experiment.setQuestionIDs((ArrayList<String>) document.getData().get("questions"));
        experiment.setSubscriberIDs((ArrayList<String>) document.getData().get("subscribers"));
        experiment.setGeolocationEnabled((Boolean) document.getData().get("geolocation"));

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
        experimentData.put("owner", experiment.getOwnerID());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("geolocation", experiment.getGeolocationEnabled());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscriberIDs());
        experimentData.put("questions",experiment.getQuestionIDs());
        experimentData.put("type", experiment.getType());
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

        // Add experiment to database
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Function for archiving an experiment in the database
     * @param experiment
     *  Experiment to archive in the database
     */
    public void endExperiment(Experiment experiment) {
        // Change experiment status
        experiment.setStatus("closed");

        // Add experiment data to HasMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("status", experimentData);

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
    public void unpublishExperiment(Experiment experiment) {
        // Retrieve experimentID
        String experimentID = experiment.getExperimentID();

        // Remove experiment from database
        removeDataFromCollection(collectionPath, experimentID);
    }
}
