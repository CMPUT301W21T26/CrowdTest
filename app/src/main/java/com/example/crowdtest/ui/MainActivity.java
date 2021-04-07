package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.Installation;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;

import java.util.ArrayList;

/**
 * Main Activity class
 */
public class MainActivity extends AppCompatActivity {

    private Button createNewButton;
    private Button myExperimentButton;
    private Button searchButton;
    private Button profileButton;

    private String installationID;
    private ExperimenterManager experimenterManager = new ExperimenterManager();
    private ExperimentManager experimentManager = new ExperimentManager();
    private Experimenter user;

    private ExperimentCreationHelper experimentCreationHelper;


    /**
     * Customized onCreate method
     * Displays buttons with options to create experiment, view experiments, search for experiments, and view/edit user profile
     * @param savedInstanceState
     */
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

        ArrayList<Experiment> experimentArrayList = new ArrayList<>();

        //assign a value to the current user variable representing signed in experimenter
        experimenterManager.retrieveExperimenter(installationID, experimenter -> user = experimenter);

        createNewButton.setOnClickListener(view -> {

            if (user != null) {

                experimentCreationHelper = new ExperimentCreationHelper(this, experimentManager, user);
                experimentCreationHelper.selectExperimentType();

            }

        });

        myExperimentButton.setOnClickListener(view -> {

            if (user != null) {

                Intent intent = new Intent(view.getContext(), ExperimentListActivity.class);

                intent.putExtra("USER", user);

                startActivity(intent);

            }

        });

        searchButton.setOnClickListener(view -> {

            if (user != null) {

                Intent intent = new Intent(view.getContext(), SearchExperimentActivity.class);

                while (user == null) {
                    continue;
                }

                intent.putExtra("USER", user);

                startActivity(intent);

            }


        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UserProfileActivity.class);
            intent.putExtra("User", user);
            startActivityForResult(intent, 0);
        });
    }

    /**
     * Method for getting data returned by an Activity after startActivityForResult is called
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                user = (Experimenter) data.getSerializableExtra("User");
            }
        }
    }

}