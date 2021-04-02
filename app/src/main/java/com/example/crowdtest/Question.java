package com.example.crowdtest;

import java.util.ArrayList;

/**
 * Question class
 */
public class Question extends Comment {

    // Question attributes
    private ArrayList<String> replyIDs;

    /**
     * Question constructor
     * @param questionID
     *  Unique ID of reply
     * @param commenterID
     *  Unique ID of experimenter who created the question
     * @param content
     *  Content of question
     */
    public Question(String questionID, String commenterID, String content) {
        super(questionID, commenterID, content);
        replyIDs = new ArrayList<>();
    }

    /**
     * Function for getting the replies to a question
     * @return
     *  Replies of question
     */
    public ArrayList<String> getReplyIDs() {
        return replyIDs;
    }

    /**
     * Function for setting the replies to a question
     * @param replyIDs
     *  Replies of question
     */
    public void setReplyIDs(ArrayList<String> replyIDs) {
        this.replyIDs = replyIDs;
    }

    /**
     * Function for adding a reply to the question
     * @param replyID
     *  Reply of question
     */
    public void addReply(String replyID) {
        replyIDs.add(replyID);
    }

    /**
     * Function for removing a reply from the question
     * @param replyID
     *  Reply of question
     */
    public void deleteReply(String replyID) {
        replyIDs.remove(replyID);
    }
}
