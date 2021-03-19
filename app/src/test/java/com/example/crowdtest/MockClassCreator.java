package com.example.crowdtest;

import android.location.Location;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.example.crowdtest.experiments.Trial;

/**
 * MockClassCreator class for creating mock model class object instances for unit tests
 */
public class MockClassCreator {

    /**
     * MockClassCreator constructor
     */
    public MockClassCreator() { }

    /**
     * Function for creating a mock binomial experiment
     * @return
     *  Binomial experiment instance
     */
    protected Binomial mockBinomialExperiment() {
        String owner = "sample_owner";
        String experimentID = "sample_experimentID";
        Binomial binomialExperiment = new Binomial(owner, experimentID);
        return binomialExperiment;
    }

    /**
     * Function for creating a mock count experiment
     * @return
     *  Count experiment instance
     */
    protected Count mockCountExperiment() {
        String owner = "sample_owner";
        String experimentID = "sample_experimentID";
        Count countExperiment = new Count(owner, experimentID);
        return countExperiment;
    }

    /**
     * Function for creating a mock measurement experiment
     * @return
     *  Measurement experiment instance
     */
    protected Measurement mockMeasurementExperiment() {
        String owner = "sample_owner";
        String experimentID = "sample_experimentID";
        Measurement measurementExperiment = new Measurement(owner, experimentID);
        return measurementExperiment;
    }

    /**
     * Function for creating a mock non-negative experiment
     * @return
     *  Non-negative experiment instance
     */
    protected NonNegative mockNonNegativeExperiment() {
        String owner = "sample_owner";
        String experimentID = "sample_experimentID";
        NonNegative nonNegativeExperiment = new NonNegative(owner, experimentID);
        return nonNegativeExperiment;
    }

    /**
     * Function for creating a mock binomial trial
     * @param inputSuccess
     *  Binomial trial input
     * @return
     *  Binomial trial instance
     */
    protected BinomialTrial mockBinomialTrial(boolean inputSuccess) {
        BinomialTrial binomialTrial = new BinomialTrial(inputSuccess);
        return binomialTrial;
    }

    /**
     * Function for creating a mock count trial
     * @return
     *  Count trial instance
     */
    protected CountTrial mockCountTrial() {
        CountTrial countTrial = new CountTrial();
        return countTrial;
    }

    /**
     * Function for creating a mock measurement trial
     * @param inputMeasurement
     *  Measurement trial input
     * @return
     *  Measurement trial instance
     */
    protected MeasurementTrial mockMeasurementTrial(double inputMeasurement) {
        MeasurementTrial measurementTrial = new MeasurementTrial(inputMeasurement);
        return measurementTrial;
    }

    /**
     * Function for creating a mock non-negative trial
     * @param inputCount
     *  Non-negative trial input
     * @return
     *  Non-negative trial instance
     * @throws Exception
     *
     */
    protected NonNegativeTrial mockNonNegativeTrial(int inputCount) {
        NonNegativeTrial nonNegativeTrial = new NonNegativeTrial(inputCount);
        return nonNegativeTrial;
    }

    /**
     * Function for creating a mock UserProfile object instance
     * @return
     *  UserProfile object instance
     */
    protected UserProfile mockUserProfile() {
        String username = "sample_username";
        String installationID = "sample_installationID";
        UserProfile userProfile = new UserProfile(username, installationID);
        return userProfile;
    }

    /**
     * Function for creating a mock Experimenter object instance
     * @return
     *  Experimenter object instance
     */
    protected Experimenter mockExperimenter() {
        UserProfile userProfile = mockUserProfile();
        Experimenter experimenter = new Experimenter(userProfile);
        return experimenter;
    }

    /**
     * Function for creating a mock Owner object instance
     * @return
     *  Owner object instance
     */
    protected Owner mockOwner() {
        UserProfile userProfile = mockUserProfile();
        Owner owner = new Owner(userProfile);
        return owner;
    }

    /**
     * Second function for creating a mock Experimenter object instance
     * @param userProfile
     *  UserProfile object instance
     * @return
     *  Experimenter object instance
     */
    protected Experimenter mockExperimenter(UserProfile userProfile) {
        Experimenter experimenter = new Experimenter(userProfile);
        return experimenter;
    }

    /**
     * Function for creating a mock Comment object instance
     * @return
     *  Comment object instance
     */
    protected Comment mockComment() {
        String commentID = "sample_commentID";
        String content = "sample_comment_content";
        Experimenter experimenter = mockExperimenter();
        Comment comment = new Comment(commentID, experimenter, content);
        return comment;
    }

    /**
     * Function for creating a mock Question object instance
     * @return
     *  Question object instance
     */
    protected Question mockQuestion() {
        String questionID = "sample_questionID";
        String content = "sample_question_content";
        Experimenter experimenter = mockExperimenter();
        Question question = new Question(questionID, experimenter, content);
        return question;
    }

    /**
     * Function for creating a mock Reply object instance
     * @return
     *  Reply object instance
     */
    protected Reply mockReply() {
        String replyID = "sample_replyID";
        String parentID = "sample_parentID";
        String content = "sample_reply_content";
        Experimenter experimenter = mockExperimenter();
        Reply reply = new Reply(replyID, parentID, experimenter, content);
        return reply;
    }

    /**
     * Function for creating a mock Trial object instnace
     * @return
     *     Trial object instance
     */
    protected Trial mockTrial() {

        return new Trial();

    }

    /**
     * Function for creating a mock Trial object instance with geolocation value
     * @return
     *     Trial object instance
     */
    protected Trial mockGeolocationTrial(Location geolocation) {

        return new Trial(geolocation);

    }

}
