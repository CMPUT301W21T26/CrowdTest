package com.example.crowdtest;

import java.util.ArrayList;

/**
 * Question class
 */
public class Question extends Comment {

    // Question attributes
    private ArrayList<String> replies;

    /**
     * Question constructor
     * @param questionID
     *  Unique ID of reply
     * @param experimenter
     *  Experimenter who created the question
     * @param content
     *  Content of question
     */
    public Question(String questionID, Experimenter experimenter, String content) {
        super(questionID, experimenter, content);
        replies = new ArrayList<>();
    }

    /**
     * Function for getting the replies to a question
     * @return
     *  Replies of question
     */
    public ArrayList<String> getReplies() {
        return replies;
    }

    /**
     * Function for setting the replies to a question
     * @param replies
     *  Replies of question
     */
    public void setReplies(ArrayList<String> replies) {
        this.replies = replies;
    }

    /**
     * Function for adding a reply to the question
     * @param replyID
     *  Reply of question
     */
    public void addReply(String replyID) {
        replies.add(replyID);
    }

    /**
     * Function for removing a reply from the question
     * @param replyID
     *  Reply of question
     */
    public void deleteReply(String replyID) {
        replies.remove(replyID);
    }
}
