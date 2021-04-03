package com.example.crowdtest.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.crowdtest.GetTrials;
import com.example.crowdtest.R;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

/**
 * Binomial experiment activity class that has two buttons for submitting success and failure
 */
public class BinomialActivity extends ExperimentActivity {
    private Button successButton;
    private Button failButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Binomial) experimentBundle.getSerializable("experiment");

        setContentView(R.layout.activity_binomial);
        setValues();

        successButton = findViewById(R.id.binomial_success_button);
        successButton.setOnClickListener(v -> ((Binomial) experiment).addTrial(true));
        successButton.setText(String.valueOf(((Binomial) experiment).getSuccessCount()));

        failButton = findViewById(R.id.binomial_fail_button);
        failButton.setOnClickListener(v -> ((Binomial) experiment).addTrial(false));
        failButton.setText(String.valueOf(((Binomial) experiment).getFailCount()));

        endExperiment = findViewById(R.id.experiment_end_experiment_button);
        endExperiment.setOnClickListener(v -> {
            if (endExperiment.getText().equals("End Experiment")) {
                experiment.setStatus("closed");
                endExperiment.setText("Reopen Experiment");
                successButton.setVisibility(View.INVISIBLE);
                failButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle()+" (Closed)");
            } else if (endExperiment.getText().equals("Reopen Experiment")) {
                experiment.setStatus("open");
                endExperiment.setText("End Experiment");
                successButton.setVisibility(View.VISIBLE);
                failButton.setVisibility(View.VISIBLE);
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) ->
        {
            successButton.setText(String.valueOf(((Binomial) experiment).getSuccessCount()));
            failButton.setText(String.valueOf(((Binomial) experiment).getFailCount()));
            if (experiment.getStatus().equals("closed")){
                endExperiment.setText("Reopen Experiment");
                successButton.setVisibility(View.INVISIBLE);
                failButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle()+" (Closed)");
            }else{
                endExperiment.setText("End Experiment");
                successButton.setVisibility(View.VISIBLE);
                failButton.setVisibility(View.VISIBLE);
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });
    }
}
