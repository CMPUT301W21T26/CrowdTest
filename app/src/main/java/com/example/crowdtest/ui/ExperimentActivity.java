package com.example.crowdtest.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;


/**
 * The general activity class for all the experiment activities
 */
public class ExperimentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton participants;
    TextView experimentDescription;
    TextView datePublished;
    Button details;
    Button endExperiment;
    RecyclerView questionList;
    Experiment experiment;
    String currentUser;

    /**
     * This method sets the values of the views that are common between all the experiment activities (Toolbar, participants, experiment description, date published, experiment details and the end experiment button)
     */
    public void setValues(){
        toolbar = findViewById(R.id.experiment_toolbar);
        if (experiment.getStatus().toLowerCase().equals("open")) {
            toolbar.setTitle(experiment.getTitle());
        }else {
            toolbar.setTitle(experiment.getTitle()+" (Closed)");
            toolbar.setTitleTextColor(0xFFE91E63);
        }

        //TODO: participants needs to be implemented

        experimentDescription = findViewById(R.id.experiment_description_textview);
        experimentDescription.setText(experiment.getDescription());

        datePublished = findViewById(R.id.experiment_publish_date_textview);
        datePublished.setText(experiment.getDatePublished().toString());
        //TODO: details button needs to be implemented
        //TODO: endExperiment button needs to be implemented

    }

    /**
     * Function for creating and showing an AlertDialog for confirmation before doing something
     *
     * Title:          How do I display an alert dialog on Android?
     * Author:         David Hedlund, https://stackoverflow.com/users/133802
     * Date:           2021-02-06
     * License:        CC BY-SA
     * Availability:   https://stackoverflow.com/a/2115770
     */
    public void showConfirmationDialog(String title, String message, Runnable runnable) {
        // Build the AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set builder attributes
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        // Delete the experiment item if the user presses "Yes"
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the "Yes" button is pressed
             * @param dialog    :   The dialog that received the click
             * @param which     :   The item that was clicked (represented by an integer)
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runnable.run();
            }

        });

        // Cancel the dialog if the user presses "No"
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the "No" button is pressed
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
}
