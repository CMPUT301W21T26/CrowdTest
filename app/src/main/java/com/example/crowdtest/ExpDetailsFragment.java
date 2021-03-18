package com.example.crowdtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.experiments.Experiment;

import org.w3c.dom.Text;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Fragment to collect experiment information in the CreateExperimentActivity
 */

public class ExpDetailsFragment extends Fragment {
    Experiment experiment;
    int minNumTrials;
    TextView header;
    EditText userData;
    String description;
    String region;
    String trials;
    String mode; // Will be either 'description' or 'location' mode
    ExperimentManager manager;


    public ExpDetailsFragment(Experiment newExperiment, ExperimentManager expManager) {
        experiment = newExperiment;
        manager = expManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_exp_details, container, false);

        header = (TextView) view.findViewById(R.id.expDetailsHeader);
        userData = (EditText) view.findViewById(R.id.expDetailsEditText);
        userData.setHint("Enter a concise description.");
        Button nextButton = (Button) view.findViewById(R.id.detailsNextButton);
        mode = "description";

        Button cancelButton = (Button) view.findViewById(R.id.detailsCancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // grab text from editText, clear text from editText, and change text
                if (mode == "description") {

                    description = userData.getText().toString();
                    experiment.setDescription(description);

                    header.setText("Add a region:");
                    userData.setText("");
                    userData.setHint("Enter the general location.");
                    mode = "region";
                }

                else if (mode == "region") {
                    region = userData.getText().toString();
                    experiment.setRegion(region);
                    userData.setText("");
                    userData.setHint("Enter an integer only.");
                    header.setText("Minimum trials:");
                    nextButton.setText("Finish");
                    mode = "mintrialcount";
                }

                else { // start geolocation toggle fragment
                    /*
                    trials = userData.getText().toString();
                    minNumTrials = Integer.valueOf(trials);
                     */
                    GeolocationToggleFragment toggleFragment = new GeolocationToggleFragment(experiment, manager);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.exp_fragment_view, toggleFragment);
                    transaction.commit();
                }
            }
        });


        return view;
    }

}
