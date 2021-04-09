package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Count;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Count experiment activity class
 */
public class CountActivity extends ExperimentActivity {
    private Button addButton;
    private Button detailsButton;
    private ImageButton participantsButton;
    private ParticipantsHelper participantsHelper;
    private ExperimentManager experimentManager = new ExperimentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Count) experimentBundle.getSerializable("experiment");
        currentUser = (String) getIntent().getStringExtra("username");

        setContentView(R.layout.activity_count);
        setValues();

        addButton = findViewById(R.id.count_add_button);
        if (experiment.isGeolocationEnabled()) {
            addButton.setOnClickListener(v -> {
                String title = "Trial Confirmation";
                String message = "Adding a trial will record your geo-location. Do you wish to continue?";
                showConfirmationDialog(title, message, new Runnable() {
                    @Override
                    public void run() {
                        ((Count) experiment).addTrial();
                    }
                });
            });
        } else {
            addButton.setOnClickListener(v -> ((Count) experiment).addTrial());
        }

        // Allows user to end an experiment if they are the owner
        endExperiment = findViewById(R.id.experiment_end_experiment_button);
        if (experiment.getOwner().equals(currentUser)) {
            endExperiment.setVisibility(View.VISIBLE);
            endExperiment.setOnClickListener(v -> {
                if (endExperiment.getText().equals("End Experiment")) {
                    experiment.setStatus("closed");
                    endExperiment.setText("Reopen Experiment");
                    addButton.setVisibility(View.INVISIBLE);
                    toolbar.setTitleTextColor(0xFFE91E63);
                    toolbar.setTitle(experiment.getTitle() + " (Closed)");
                } else if (endExperiment.getText().equals("Reopen Experiment")) {
                    experiment.setStatus("open");
                    endExperiment.setText("End Experiment");
                    addButton.setVisibility(View.VISIBLE);
                    toolbar.setTitleTextColor(0xFF000000);
                    toolbar.setTitle(experiment.getTitle());
                }
            });
        } else {
            endExperiment.setVisibility(View.INVISIBLE);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) -> {
            addButton.setText(String.valueOf(((Count) experiment).getCount()));
            if (experiment.getStatus().toLowerCase().equals("closed")) {
                endExperiment.setText("Reopen Experiment");
                addButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle() + " (Closed)");
            } else {
                endExperiment.setText("End Experiment");
//                if (experiment.getSubscribers().contains(currentUser) && !experiment.getBlackListedUsers().contains(currentUser)) {
                if (experiment.getSubscribers().contains(currentUser)) {
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.INVISIBLE);
                }
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });

        detailsButton = findViewById(R.id.experiment_details_button);

        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ExpStatisticsActivity.class);

                intent.putExtra("EXP", experiment);

                startActivity(intent);

            }
        });

        participantsButton = findViewById(R.id.exp_count_participants_button);

        participantsButton.setOnClickListener(view -> {

            participantsHelper = new ParticipantsHelper(this, experimentManager, experiment, currentUser);
            participantsHelper.displayParticipantList("Participants","Back");
        });
    }
}
