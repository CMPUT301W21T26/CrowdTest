package com.example.crowdtest;

/**
 *
 */
public class UserProfile {

    // UserProfile attributes
    private String username;
    private String email;
    private String phoneNumber;
    private String installationID;

    /**
     * UserProfile constructor
     */
    public UserProfile(String username, String installationID) {
        this.username = username;
        this.installationID = installationID;
        this.email = "";
        this.phoneNumber = "";
    }

    /**
     * second user profile constructor
     * @param username
     * @param installationID
     */
    public UserProfile(String username, String installationID, String email, String phoneNumber) {
        this.username = username;
        this.installationID = installationID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Function for getting user's username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Function for setting user's username
     * @return
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Function for getting user's email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Function for setting user's email
     * @return
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Function for getting user's phone number
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Function for setting user's phone number
     * @return
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
