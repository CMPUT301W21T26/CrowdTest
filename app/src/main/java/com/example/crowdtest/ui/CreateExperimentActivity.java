package com.example.crowdtest.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.R;

import java.util.ArrayList;

public class CreateExperimentActivity extends MainActivity {
    ListView expOptionsList;
    ArrayList<String> options;
    ArrayAdapter<String> optionAdapter;
    Experimenter owner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_experiment_activity);
        owner = (Experimenter) getIntent().getSerializableExtra("USER");

        NewExpFragment listFragment = new NewExpFragment(owner);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exp_fragment_view, listFragment);
        transaction.commit();

    }
}
