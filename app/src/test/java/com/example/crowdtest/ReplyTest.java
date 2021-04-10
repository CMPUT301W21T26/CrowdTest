package com.example.crowdtest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ReplyTest class for unit testing Reply class and its children
 */

public class ReplyTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Function to test getter and setter methods for common reply attributes
     */
    @Test
    void testAttributes() {
        // Create a mock reply
        Reply reply = mockClassCreator.mockReply();

        // Set reply attributes
        reply.setParentID("sample_ParentID");

        // Check that defined attributes have been successfully set
        assertEquals("sample_ParentID", reply.getParentID());

    }

}