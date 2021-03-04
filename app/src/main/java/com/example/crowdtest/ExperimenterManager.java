package com.example.crowdtest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class ExperimenterManager extends DatabaseManager {

    // ExperimenterManager attributes
    final private String collectionPath = "Users";

    /**
     * ExperimenterManager constructor
     */
    public ExperimenterManager() {
        super();
    }

    /**
     * Function for generating a unique username
     * @return
     */
    public String generateUsername() {
        return generateDocumentID("user", collectionPath);
    }

    /**
     * Function for adding a experimenter to the database
     * @param experimenter
     */
    public void addExperimenter(Experimenter experimenter) {
        // Retrieve Experimenter data
        String status = experimenter.getStatus();
        ArrayList<String> subscribedExperiments = experimenter.getSubscribedExperiments();

        // Retrieve UserProfile data
        UserProfile userProfile = experimenter.getUserProfile();
        String username = userProfile.getUsername();
        String email = userProfile.getEmail();
        String phoneNumber = userProfile.getPhoneNumber();

        // Add experimenter data to HashMap
        HashMap<String, String> experimenterData = new HashMap<>();
        experimenterData.put("status", status);
        experimenterData.put("email", email);
        experimenterData.put("phoneNumber", phoneNumber);

        // Add experimenter data to database
        addDataToCollection(collectionPath, username, experimenterData);

        // Add experimenter's subscribed data to database
        String subscribedExperimentsPath = collectionPath + "/" + username + "/Subscribed";
        for (String experimentID : subscribedExperiments) {
            addDataToCollection(subscribedExperimentsPath, experimentID);
        }
    }

    /**
     * Function for deleting a experimenter from the database
     * @param experimenter
     */
    public void delExperimenter(Experimenter experimenter) {
        // Retrieve username
        UserProfile userProfile = experimenter.getUserProfile();
        String username = userProfile.getUsername();

        // Remove experimenter profile from database
        removeDataFromCollection(collectionPath, username);
    }

}
