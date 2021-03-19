package com.example.crowdtest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Experimenter experimenter = mockClassCreator.mockExperimenter();

        // Set user profile attributes
        comment.setCommenter(experimenter);
        comment.setContent("sample_content");
        comment.setTimestamp("sample_timestamp");

        // Check that defined attributes have been successfully set
        assertEquals(experimenter.getUserProfile().getUsername(), comment.getCommenter().getUserProfile().getUsername());
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
        assertEquals(1, question.getReplies().size());
        assertEquals(reply.getCommentID(), question.getReplies().get(0));
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
        assertEquals(0, question.getReplies().size());
    }
}
