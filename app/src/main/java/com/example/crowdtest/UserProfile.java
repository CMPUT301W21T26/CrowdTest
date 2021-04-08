package com.example.crowdtest;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * UserProfile class that stores a user's contact information such as
 * their username, email, and phone number
 */
public class UserProfile implements Serializable {

    // UserProfile attributes
    private String username;
    private String email;
    private String phoneNumber;
    private String installationID;

    /**
     * UserProfile constructor
     * @param username
     *  Unique username of user
     * @param installationID
     *  Unique installation ID of user's app
     */
    public UserProfile(String username, String installationID) {
        this.username = username;
        this.installationID = installationID;
        this.email = "";
        this.phoneNumber = "";
    }

    /**
     * Secondary UserProfile constructor
     * @param username
     *  Unique username of user
     * @param installationID
     *  Unique installation ID of user's app
     * @param email
     *  Email of user
     * @param phoneNumber
     *  Phone number of user
     */
    public UserProfile(String username, String installationID, String email, String phoneNumber) {
        this.username = username;
        this.installationID = installationID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Function for getting a user's username
     * @return
     *  Unique username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Function for setting a user's username
     * @param username
     *  Unique username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Function for getting a user's email
     * @return
     *  Email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Function for setting a user's email
     * @param email
     *  Email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Function for getting a user's phone number
     * @return
     *  Phone number of user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Function for setting a user's phone number
     * @return
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Function for getting user's installation ID
     */
    public String getInstallationID() { return installationID; }

    /**
     * Function for setting user's installation ID
     */
    public void setInstallationID(String installationID) { this.installationID = installationID; }

    /**
     * Function for validating user email
     * @param email
     *  Email of user
     * @return
     *  True if valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        // Define regex pattern to compare email to
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        // Validate email
        if (email.isEmpty() || pattern.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Function for validating user phone number
     * @param phoneNumber
     *  Phone number of user
     * @return
     *  True if valid, false otherwise
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Define regex pattern to compare phone number to
        Pattern pattern = Pattern.compile("[0-9]+");

        // Validate email
        if (phoneNumber.isEmpty() || pattern.matcher(phoneNumber).matches()) {
            return true;
        } else {
            return false;
        }
    }
}
