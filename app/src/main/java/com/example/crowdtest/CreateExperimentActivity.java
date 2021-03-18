package com.example.crowdtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateExperimentActivity extends MainActivity {
    ListView expOptionsList;
    ArrayList<String> options;
    ArrayAdapter<String> optionAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_experiment_activity);

        NewExpFragment listFragment = new NewExpFragment(this.getExperimenter());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exp_fragment_view, listFragment);
        transaction.commit();

    }
}
