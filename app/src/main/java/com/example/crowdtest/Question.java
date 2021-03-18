package com.example.crowdtest;

import java.util.ArrayList;

/**
 *
 */
public class Question extends Comment{

    // Question attributes
    private ArrayList<Reply> replies;

    /**
     *
     * @param experimenter
     * @param content
     */
    public Question(Experimenter experimenter, String content) {
        super(experimenter, content);
    }
}
