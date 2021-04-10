package com.example.crowdtest;

/**
 * Reply class
 */
public class Reply extends Comment {

    /**
     * Reply constructor
     *
     * @param replyID   Unique ID of reply
     * @param replierID The experimenter who created the reply
     * @param content   Content of reply
     */
    public Reply(String replyID, String replierID, String content) {
        super(replyID, replierID, content);
    }

    /**
     * Constructor for retrieving data from the database
     *
     * @param commenterID Experimenter who created the comment
     * @param commentID   Unique ID of comment
     * @param content     Comment's content
     * @param timestamp   Time posted
     */
    public Reply(String commenterID, String commentID, String content, String timestamp) {
        super(commenterID, commentID, content, timestamp);
    }
}
