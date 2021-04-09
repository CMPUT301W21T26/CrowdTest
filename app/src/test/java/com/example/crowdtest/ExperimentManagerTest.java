package com.example.crowdtest;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class ExperimentManagerTest {

    FirebaseFirestore mockDatabase = Mockito.mock(FirebaseFirestore.class);

    ExperimentManager mockExperimentManager = new ExperimentManager(mockDatabase);

    Experimenter mockOwner = new Experimenter(new UserProfile("mockUserName1", "123", "mockemail@gmail.com", "1234567"));

    Experimenter mockSubscriber = new Experimenter(new UserProfile("mockUserName2", "123", "mockemail@gmail.com", "1234567"));

    Experimenter mockOtherUser = new Experimenter(new UserProfile("mockUserName3", "123", "mockemail@gmail.com", "1234567"));

    Experiment mockExperiment = new Binomial("mockUserName1", "mock123", "open","Mock Experiment", "Mock Description", "Mock region", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),true, new Date(),10, new ArrayList<>(), true);

    @Test
    public void testExperimentContainsKeyword() {

        //test that if word is in title and description, returns true
        Boolean containsWord = mockExperimentManager.experimentContainsKeyword("Mock", mockExperiment);


        assertTrue(containsWord);

        //test that if word is in title, returns true

        containsWord = mockExperimentManager.experimentContainsKeyword("Title", mockExperiment);

        assertTrue(containsWord);

        //test that if word is in description, returns true

        containsWord = mockExperimentManager.experimentContainsKeyword("Description", mockExperiment);

        assertTrue(containsWord);

        //test that case of word does not matter

        containsWord = mockExperimentManager.experimentContainsKeyword("description", mockExperiment);

        assertTrue(containsWord);

        //test that a non-contained word returns false

        containsWord = mockExperimentManager.experimentContainsKeyword("NotContainedWord", mockExperiment);

        assertFalse(containsWord);

        //test that an empty string returns true

        containsWord = mockExperimentManager.experimentContainsKeyword("", mockExperiment);

        assertTrue(containsWord);
    }

    @Test
    public void testExperimentIsOwned(){

        Boolean owned = mockExperimentManager.experimentIsOwned(mockOwner, mockExperiment);

        assertTrue(owned);

        owned = mockExperimentManager.experimentIsOwned(mockOtherUser, mockExperiment);

        assertFalse(owned);

    }

    @Test
    public void testExperimentIsSubscribed(){

        //check that subscribed user returns true

        mockExperiment.addSubscriber(mockSubscriber.getUserProfile().getUsername());

        Boolean subscribed = mockExperimentManager.experimentIsSubscribed(mockSubscriber, mockExperiment);

        assertTrue(subscribed);

        //assert returns false after subscriber is removed

        mockExperiment.removeSubscriber(mockSubscriber.getUserProfile().getUsername());

        subscribed = mockExperimentManager.experimentIsSubscribed(mockSubscriber, mockExperiment);

        assertFalse(subscribed);

        //assert user that was never subscribed returns false

        subscribed = mockExperimentManager.experimentIsSubscribed(mockOtherUser, mockExperiment);

        assertFalse(subscribed);

    }
}
