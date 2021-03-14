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

        /*
        expOptionsList = findViewById(R.id.exp_type_view);

        String[] optionArray = {"Count-Based", "Binomial Trials", "Non-Negative Count", "Measurement Trials"};

        options = new ArrayList<>();
        options.addAll(Arrays.asList(optionArray));

        // first connect our adapter to our list
        optionAdapter = new ArrayAdapter<>(this,R.layout.content,options);
        expOptionsList.setAdapter(optionAdapter);
        */

        NewExpFragment listFragment = new NewExpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exp_fragment_view, listFragment);
        transaction.commit();
        /*
        Button cancelButton = findViewById(R.id.cancelbutton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateExperimentActivity.this, MainActivity.class));
            }
        });*/
        /*
        expOptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        */

    }
}
