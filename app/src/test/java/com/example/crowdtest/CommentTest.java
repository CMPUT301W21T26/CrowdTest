package com.example.crowdtest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommentTest class for unit testing Comment class and its children
 */
public class CommentTest {

    private MockClassCreator mockClassCreator = new MockClassCreator();

    /**
     * Function to test getter and setter methods for common comment attributes
     */
    @Test
    void testAttributes() {
        // Create a mock user profile
        Comment comment = mockClassCreator.mockComment();

        // Set user profile attributes
        comment.setCommenterID("sample_commenterID");
        comment.setContent("sample_content");
        comment.setTimestamp("sample_timestamp");

        // Check that defined attributes have been successfully set
        assertEquals("sample_commenterID", comment.getCommenterID());
        assertEquals("sample_content", comment.getContent());
        assertEquals("sample_timestamp", comment.getTimestamp());
    }

    /**
     * Function to test adding a reply to a question
     */
    @Test
    void testAddReplyToQuestion() {
        Question question = mockClassCreator.mockQuestion();
        Reply reply = mockClassCreator.mockReply();

        // Add reply to question
        question.addReply(reply.getCommentID());

        // Check that reply has been successfully added
        assertEquals(1, question.getReplyIDs().size());
        assertEquals(reply.getCommentID(), question.getReplyIDs().get(0));
    }

    /**
     * Function to test deleting a reply from a question
     */
    @Test
    void testDeleteReplyFromQuestion() {
        Question question = mockClassCreator.mockQuestion();
        Reply reply = mockClassCreator.mockReply();

        // Add reply to question
        question.addReply(reply.getCommentID());

        // Delete reply from question
        question.deleteReply(reply.getCommentID());

        // Check that reply has been successfully deleted
        assertEquals(0, question.getReplyIDs().size());
    }
}
