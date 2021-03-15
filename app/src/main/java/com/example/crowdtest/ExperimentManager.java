package com.example.crowdtest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays; //TODO: remove this
import java.util.HashMap;

/**
 *
 */
public class ExperimentManager extends DatabaseManager {

    // ExperimentManager attributes
    private ArrayList<Experiment> experiments = new ArrayList<Experiment>(
            Arrays.asList(
                    new Binomial(new Experimenter(new UserProfile("User1", "ID1")), "1"),
                    new Count(new Experimenter(new UserProfile("User2", "ID2")), "2"),
                    new Measurement(new Experimenter(new UserProfile("User2", "ID3")), "3"))
    ); // TODO: remove this

    final private String collectionPath = "Experiments";
    private String TAG = "GetExperiments";
    private ExperimenterManager experimenterManager;

    /**
     * ExperimentManager constructor
     */
    public ExperimentManager() {
        super();
        experiments = new ArrayList<>();
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
        String region = (String) document.getData().get("region");
        String status = (String) document.getData().get("status");
        String title = (String) document.getData().get("title");
        String description = (String) document.getData().get("description");
        String type = (String) document.getData().get("type");

        //TODO: make it so that each experiment created uses actual user (ie add code for actual user here)
        if (type == "binomial") {

            //call ExperimentManager.getExperimenter(ownerID)
            experiment = new Binomial(new Experimenter(new UserProfile("usernameBinom123", "BinomInstID123")), experimentID);
        }
        else if (type == "count") {

            experiment = new Count(new Experimenter(new UserProfile("Countusername123", "CountInstID123")), experimentID);

        }
        else if (type =="measurement") {

            experiment = new Measurement(new Experimenter(new UserProfile("Measusername123", "MeasInstID123")), experimentID);

        }

        else {

            experiment = new NonNegative(new Experimenter(new UserProfile("NonNegusername123", "NonNegInstID123")), experimentID);

        }

        experiment.setStatus(status);
        experiment.setTitle(title);
        experiment.setDescription(description);

        return experiment;
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
        return experiments;
    }

    /**
     * Get owned experiments for the signed in user
     * @param owner
     *     The user whose owned experiments are being obtained
     * @return
     *     ArrayList of experiments owned  by given user
     */
    public Boolean experimentIsOwned(Experimenter owner, Experimenter user) {

        String ownerName = owner.getUserProfile().getUsername();

        String userName = user.getUserProfile().getUsername();

        if (ownerName == userName){
            return true;
        }

        return false;

    }

    /**
     *
     * @param subscriber
     *    The currently signed in user
     * @return
     *     An array list of all the experiments that user is subscribed to
     */
    public ArrayList<Experiment> getSubscribedExperiments(Experimenter subscriber, ArrayList<Experiment> allExperiments) {

        return experiments;
    }

    /**
     * Function for adding an experiment to the database
     */
    public void publishExperiment(Experimenter owner) {
        // Generate unique experiment ID and create experiment
        String experimentID = generateExperimentID();
        Experiment experiment = new Binomial(owner, experimentID);
        experiments.add(experiment);

        // Retrieve experiment owner's profile
        UserProfile ownerProfile = owner.getUserProfile();

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("owner", ownerProfile.getUsername());
        experimentData.put("status", experiment.getStatus());
        experimentData.put("title", experiment.getTitle());
        experimentData.put("description", experiment.getDescription());
        experimentData.put("region", experiment.getRegion());
        experimentData.put("subscribers", experiment.getSubscribers());
        experimentData.put("trials", experiment.getTrials());


        // Add experiment to database
        // TODO: add questions as a sub-collection
        addDataToCollection(collectionPath, experimentID, experimentData);
    }

    /**
     * Function for updating a given experiment in the database
     * @param experiment
     */
    public void updateExperiment(Experiment experiment) {
        // Retrieve experiment owner's profile
        Experimenter owner = experiment.getOwner();
        UserProfile ownerProfile = owner.getUserProfile();

        // Add experiment data to HashMap
        HashMap<String, Object> experimentData = new HashMap<>();
        experimentData.put("owner", ownerProfile.getUsername());
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
