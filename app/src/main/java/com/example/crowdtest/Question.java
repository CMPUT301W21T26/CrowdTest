package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Question class
 */
public class Question extends Comment {

    // Question attributes
    private ArrayList<Reply> replies;

    /**
     * Question constructor
     *
     * @param questionID  Unique ID of reply
     * @param commenterID Unique ID of experimenter who created the question
     * @param content     Content of question
     */
    public Question(String questionID, String commenterID, String content) {
        super(questionID, commenterID, content);
        replies = new ArrayList<>();
    }

    /**
     * Constructor for retrieving data from the database
     *
     * @param commenter Experimenter who created the comment
     * @param commentID Unique ID of comment
     * @param content   Comment's content
     * @param timestamp Time posted
     */
    public Question(String commenter, String commentID, String content, String timestamp) {
        super(commenter, commentID, content, timestamp);
        replies = new ArrayList<>();
    }

    /**
     * Function for getting the replies to a question
     *
     * @return Replies of question
     */
    public ArrayList<Reply> getReplies() {
        return replies;
    }

    /**
     * Function for setting the replies to a question
     *
     * @param replyIDs Replies of question
     */
    public void setReplyIDs(ArrayList<Comment> replyIDs) {
        this.replies = replies;
    }

    /**
     * Function for adding a reply to the question
     *
     * @param reply Reply of question
     */
    public void addReply(Reply reply) {
        replies.add(reply);
    }
}