package com.example.crowdtest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserProfileTest class for unit testing UserProfile class
 */
public class UserProfileTest extends MockClassCreator {

    /**
     * Function to test getter and setter methods for common user profile attributes
     */
    @Test
    void testAttributes() {
        // Create a mock user profile
        UserProfile userProfile = mockUserProfile();

        // Set user profile attributes
        userProfile.setUsername("sample_user");
        userProfile.setEmail("sample@email.com");
        userProfile.setPhoneNumber("sample_number");

        // Check that defined attributes have been successfully set
        assertEquals("sample_user", userProfile.getUsername());
        assertEquals("sample@email.com", userProfile.getEmail());
        assertEquals("sample_number", userProfile.getPhoneNumber());
    }
}
