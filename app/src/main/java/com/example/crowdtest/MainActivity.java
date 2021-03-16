package com.example.crowdtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button createNewButton;
    private Button myExperimentButton;
    private Button searchButton;
    private Button profileButton;

    private String installationID;
    private ExperimenterManager experimenterManager = new ExperimenterManager();
    private Experimenter user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        createNewButton = (Button) findViewById(R.id.create_new_button);
        myExperimentButton = (Button) findViewById(R.id.my_exp_button);
        searchButton = (Button) findViewById(R.id.search_button);
        profileButton = (Button) findViewById(R.id.profile_button);

        //initialize installation id
        installationID = (new Installation()).id(getApplicationContext());

        //assign a value to the current user variable representing signed in experimenter
        experimenterManager.retrieveExperimenter(installationID, new RetrieveExperimenterResults() {
             @Override
             public void onRetrieveExperimenter(Experimenter experimenter) {
                 user = experimenter;

             }
         });

        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateExperimentActivity.class);

                startActivity(intent);
            }
        });

        myExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ExperimentListActivity.class);

                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}