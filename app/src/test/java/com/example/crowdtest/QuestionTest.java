package com.example.crowdtest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QuestionTest class for unit testing Question class and its children
 */
public class QuestionTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Function to test getter and setter methods for common question attributes
     */
    @Test
    void testAttributes() {
        // Create a mock question
        Question question = mockClassCreator.mockQuestion();

        // Set question attributes
        question.setReplyIDs(new ArrayList());

        // Check that defined attributes have been successfully set
        assertEquals(0, question.getReplyIDs().size());

    }

}