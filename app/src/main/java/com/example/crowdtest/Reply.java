package com.example.crowdtest;

/**
 * Reply class
 */
public class Reply extends Comment {

    /**
     * Reply constructor
     * @param replyID
     *  Unique ID of reply
     * @param experimenter
     *  Experimenter who created the reply
     * @param content
     *  Content of reply
     */
    public Reply(String replyID, Experimenter experimenter, String content) {
        super(replyID, experimenter, content);
    }
}
