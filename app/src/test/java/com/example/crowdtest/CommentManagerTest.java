package com.example.crowdtest;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class CommentManagerTest {

    FirebaseFirestore mockDatabase = Mockito.mock(FirebaseFirestore.class);

    CommentManager mockCommentManager = new CommentManager(mockDatabase);

    Question mockQuestion = new Question("mockQuestionID","mockCommentID", "mockQuestionContent");

    /**
     * Test to see if question is posted
     */

    @Test
    public void testPostQuestion() {
        String content = "test content";
        String commenterID = "commentID";
        Question question = mockCommentManager.postQuestion(commenterID, content);

        assertEquals(question.getCommenterID(), commenterID);
        assertEquals(question.getContent(), content);
    }

    /**
     * Test to see if reply is posted
     */
    @Test
    public void testPostReply() {
        String content = "test reply";
        String commenterID = "commentID";
        Reply reply = mockCommentManager.postReply(commenterID, mockQuestion, content);

        assertEquals(reply.getCommenterID(), commenterID);
        assertEquals(reply.getContent(), content);
    }

}