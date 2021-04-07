package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Binomial experiment activity class that has two buttons for submitting success and failure
 */
public class BinomialActivity extends ExperimentActivity {
    private Button successButton;
    private Button failButton;
    private Button detailsButton;
    private ImageButton qrButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Binomial) experimentBundle.getSerializable("experiment");
        currentUser = (String) getIntent().getStringExtra("username");

        setContentView(R.layout.activity_binomial);
        setValues();

        successButton = findViewById(R.id.binomial_success_button);
        if (experiment.isGeolocationEnabled()) {
            successButton.setOnClickListener(v -> {
                String title = "Trial Confirmation";
                String message = "Adding a trial will record your geo-location. Do you wish to continue?";
                showConfirmationDialog(title, message, new Runnable() {
                    @Override
                    public void run() {
                        ((Binomial) experiment).addTrial(true);
                        successButton.setText(String.valueOf(((Binomial) experiment).getSuccessCount()));
                    }
                });
            });
        } else {
            successButton.setOnClickListener(v -> ((Binomial) experiment).addTrial(true));
            successButton.setText(String.valueOf(((Binomial) experiment).getSuccessCount()));
        }

        failButton = findViewById(R.id.binomial_fail_button);
        if (experiment.isGeolocationEnabled()) {
            failButton.setOnClickListener(v -> {
                String title = "Trial Confirmation";
                String message = "Adding a trial will record your geo-location. Do you wish to continue?";
                showConfirmationDialog(title, message, new Runnable() {
                    @Override
                    public void run() {
                        ((Binomial) experiment).addTrial(false);
                        failButton.setText(String.valueOf(((Binomial) experiment).getFailCount()));
                    }
                });
            });
        } else {
            failButton.setOnClickListener(v -> ((Binomial) experiment).addTrial(false));
            failButton.setText(String.valueOf(((Binomial) experiment).getFailCount()));
        }

        qrButton = findViewById(R.id.qr_icon);
        qrButton.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), QRActivity.class);
            intent.putExtra("EXTRA_EXP_TYPE", "binomial");
            intent.putExtra("EXTRA_EXP_ID", experiment.getExperimentID());
            startActivity(intent);

        });

        // Allows user to end an experiment if they are the owner
        endExperiment = findViewById(R.id.experiment_end_experiment_button);
        if (experiment.getOwner().equals(currentUser)) {
            endExperiment.setVisibility(View.VISIBLE);
            endExperiment.setOnClickListener(v -> {
                if (endExperiment.getText().equals("End Experiment")) {
                    experiment.setStatus("closed");
                    endExperiment.setText("Reopen Experiment");
                    successButton.setVisibility(View.INVISIBLE);
                    failButton.setVisibility(View.INVISIBLE);
                    toolbar.setTitleTextColor(0xFFE91E63);
                    toolbar.setTitle(experiment.getTitle() + " (Closed)");
                } else if (endExperiment.getText().equals("Reopen Experiment")) {
                    experiment.setStatus("open");
                    endExperiment.setText("End Experiment");
                    successButton.setVisibility(View.VISIBLE);
                    failButton.setVisibility(View.VISIBLE);
                    toolbar.setTitleTextColor(0xFF000000);
                    toolbar.setTitle(experiment.getTitle());
                }
            });
        } else {
            endExperiment.setVisibility(View.INVISIBLE);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) ->
        {
            successButton.setText(String.valueOf(((Binomial) experiment).getSuccessCount()));
            failButton.setText(String.valueOf(((Binomial) experiment).getFailCount()));
            if (experiment.getStatus().toLowerCase().equals("closed")){
                endExperiment.setText("Reopen Experiment");
                successButton.setVisibility(View.INVISIBLE);
                failButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle()+" (Closed)");
            } else{
                endExperiment.setText("End Experiment");
//                if (experiment.getSubscribers().contains(currentUser) && !experiment.getBlackListedUsers().contains(currentUser)) {
                if (experiment.getSubscribers().contains(currentUser)) {
                    successButton.setVisibility(View.VISIBLE);
                    failButton.setVisibility(View.VISIBLE);
                } else {
                    successButton.setVisibility(View.INVISIBLE);
                    failButton.setVisibility(View.INVISIBLE);
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
    }
}
