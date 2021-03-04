package com.example.crowdtest;

/**
 *
 */
public class Experimenter {

    // Experimenter attributes
    private String status;
    private UserProfile userProfile;

    /**
     * Experimenter constructor
     */
    public Experimenter() {
        userProfile = new UserProfile();
        // TODO: add new experimenter instance and corresponding UserProfile to database
    }

    /**
     * Function for getting user's profile
     * @return
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Function for setting user's profile
     * @param userProfile
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Function for getting user's status
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function for setting user's status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
