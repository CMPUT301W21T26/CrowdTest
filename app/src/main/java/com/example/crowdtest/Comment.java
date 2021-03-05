package com.example.crowdtest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Comment {

    // Comment attributes
    private String commentID;
    private Experimenter experimenter;
    private String content;
    private String timeStamp;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * @param experimenter
     * @param content
     */
    public Comment(Experimenter experimenter, String content) {
        this.experimenter = experimenter;
        this.content = content;
        timeStamp = dateFormat.format(new Date());
    }

    /**
     *
     * @return
     */
    public Experimenter getExperimenter() {
        return experimenter;
    }

    /**
     *
     * @param experimenter
     */
    public void setExperimenter(Experimenter experimenter) {
        this.experimenter = experimenter;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
