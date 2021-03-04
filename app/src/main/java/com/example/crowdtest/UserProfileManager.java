package com.example.crowdtest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 */
public class UserProfileManager extends DatabaseManager {

    // UserProfileManager attributes
    final private String collectionPath = "Users";

    /**
     * UserProfileManager constructor
     */
    public UserProfileManager() {
        super();
    }

    /**
     * Function for adding a user profile to the database
     * @param userProfile
     */
    public void addUserProfile(UserProfile userProfile) {
        // Retrieve username
        String username = userProfile.getUsername();

        // Collect user data
        ArrayList<String> userDataList = new ArrayList();
        userDataList.add(userProfile.getEmail());
        userDataList.add(userProfile.getPhoneNumber());

        // Add user data to HashMap
        HashMap<String, Collection> userData = new HashMap<>();
        userData.put(username, userDataList);

        // Add user profile to database
        addDataToCollection(collectionPath, username, userData);
    }

    /**
     * Function for deleting a user profile from the database
     * @param userProfile
     */
    public void delUserProfile(UserProfile userProfile) {
        // Retrieve username
        String username = userProfile.getUsername();

        // Remove user profile from database
        removeDataFromCollection(collectionPath, username);
    }

}
