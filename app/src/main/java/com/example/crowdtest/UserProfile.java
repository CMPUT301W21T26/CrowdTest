package com.example.crowdtest;

/**
 *
 */
public class UserProfile {

    // UserProfile attributes
    private String username;
    private String email;
    private String phoneNumber;

    /**
     * UserProfile constructor
     */
    public UserProfile() {
        // TODO: Generate unique username
        email = "";
        phoneNumber = "";
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
