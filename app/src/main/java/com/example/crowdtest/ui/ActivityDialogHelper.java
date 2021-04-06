package com.example.crowdtest.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
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
 * ActivityDialogHelper class for showing and defining the behaviour for various Dialog Fragments
 * for a given Activity instance (who implements the ActivityHolder interface)
 */
public class ActivityDialogHelper {

    // Private ActivityDialogHelper attributes
    private Context context;
    private ExperimentManager experimentManager;
    private Experimenter user;
    private Experiment experiment;

    /**
     * ActivityDialogHelper constructor
     * @param activity  :   Activity instance to be 'helped'
     */
    public ActivityDialogHelper(Activity activity, ExperimentManager experimentManager, Experimenter user) {
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

        // Get user text
        final EditText titleEditText = dialogView.findViewById(R.id.textbox_title);
        final EditText descEditText = dialogView.findViewById(R.id.textbox_desc);
        final EditText regionEditText = dialogView.findViewById(R.id.textbox_region);
        final EditText minTrialsEditText = dialogView.findViewById(R.id.textbox_mintrials);
        final Switch geolocationSwitch = dialogView.findViewById(R.id.switch_geolocation);

        builder.setPositiveButton(posButtonText, new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the positive button is pressed
             * @param dialog    :   The dialog that received the click
             * @param which     :   The button that was clicked (represented by an integer)
             */
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                } else {

                    // TODO: fix this
                    if (titleUserText.length() == 0) {
                        titleEditText.setHintTextColor(0xFF0000);
//                        showMessageDialog("Invalid title!", "Please review your entered title");
                    } else {
                        titleEditText.setHintTextColor(R.color.grey);
                    }

                    if (descUserText.length() == 0) {
                        descEditText.setHintTextColor(0xFF0000);
//                        showMessageDialog("Invalid description!", "Please review your entered description");
                    } else {
                        descEditText.setHintTextColor(R.color.grey);
                    }

                    if (regionUserText.length() == 0) {
                        regionEditText.setHintTextColor(0xFF0000);
//                        showMessageDialog("Invalid region!", "Please review your entered region");
                    } else {
                        regionEditText.setHintTextColor(R.color.grey);
                    }

                    if (minTrialsUserNum == 0) {
                        minTrialsEditText.setHintTextColor(0xFF0000);
//                        showMessageDialog("Invalid number of minimum trials!", "Please review your entered min trials");
                    } else {
                        minTrialsEditText.setHintTextColor(R.color.grey);
                    }
                }
            }
        });

        // Cancel the dialog if the user presses negative button
        builder.setNegativeButton(negButtonText, new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the negative button is pressed
             * @param dialog    :   The dialog that received the click
             * @param which     :   The button that was clicked (represented by an integer)
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

    /**
     * Function for creating and showing an AlertDialog with a list
     */
    public void selectExperimentType() {
        // Build the AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] items = {"Count-Based Trials", "Binomial Trials", "Non-Negative Trials", "Measurement Trials"};

        // Set builder attributes
        builder.setTitle("Select Experiment Type");
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
                switch (which) {
                    case 0:
                        experiment = new Count(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 1:
                        experiment = new Binomial(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 2:
                        experiment = new NonNegative(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    case 3:
                        experiment = new Measurement(user.getUserProfile().getUsername(), experimentManager.generateExperimentID());
                        break;

                    default:
                        dialog.cancel();
                        break;
                }

                createTextBoxDialog("Experiment Creation Menu", "Publish", "Cancel");
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

    /**
     * Function for creating and showing an AlertDialog with a message and one button
     * @param title     :   Title of dialog fragment
     * @param message   :   Message to print to the dialog fragment
     *
     * Title:          How do I display an alert dialog on Android?
     * Author:         David Hedlund, https://stackoverflow.com/users/133802
     * Date:           2021-02-06
     * License:        CC BY-SA
     * Availability:   https://stackoverflow.com/a/2115770
     */
    public void showMessageDialog(String title, String message) {
        // Build the AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        // Set builder attributes
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        // Dismiss the dialog if user presses "Ok"
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the "Ok" button is pressed
             * @param dialog    :   The dialog that received the click
             * @param which     :   The button that was clicked (represented by an integer)
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createTextBoxDialog("Experiment Creation", "Publish", "Cancel");
            }

        });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }
}
