package com.example.crowdtest.ui;

import android.icu.util.Measure;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Value input experiment activity class. Value input experiments are experiments that require a number for their trials. Double for measurement trials and positive int for Non negative trials
 */
public class ValueInputActivity extends ExperimentActivity {
    Button addButton;
    EditText valueEditText;
    /**
     * To determine whether the experiment is a measurement experiment or a non negative integer experiment
     */
    boolean isMeasurement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        if (experimentBundle.getSerializable("experiment") instanceof Measurement){
            experiment = (Measurement) experimentBundle.getSerializable("experiment");
            isMeasurement = true;
        }
        else {
            experiment = (NonNegative) experimentBundle.getSerializable("experiment");
            isMeasurement = false;
        }
        setContentView(R.layout.activity_value_input);
        setValues();

        addButton = findViewById(R.id.value_input_add_button);
        valueEditText = findViewById(R.id.value_input_trial_input_editText);

        endExperiment = findViewById(R.id.experiment_end_experiment_button);
        endExperiment.setOnClickListener(v -> {
            if (endExperiment.getText().equals("End Experiment")) {
                experiment.setStatus("closed");
                endExperiment.setText("Reopen Experiment");
                addButton.setVisibility(View.INVISIBLE);
                valueEditText.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle() + " (Closed)");
            } else if (endExperiment.getText().equals("Reopen Experiment")) {
                experiment.setStatus("open");
                endExperiment.setText("End Experiment");
                addButton.setVisibility(View.VISIBLE);
                valueEditText.setVisibility(View.VISIBLE);
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });

        addButton.setOnClickListener(v -> {
            if (isMeasurement){
                double trialInput = Double.parseDouble(valueEditText.getText().toString());
                ((Measurement)experiment).addTrial(trialInput);
            }
            else{
                int trialInput = Integer.parseInt(valueEditText.getText().toString());
                valueEditText.setText("");
                try {
                    ((NonNegative)experiment).addTrial(trialInput);
                    Snackbar.make(v, "Trial added successfully", Snackbar.LENGTH_SHORT);
                } catch (Exception e) {
                    Snackbar.make(v, "Please enter a non negative integer", Snackbar.LENGTH_SHORT);
                }
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) -> {
            if (experiment.getStatus().equals("closed")) {
                endExperiment.setText("Reopen Experiment");
                addButton.setVisibility(View.INVISIBLE);
                valueEditText.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle() + " (Closed)");
            } else {
                endExperiment.setText("End Experiment");
                addButton.setVisibility(View.VISIBLE);
                valueEditText.setVisibility(View.VISIBLE);
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });
    }
}
