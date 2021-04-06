package com.example.crowdtest.ui;

import android.content.Intent;
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
    private Button addButton;
    private Button detailsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Count) experimentBundle.getSerializable("experiment");
        setContentView(R.layout.activity_count);
        setValues();

        addButton = findViewById(R.id.count_add_button);
        addButton.setOnClickListener(v -> ((Count) experiment).addTrial());

        endExperiment = findViewById(R.id.experiment_end_experiment_button);
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) -> {
            addButton.setText(String.valueOf(((Count) experiment).getCount()));
            if (experiment.getStatus().equals("closed")) {
                endExperiment.setText("Reopen Experiment");
                addButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle() + " (Closed)");
            } else {
                endExperiment.setText("End Experiment");
                addButton.setVisibility(View.VISIBLE);
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
