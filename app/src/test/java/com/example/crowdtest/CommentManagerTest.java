package com.example.crowdtest;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.ui.Comment;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentManagerTest {

    FirebaseFirestore mockDatabase = Mockito.mock(FirebaseFirestore.class);

    ExperimentManager mockExperimentManager = new ExperimentManager();

    Experimenter mockOwner = new Experimenter(new UserProfile("mockUserName1", "123", "mockemail@gmail.com", "1234567"));

    Experiment mockExperiment = new Binomial("mockUserName1", "mock123", "open", "Mock Experiment", "Mock Description", "Mock region", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true, new Date(), 10, new ArrayList<>(), true);

    Question mockQuestion = new Question("mockQuestionID","mockCommentID", "mockQuestionContent")

    Reply mockReply = new Reply("mockReplyID","mockParentID","mockCommenterID", "mockReplyContent")

    @Test
    public void testPostQuestion() {




    }

    @Test
    public void testPostReply() {



    }

    @Test
    public void testUpdateQuestion() {

        mockQuestion.

    }

    @Test
    public void testDeleteQuestion() {

        mockQuestion.

    }

    @Test
    public void testDeleteReply() {

        mockQuestion.

    }
}