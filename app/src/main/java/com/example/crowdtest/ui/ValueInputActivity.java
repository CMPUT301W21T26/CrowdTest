package com.example.crowdtest.ui;

import android.icu.util.Measure;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.google.android.material.snackbar.Snackbar;

public class ValueInputActivity extends ExperimentActivity {
    Button addButton;
    EditText valueEditText;
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

        addButton.setOnClickListener(v -> {
            if (isMeasurement){
                double trialInput = Double.parseDouble(valueEditText.getText().toString());
                ((Measurement)experiment).addTrial(trialInput);
            }
            else{
                int trialInput = Integer.parseInt(valueEditText.getText().toString());
                try {
                    ((NonNegative)experiment).addTrial(trialInput);
                } catch (Exception e) {
                    Snackbar.make(v, "Please enter a non negative integer", Snackbar.LENGTH_SHORT);
                }
            }
        });
    }
}
