package com.example.crowdtest;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Database manager class for adding, removing, accessing and updating values in database collections
 */
public class DatabaseManager implements Serializable {

    // DatabaseManager attributes
    protected FirebaseFirestore database;

    /**
     * DatabaseManager constructor for general use
     */
    public DatabaseManager() {

        database = FirebaseFirestore.getInstance();
    }

    /**
     * Constructor for testing.
     * Uses dependency injection to allow mock firebase object to be passed in.
     * @param database
     */
    public DatabaseManager(FirebaseFirestore database){

        this.database = database;

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
     *     The name of the document being checked for
     * @param collectionPath
     *     The collectionPath for the collection that is being checked
     * @return
     *    Boolean representing whether the document is valid or not
     */
    public boolean isValidDocument(String documentName, String collectionPath) {
        // Check if document is already in the collection

        //return getAllDocuments(collectionPath).contains(documentName);

        if (getAllDocuments(collectionPath).contains(documentName)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Function for adding a document and its corresponding set of data to a collection
     * @param collectionPath
     *     The collectionPath for the collection that is having data added to it
     * @param document
     *     The name of the document being being added
     * @param data
     *     The HashMap of data to be added to the collection
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
     *     The collectionPath for the collection containing the document to be removed
     * @param document
     *     The document to be removed
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
     *     The prefix for the unique id (ex. User, Experiment, etc)
     * @param collectionPath
     *     The collectionPath for the collection that the document will be added to after creation
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
