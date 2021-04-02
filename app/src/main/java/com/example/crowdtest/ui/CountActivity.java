package com.example.crowdtest.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Count experiment activity class
 */
public class CountActivity extends ExperimentActivity {
    Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Count) experimentBundle.getSerializable("experiment");
        setContentView(R.layout.activity_count);
        setValues();

        addButton = findViewById(R.id.count_add_button);
        addButton.setOnClickListener(v -> ((Count)experiment).addTrial());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) -> {
            addButton.setText(String.valueOf(((Count) experiment).getCount()));
        });
    }
}
