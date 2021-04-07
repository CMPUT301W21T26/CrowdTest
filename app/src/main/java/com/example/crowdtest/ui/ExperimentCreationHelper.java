package com.example.crowdtest.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;

/**
 * ExperimentCreationHelper class for showing and defining the behaviour for various Dialog Fragments
 * for a given Activity instance (who implements the ActivityHolder interface)
 */
public class ExperimentCreationHelper {

    // Private ExperimentCreationHelper attributes
    private Context context;
    private ExperimentManager experimentManager;
    private Experimenter user;
    private Experiment experiment;

    /**
     * ExperimentCreationHelper constructor
     * @param activity  :   Activity instance to be 'helped'
     */
    public ExperimentCreationHelper(Activity activity, ExperimentManager experimentManager, Experimenter user) {
        this.context = (Context) activity;
        this.experimentManager = experimentManager;
        this.user = user;
    }

    /**
     * Function for creating and showing an AlertDialog with a text box
     * @param title
     * @param posButtonText
     * @param negButtonText
     */
    public void createTextBoxDialog(String title, String posButtonText, String negButtonText) {
        // Build an AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_textbox, null);

        // Set builder attributes
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton(posButtonText, null);
        builder.setNegativeButton(negButtonText, null);

        // Get user text
        final EditText titleEditText = dialogView.findViewById(R.id.textbox_title);
        final EditText descEditText = dialogView.findViewById(R.id.textbox_desc);
        final EditText regionEditText = dialogView.findViewById(R.id.textbox_region);
        final EditText minTrialsEditText = dialogView.findViewById(R.id.textbox_mintrials);
        final Switch geolocationSwitch = dialogView.findViewById(R.id.switch_geolocation);

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();

        // Setup positive button
        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user text
                String titleUserText = titleEditText.getText().toString();
                String descUserText = descEditText.getText().toString();
                String regionUserText = regionEditText.getText().toString();
                String minTrialsUserText = minTrialsEditText.getText().toString();

                int minTrialsUserNum = 0;
                if (minTrialsUserText.length() > 0) {
                    minTrialsUserNum = Integer.parseInt(minTrialsEditText.getText().toString());
                }

                // Validate text
                if (titleUserText.length() > 0 && descUserText.length() > 0 && regionUserText.length() > 0 && minTrialsUserNum > 0) {
                    // Publish experiment
                    experiment.setTitle(titleUserText);
                    experiment.setDescription(descUserText);
                    experiment.setRegion(regionUserText);
                    experiment.setMinTrials(minTrialsUserNum);
                    experiment.setGeolocationEnabled(geolocationSwitch.isChecked());
                    experimentManager.publishExperiment(experiment);
                    alert.dismiss();

                } else {
                    if (titleUserText.length() == 0) {
                        titleEditText.setHint("*Experiment Title");
                        titleEditText.setHintTextColor(Color.rgb(219, 90, 107));
                    }

                    if (descUserText.length() == 0) {
                        descEditText.setHint("*Experiment Description");
                        descEditText.setHintTextColor(Color.rgb(219, 90, 107));
                    }

                    if (regionUserText.length() == 0) {
                        regionEditText.setHint("*Experiment Region");
                        regionEditText.setHintTextColor(Color.rgb(219, 90, 107));
                    }

                    if (minTrialsUserNum == 0) {
                        minTrialsEditText.setHint("*Minimum Trials");
                        minTrialsEditText.setHintTextColor(Color.rgb(219, 90, 107));
                    }
                }
            }
        });

        // Setup negative button
        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                selectExperimentType();
            }
        });
    }

    /**
     * Function for creating and showing an AlertDialog with a list
     */
    public void selectExperimentType() {
        // Build the AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] items = {"Count-Based Trials", "Binomial Trials", "Non-Negative Trials", "Measurement Trials"};

        // Set builder attributes
        builder.setTitle("Experiment Selection");
        builder.setCancelable(false);

        // Set items to be displayed within the AlertDialog and define their behavior when pressed
        builder.setItems(items, new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when a given item is pressed
             *
             * @param dialog :   The dialog that received the click
             * @param which  :   The item that was clicked (represented by an integer)
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dialogTitle = "Experiment Creation";
                switch (which) {
                    case 0:
                        dialogTitle = "Count Experiment Creation";
                        experiment = new Count(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 1:
                        dialogTitle = "Binomial Experiment Creation";
                        experiment = new Binomial(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 2:
                        dialogTitle = "Non-Negative Experiment Creation";
                        experiment = new NonNegative(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 3:
                        dialogTitle = "Measurement Experiment Creation";
                        experiment = new Measurement(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    default:
                        dialog.cancel();
                        break;
                }

                createTextBoxDialog(dialogTitle, "Publish", "Back");
            }
        });

        // Cancel the dialog if the user presses negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the negative button is pressed
             *
             * @param dialog :   The dialog that received the click
             * @param which  :   The button that was clicked (represented by an integer)
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }
}
