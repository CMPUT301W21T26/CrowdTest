package com.example.crowdtest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to contain methods which manager the accessing and updating of Comment objects through firestore
 */
public class CommentManager extends DatabaseManager {
    private static CommentManager singleton_comment_manager = null;

    // CommentManager attributes
    final private String QUESTION_COLLECTION_PATH = "Questions";
    String TAG = "GetCommentData";

    /**
     * CommentManager constructor
     */
    private CommentManager() {
        super();
    }

    public static CommentManager getInstance() {
        if (singleton_comment_manager == null) singleton_comment_manager = new CommentManager();
        return singleton_comment_manager;
    }

    /**
     * Function that retrieves all questions from the database
     */
    public void getQuestions(String experimentID, CommentRetriever commentRetriever) {
        Query query = database.collection(QUESTION_COLLECTION_PATH).whereEqualTo("experiment id", experimentID);
        final Task<QuerySnapshot> task = query.get();
        task.addOnCompleteListener(task1 -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String userID = (String) documentSnapshot.getData().get("poster");
                    Experimenter commenter = ExperimenterManager.getUser(userID);
                    String questionID = documentSnapshot.getId();
                    String content = (String) documentSnapshot.getData().get("content");
                    String timestamp = (String) documentSnapshot.getData().get("timestamp");
                    Question question = new Question(commenter.getUserProfile().getUsername(), questionID, content, timestamp);
                    commentRetriever.getComments(question);
                }
            } else {

                //if firestore query is unsuccessful, log an error and return
                Log.d(TAG, "Error getting documents: ", task.getException());

                return;

            }
        });
    }

    /**
     * Function that retrieves a question from the database
     */
    public Question getQuestion(QueryDocumentSnapshot document) {
        String userID = (String) document.getData().get("poster");
        String questionID = document.getId();
        String content = (String) document.getData().get("content");
        String timestamp = (String) document.getData().get("timestamp");
        Question question = new Question(userID, questionID, content, timestamp);
        return question;
    }


//    public Question loadQuestion(QueryDocumentSnapshot documentSnapshot) {
//        String userID = (String) documentSnapshot.getData().get("poster");
//        Experimenter commenter = ExperimenterManager.getUser(userID);
//        String questionID = documentSnapshot.getId();
//        String content = (String) documentSnapshot.getData().get("content");
//        String timestamp = (String) documentSnapshot.getData().get("timestamp");
//        Question question = new Question(commenter.getUserProfile().getUsername(), questionID, content, timestamp);
//        return question;
//    }

    /**
     * Function that retrieves all the replies to the given question from the database
     */
    public Reply getReply(QueryDocumentSnapshot documentSnapshot) {
        String userID = (String) documentSnapshot.getData().get("poster");
        String replyID = documentSnapshot.getId();
        String content = (String) documentSnapshot.getData().get("content");
        String timestamp = (String) documentSnapshot.getData().get("timestamp");
        Reply reply = new Reply(userID, replyID, content, timestamp);
        return reply;
    }

//    public void getReply(String questionID, CommentRetriever commentRetriever) {
//        Query query = database.collection(QUESTION_COLLECTION_PATH).document(questionID).collection("replies");
//        final Task<QuerySnapshot> task = query.get();
//        task.addOnCompleteListener(task1 -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                    String userID = (String) documentSnapshot.getData().get("poster");
//                    Experimenter commenter = ExperimenterManager.getUser(userID);
//                    String replyID = documentSnapshot.getId();
//                    String content = (String) documentSnapshot.getData().get("content");
//                    String timestamp = (String) documentSnapshot.getData().get("timestamp");
//                    Reply reply = new Reply(commenter.getUserProfile().getUsername(), replyID, content, timestamp);
//                    commentRetriever.getComments(reply);
//                }
//            } else {
//
//                //if firestore query is unsuccessful, log an error and return
//                Log.d(TAG, "Error getting documents: ", task.getException());
//
//                return;
//
//            }
//        });
//    }
//
//    /**
//     * Function that retrieves all reply IDs from the database
//     *
//     * @return ArrayList of all reply IDs stored in database
//     */
//    public ArrayList<String> getAllReplyIDs() {
//        return getAllDocuments(REPLY_COLLECTION_PATH);
//    }


    public void getCollectionSize(String collection, CommentSizeRetriever collectionSize) {
        Task<DocumentSnapshot> task;
        task = database.collection(collection).document("size").get();
        task.addOnCompleteListener(task1 -> {
            if (task.isSuccessful()) {
                collectionSize.getSize((Long) task1.getResult().getData().get("value"));
            } else {
                //if firestore query is unsuccessful, log an error and return
                Log.d(TAG, "Error getting documents: ", task.getException());

                return;
            }
        });
    }

    /**
     * Function for adding a question to the database
     *
     * @param signedInExperimenterID The current experimenter posting the question
     * @param experimentID           experiment ID of the experiment that the question is being posted on
     * @param content                Content of question
     * @return Instance of question object added to database
     */
    public void postQuestion(String signedInExperimenterID, String experimentID, String content, long collectionSize) {
        String questionID = "q#" + String.format("%05d", collectionSize);


        Question question = new Question(questionID, signedInExperimenterID, content);

        // Retrieve Question data
        String timestamp = question.getTimestamp();

        // Add question data to HashMap
        HashMap<String, Object> questionData = new HashMap<>();
        questionData.put("poster", signedInExperimenterID);
        questionData.put("experiment id", experimentID);
        questionData.put("content", content);
        questionData.put("timestamp", timestamp);

        // Add question data to database
        addDataToCollection(QUESTION_COLLECTION_PATH, questionID, questionData);

        HashMap replyCollectionSize = new HashMap<String, Integer>();
        replyCollectionSize.put("value", 0);
        database.collection(QUESTION_COLLECTION_PATH).document(questionID).collection("replies").document("size").set(replyCollectionSize);

        database.collection(QUESTION_COLLECTION_PATH).document("size").update("value", collectionSize + 1);
    }

    /**
     * Function for updating an existing question in the database
     *
     * @param question Instance of question object to update
     */
    public void updateQuestion(Question question) {
        // Retrieve Question data
        String questionID = question.getCommentID();
        String poster = question.getCommenterID();
        String content = question.getContent();
        String timestamp = question.getTimestamp();

        // Add question data to HashMap
        HashMap<String, Object> questionData = new HashMap<>();
        questionData.put("poster", poster);
        questionData.put("content", content);
        questionData.put("timestamp", timestamp);

        // Update question data to database
        database.collection(QUESTION_COLLECTION_PATH)
                .document(questionID)
                .update(questionData);
    }

    /**
     * Function for adding a reply to the database
     *
     * @param signedInExperimenterID The experimenter who created the reply
     * @param content                Content of reply
     */
    public void postReply(String questionID, String signedInExperimenterID, String content, long collectionSize) {
        String replyID = "r#" + String.format("%05d", collectionSize);

        Reply reply = new Reply(replyID, signedInExperimenterID, content);


        HashMap<String, Object> replyData = new HashMap<>();
        replyData.put("poster", reply.getCommenterID());
        replyData.put("content", reply.getContent());
        replyData.put("timestamp", reply.getTimestamp());

        // Add reply data
        database.collection(QUESTION_COLLECTION_PATH).document(questionID).collection("replies").document("size").update("value", collectionSize + 1);
        database.collection(QUESTION_COLLECTION_PATH).document(questionID).collection("replies").document(replyID).set(replyData);
    }

    public interface CommentSizeRetriever {
        void getSize(long size);
    }

//    /**
//     * Function for deleting a question from the database
//     *
//     * @param question Instance of Question object to delete from database
//     */
//    public void deleteQuestion(Question question) {
//        // Retrieve question ID and remove question from database
//        String questionID = question.getCommentID();
//        removeDataFromCollection(QUESTION_COLLECTION_PATH, questionID);
//
//        // Retrieve reply ID's and remove all child replies from database
//        ArrayList<String> replyIDs = question.getReplyIDs();
//        for (String replyID : replyIDs) {
//            removeDataFromCollection(REPLY_COLLECTION_PATH, replyID);
//        }
//    }
//
//    /**
//     * Function for deleting a comment from the database
//     *
//     * @param reply Instance of Reply object to delete from database
//     */
//    public void deleteReply(Reply reply) {
//        // Retrieve reply ID and remove reply from database
//        String replyID = reply.getCommentID();
//        removeDataFromCollection(REPLY_COLLECTION_PATH, replyID);
//    }
}
