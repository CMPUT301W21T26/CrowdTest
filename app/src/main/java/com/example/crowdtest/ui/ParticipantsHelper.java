package com.example.crowdtest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.GetTrials;
import com.example.crowdtest.R;
import com.example.crowdtest.UserProfile;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ParticipantsHelper {

    private Context context;
    private Experiment experiment;
    private ArrayList<String> subscribers;
    private ArrayAdapter<String> subscriberAdapter;
    private String currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    private ExperimentManager experimentManager;

    public ParticipantsHelper(Activity activity, ExperimentManager experimentManager, Experiment experiment, String currentUser) {
        this.context = (Context) activity;
        this.experimentManager = experimentManager;
        this.experiment = experiment;
        this.currentUser = currentUser;
    }

    public void displayParticipantList(String title, String negButtonText) {
        // Build an AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.fragment_participants_list, null);

        // Set builder attributes
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setNegativeButton(negButtonText, null);

        final ListView participantsList = dialogView.findViewById(R.id.participants_list_ListView);

        subscribers = experiment.getSubscribers();

        subscriberAdapter = new ArrayAdapter<>(context, R.layout.content, subscribers);

        participantsList.setAdapter(subscriberAdapter);

        participantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = participantsList.getItemAtPosition(position);
                String participant = object.toString();
                showUserInfo("User Info", participant);
            }
        });


        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();

        // Setup negative button
        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    public void showUserInfo(String title, String participant) {

        ArrayList<String> blacklisted = experiment.getBlackListedUsers();

        // Build an AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.fragment_user_info, null);

        // Set builder attributes
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setNegativeButton("Un-Blacklist", null);
        builder.setNeutralButton("Back", null);
        builder.setPositiveButton("Blacklist", null);

        final TextView userNameTextView = dialogView.findViewById(R.id.user_name_TextView);
        final TextView userEmailTextView = dialogView.findViewById(R.id.user_email_TextView);
        final TextView userNumberTextView = dialogView.findViewById(R.id.user_number_TextView);

        collectionReference = db.collection("Users");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (QueryDocumentSnapshot document : value) {

                    String userNameText = (String) document.getId();

                    if (userNameText.equals(participant)) {
                        String userEmailText = (String) document.getData().get("email");
                        String userNumberText = (String) document.getData().get("phoneNumber");
                        userNameTextView.setText("USERNAME:    " + userNameText);
                        userEmailTextView.setText("EMAIL:             " + userEmailText);
                        userNumberTextView.setText("NUMBER:        " + userNumberText);
                        break;
                    }
                }

            }
        });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();

        Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);

        if (experiment.getOwner().equals(currentUser) && blacklisted.contains(participant)) {
            positiveButton.setVisibility(View.INVISIBLE);
            negativeButton.setVisibility(View.VISIBLE);
        } else if (experiment.getOwner().equals(currentUser) && !blacklisted.contains(participant)) {
            positiveButton.setVisibility(View.VISIBLE);
            negativeButton.setVisibility(View.INVISIBLE);
        } else {
            positiveButton.setVisibility(View.INVISIBLE);
            negativeButton.setVisibility(View.INVISIBLE);
        }
        if (experiment.getOwner().equals(participant)) {
            positiveButton.setVisibility(View.INVISIBLE);
            negativeButton.setVisibility(View.INVISIBLE);
        }

        // Setup neutral button
        neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        // Setup negative button
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experimentManager.removeBlackListedUser(experiment, participant);
                positiveButton.setVisibility(View.VISIBLE);
                negativeButton.setVisibility(View.INVISIBLE);
            }
        });

        // Setup positive button
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experimentManager.addBlackListedUser(experiment, participant);
                positiveButton.setVisibility(View.INVISIBLE);
                negativeButton.setVisibility(View.VISIBLE);
            }
        });
    }
}
