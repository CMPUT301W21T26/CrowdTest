package com.example.crowdtest.ui;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;


public class ExperimentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton participants;
    TextView experimentDescription;
    TextView datePublished;
    Button details;
    Button endExperiment;
    RecyclerView questionList;
    Experiment experiment;

    public void setValues(){
        toolbar = findViewById(R.id.experiment_toolbar);
        toolbar.setTitle(experiment.getTitle());

        //TODO: participants needs to be implemented

        experimentDescription = findViewById(R.id.experiment_description_textview);
        experimentDescription.setText(experiment.getDescription());

        datePublished = findViewById(R.id.experiment_publish_date_textview);
        datePublished.setText(experiment.getDatePublished().toString());
        //TODO: details button needs to be implemented
        //TODO: endExperiment button needs to be implemented

    }
}
