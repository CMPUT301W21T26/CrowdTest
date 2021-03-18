package com.example.crowdtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class DatabaseManager implements Serializable {

    // DatabaseManager attributes
    protected FirebaseFirestore database;

    /**
     * DatabaseManager constructor
     */
    public DatabaseManager() {
        database = FirebaseFirestore.getInstance();
    }

    /**
     * Function for retrieving all documents from a collection
     * @param collectionPath
     */
    public ArrayList<String> getAllDocuments(String collectionPath) {
        // Retrieve all documents from the given collection
        ArrayList<String> documentList = new ArrayList<>();
        final CollectionReference collectionReference = database.collection(collectionPath);

        // TODO: verify that this works
        collectionReference.addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    documentList.add(doc.getId());
                }
            }
        });

        return documentList;
    }

    /**
     * Function for checking whether a document already exists within a collection
     * @param documentName
     * @param collectionPath
     * @return
     */
    public boolean isValidDocument(String documentName, String collectionPath) {
        // Check if document is already in the collection
        if (getAllDocuments(collectionPath).contains(documentName)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Function for adding a document and its corresponding set of data to a collection
     * @param collectionPath
     * @param document
     * @param data
     */
    public void addDataToCollection(String collectionPath, String document, HashMap data) {
        // Add key-value pair to DB
        final CollectionReference collectionReference = database.collection(collectionPath);
        collectionReference
                .document(document)
                .set(data);
    }

    /**
     * Function for deleting a document from a collection
     * @param collectionPath
     * @param document
     */
    public void removeDataFromCollection(String collectionPath, String document) {
        // Delete key-value pair from DB
        final CollectionReference collectionReference = database.collection(collectionPath);
        collectionReference
                .document(document)
                .delete();
    }

    /**
     * Function for generating a unique document ID
     * @param prefix
     * @param collectionPath
     * @return
     */
    public String generateDocumentID(String prefix, String collectionPath) {
        // Generate a random number between 0 and 9999
        Random rand = new Random();
        int generatedNumber = rand.nextInt(10000);

        // Create the generated username
        String returnedDocumentID = "";
        String generatedDocumentID = prefix + String.valueOf(generatedNumber);

        // Validate the username and return if valid
        if (isValidDocument(generatedDocumentID, collectionPath)) {
            returnedDocumentID = generatedDocumentID;
        } else {
            generateDocumentID(prefix, collectionPath);
        }

        return returnedDocumentID;
    }
}
