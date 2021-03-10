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
     *
     * @return
     */
    public ArrayList<String> getAllExperimenterIDs() {
        return getAllDocuments(collectionPath);
    }

    /**
     * Function for adding an experimenter to the database
     */
    public Experimenter addExperimenter() {
        // Generate initial unique username and create an experimenter
        String username = generateUsername();
        Experimenter experimenter = new Experimenter(username);

        // Retrieve Experimenter data
        String status = experimenter.getStatus();
        ArrayList<String> subscribedExperiments = experimenter.getSubscribedExperiments();

        // Retrieve UserProfile data
        UserProfile userProfile = experimenter.getUserProfile();
        String email = userProfile.getEmail();
        String phoneNumber = userProfile.getPhoneNumber();

        // Add experimenter data to HashMap
        HashMap<String, Object> experimenterData = new HashMap<>();
        experimenterData.put("status", status);
        experimenterData.put("email", email);
        experimenterData.put("phoneNumber", phoneNumber);
        experimenterData.put("subscribed", subscribedExperiments);

        // Add experimenter data to database
        addDataToCollection(collectionPath, username, experimenterData);

        return  experimenter;
    }

    /**
     * Function for updating a given experimenter in the database
     * @param experimenter
     */
    public void updateExperimenter(Experimenter experimenter) {
        // Retrieve Experimenter data
        String status = experimenter.getStatus();
        ArrayList<String> subscribedExperiments = experimenter.getSubscribedExperiments();

        // Retrieve UserProfile data
        UserProfile userProfile = experimenter.getUserProfile();
        String username = userProfile.getUsername();
        String email = userProfile.getEmail();
        String phoneNumber = userProfile.getPhoneNumber();

        // Add experimenter data to HashMap
        HashMap<String, Object> experimenterData = new HashMap<>();
        experimenterData.put("status", status);
        experimenterData.put("email", email);
        experimenterData.put("phoneNumber", phoneNumber);
        experimenterData.put("subscribed", subscribedExperiments);

        // Add experimenter data to database
        addDataToCollection(collectionPath, username, experimenterData);
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
