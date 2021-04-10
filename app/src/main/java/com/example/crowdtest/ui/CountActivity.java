package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Build;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.LocationService;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * Count experiment activity class
 */
public class CountActivity extends ExperimentActivity {

    private Button addButton;
    private Button detailsButton;
    private ImageButton qrButton;
    private ImageButton qrScanButton;

    private ImageButton participantsButton;
    private ParticipantsHelper participantsHelper;
    private ExperimentManager experimentManager = new ExperimentManager();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle experimentBundle = getIntent().getExtras();
        experiment = (Count) experimentBundle.getSerializable("experiment");
        currentUser = (String) getIntent().getStringExtra("username");

        setContentView(R.layout.activity_count);
        setValues();

        addButton = findViewById(R.id.count_add_button);
        if (experiment.isGeolocationEnabled()) {
            addButton.setOnClickListener(v -> {
                ((Count) experiment).addTrial(currentUser, currentLocation);
            });
        } else {
            addButton.setOnClickListener(v -> ((Count) experiment).addTrial(currentUser, null));
        }

        qrButton = findViewById(R.id.qr_icon);
        qrButton.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), QRActivity.class);
            intent.putExtra("EXTRA_EXP_TYPE", "count");
            intent.putExtra("EXTRA_EXP_ID", experiment.getExperimentID());
            startActivity(intent);

        });

        qrScanButton = findViewById(R.id.qr_scan_icon);
        qrScanButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("experiment", experiment);
            bundle.putString("user", currentUser);
            Intent intent = new Intent(view.getContext(), CodeScanActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        });


        // Allows user to end an experiment if they are the owner
        endExperiment = findViewById(R.id.experiment_end_experiment_button);
        if (experiment.getOwner().equals(currentUser)) {
            endExperiment.setVisibility(View.VISIBLE);
            endExperiment.setOnClickListener(v -> {
                if (endExperiment.getText().equals("End Experiment")) {
                    experiment.setStatus("closed");
                    endExperiment.setText("Reopen Experiment");
                    addButton.setVisibility(View.INVISIBLE);
                    toolbar.setTitleTextColor(0xFFE91E63);
                    toolbar.setTitle(experiment.getTitle() + " (Closed)");
                } else if (endExperiment.getText().equals("Reopen Experiment")) {
                    experiment.setStatus("open");
                    endExperiment.setText("End Experiment");
                    addButton.setVisibility(View.VISIBLE);
                    toolbar.setTitleTextColor(0xFF000000);
                    toolbar.setTitle(experiment.getTitle());
                }
            });
        } else {
            endExperiment.setVisibility(View.INVISIBLE);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("Experiments").document(experiment.getExperimentID());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {


                if (e != null) {
                    Log.w("FAIL", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    addButton.setText(String.valueOf(((Count) experiment).getValidCount()));

                    //if there was a change to the experiment (not trials), it was that a user was blacklisted.
                    Log.d("SUCCESS", "Current data: " + snapshot.getData());

                } else {
                    Log.d("SNAPSHOT NOT EXISTENT", "Current data: null");
                }
            }
        });

        CollectionReference collectionReference = db.collection("Experiments").document(experiment.getExperimentID()).collection("trials");
        collectionReference.addSnapshotListener((value, error) -> {

            ((Count) experiment).getTrials().clear();
            for (QueryDocumentSnapshot document: value) {

                double locationLat = (Double) document.getData().get("locationLat");
                double locationLong = (Double) document.getData().get("locationLong");
                Location location = new Location("Provider");
                location.setLatitude(locationLat);
                location.setLongitude(locationLong);
                Date timeStamp = ((Timestamp) document.getData().get("timestamp")).toDate();
                String poster = (String) document.getData().get("user");
                CountTrial countTrial = new CountTrial(timeStamp, location, poster);
                ((Count) experiment).getTrials().add(countTrial);
            }


            addButton.setText(String.valueOf(((Count) experiment).getValidCount()));
            if (experiment.getStatus().toLowerCase().equals("closed")) {
                endExperiment.setText("Reopen Experiment");
                addButton.setVisibility(View.INVISIBLE);
                toolbar.setTitleTextColor(0xFFE91E63);
                toolbar.setTitle(experiment.getTitle() + " (Closed)");
            } else {
                endExperiment.setText("End Experiment");
                if (experiment.getSubscribers().contains(currentUser) && !experiment.getBlackListedUsers().contains(currentUser)) {
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.INVISIBLE);
                }
                toolbar.setTitleTextColor(0xFF000000);
                toolbar.setTitle(experiment.getTitle());
            }
        });

        detailsButton = findViewById(R.id.experiment_details_button);

        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ExpStatisticsActivity.class);

                intent.putExtra("EXP", experiment);

                startActivity(intent);

            }
        });

        participantsButton = findViewById(R.id.exp_count_participants_button);

        participantsButton.setOnClickListener(view -> {

            participantsHelper = new ParticipantsHelper(this, experimentManager, experiment, currentUser);
            participantsHelper.displayParticipantList("Participants","Back");
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            Bundle experimentBundle = data.getExtras();
            experiment = (Count) experimentBundle.getSerializable("experiment");
            setValues();
        }
    }

}
