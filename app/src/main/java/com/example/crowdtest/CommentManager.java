package com.example.crowdtest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class CommentManager extends DatabaseManager {

    // CommentManager attributes
    final private String questionCollectionPath = "Questions";
    final private String replyCollectionPath = "Replies";
    String TAG = "GetCommentData";

    /**
     * CommentManager constructor
     */
    public CommentManager() {
        super();
    }

    /**
     * Function for generating a unique question ID
     * @return
     *  Unique question ID
     */
    public String generateQuestionID() {
        return generateDocumentID("question", questionCollectionPath);
    }

    /**
     * Function for generating a unique reply ID
     * @return
     *  Unique reply ID
     */
    public String generateReplyID() {
        return generateDocumentID("reply", replyCollectionPath);
    }

    /**
     * Function that retrieves all question IDs from the database
     * @return
     *  ArrayList of all reply IDs stored in database
     */
    public ArrayList<String> getAllQuestionIDs() {
        return getAllDocuments(questionCollectionPath);
    }

    /**
     * Function that retrieves all reply IDs from the database
     * @return
     *  ArrayList of all reply IDs stored in database
     */
    public ArrayList<String> getAllReplyIDs() {
        return getAllDocuments(replyCollectionPath);
    }

    /**
     * Function for adding a question to the database
     * @param experimenter
     *  Experimenter who created the question
     * @param content
     *  Content of question
     * @return
     *  Instance of question object added to database
     */
    private Question addNewQuestion(Experimenter experimenter, String content) {
        // Generate unique commentID and create a comment
        String questionID = generateQuestionID();
        Question question = new Question(questionID, experimenter, content);

        // Retrieve Experimenter data
        String timestamp = question.getTimeStamp();
        ArrayList<String> replies = question.getReplies();

        // Retrieve UserProfile data
        UserProfile userProfile = experimenter.getUserProfile();

        // Add experimenter data to HashMap
        HashMap<String, Object> questionData = new HashMap<>();
        questionData.put("poster", userProfile.getUsername());
        questionData.put("content", content);
        questionData.put("timestamp", timestamp);
        questionData.put("replies", replies);

        // Add experimenter data to database
        addDataToCollection(questionCollectionPath, questionID, questionData);

        return question;
    }

    /**
     * Function for adding a reply to the database
     * @param question
     *  Parent question
     * @param experimenter
     *  Experimenter who created the reply
     * @param content
     *  Content of reply
     * @return
     *  Instance of Reply object added to database
     */
    private Reply addNewReply(Experimenter experimenter, Question question, String content) {
        // Generate unique commentID and create a comment
        String replyID = generateReplyID();
        String questionID = question.getCommentID();
        Reply reply = new Reply(replyID, experimenter, content);

        // Retrieve Experimenter data
        String timestamp = reply.getTimeStamp();

        // Retrieve UserProfile data
        UserProfile userProfile = experimenter.getUserProfile();

        // Add experimenter data to HashMap
        HashMap<String, Object> replyData = new HashMap<>();
        replyData.put("poster", userProfile.getUsername());
        replyData.put("content", content);
        replyData.put("timestamp", timestamp);
        replyData.put("parent", questionID);

        // Add experimenter data to database
        addDataToCollection(replyCollectionPath, replyID, replyData);

        return reply;
    }

    /**
     * Function for deleting a question from the database
     * @param question
     *  Instance of Question object to delete from database
     */
    public void deleteQuestion(Question question) {
        // Retrieve question ID and remove question from database
        String questionID = question.getCommentID();
        removeDataFromCollection(questionCollectionPath, questionID);

        // Retrieve reply ID's and remove all child replies from database
        ArrayList<String> replyIDs = question.getReplies();
        for (String replyID : replyIDs) {
            removeDataFromCollection(replyCollectionPath, replyID);
        }
    }

    /**
     * Function for deleting a comment from the database
     * @param reply
     *  Instance of Reply object to delete from database
     */
    public void deleteReply(Reply reply) {
        // Retrieve reply ID and remove reply from database
        String replyID = reply.getCommentID();
        removeDataFromCollection(replyCollectionPath, replyID);
    }
}
