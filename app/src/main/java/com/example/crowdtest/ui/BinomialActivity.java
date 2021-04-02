package com.example.crowdtest.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;

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
        successButton.setOnClickListener(v -> ((Binomial)experiment).addTrial(true));
        successButton.setText(String.valueOf(((Binomial)experiment).getSuccessCount()));

        failButton = findViewById(R.id.binomial_fail_button);
        failButton.setOnClickListener(v -> ((Binomial)experiment).addTrial(false));
        failButton.setText(String.valueOf(((Binomial)experiment).getFailCount()));
    }
}
