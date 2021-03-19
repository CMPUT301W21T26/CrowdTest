package com.example.crowdtest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Comment class
 */
public class Comment {

    // Comment attributes
    private String commentID;
    private Experimenter experimenter;
    private String content;
    private String timeStamp;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Comment constructor
     * @param commentID
     *  Unique ID of comment
     * @param experimenter
     *  Experimenter who created the comment
     * @param content
     *  Comment's content
     */
    public Comment(String commentID, Experimenter experimenter, String content) {
        this.commentID = commentID;
        this.experimenter = experimenter;
        this.content = content;
        timeStamp = dateFormat.format(new Date());
    }

    /**
     * Function for getting the ID of the comment
     * @return
     *  Unique ID of comment
     */
    public String getCommentID() {
        return commentID;
    }

    /**
     * Function for setting the ID of the comment
     * @param commentID
     *  Unique ID of comment
     */
    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    /**
     * Function for getting the experimenter of the comment
     * @return
     *  Experimenter who created the comment
     */
    public Experimenter getExperimenter() {
        return experimenter;
    }

    /**
     * Function for setting the experimenter of the comment
     * @param experimenter
     *  Experimenter who created the comment
     */
    public void setExperimenter(Experimenter experimenter) {
        this.experimenter = experimenter;
    }

    /**
     * Function for getting the content of the comment
     * @return
     *  Content of comment
     */
    public String getContent() {
        return content;
    }

    /**
     * Function for setting the content of the comment
     * @param content
     *  Content of comment
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Function for getting the timestamp of the comment
     * @return
     *  Timestamp of comment
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Function for setting the timestamp of the comment
     * @param timeStamp
     *  Timestamp of comment
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
