package com.example.crowdtest.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdtest.CommentManager;
import com.example.crowdtest.LocationService;
import com.example.crowdtest.Question;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


/**
 * The general activity class for all the experiment activities
 */
public abstract class ExperimentActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    Toolbar toolbar;
    ImageButton participants;
    TextView experimentDescription;
    TextView datePublished;
    Button details;
    Button endExperiment;

    RecyclerView questionList;
    RecyclerView.LayoutManager layoutManager;
    Experiment experiment;

    String currentUser;

    Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;

    CommentManager commentManager = CommentManager.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * This method sets the values of the views that are common between all the experiment activities (Toolbar, participants, experiment description, date published, experiment details and the end experiment button)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setValues() {
        toolbar = findViewById(R.id.experiment_toolbar);
        if (experiment.getStatus().toLowerCase().equals("open")) {
            toolbar.setTitle(experiment.getTitle());
        } else {
            toolbar.setTitle(experiment.getTitle() + " (Closed)");
            toolbar.setTitleTextColor(0xFFE91E63);
        }

        //TODO: participants needs to be implemented

        experimentDescription = findViewById(R.id.experiment_description_textview);
        experimentDescription.setText(experiment.getDescription());

        datePublished = findViewById(R.id.experiment_publish_date_textview);
        datePublished.setText(experiment.getDatePublished().toString());
        //TODO: details button needs to be implemented
        //TODO: endExperiment button needs to be implemented

        questionList = (RecyclerView) findViewById(R.id.experiment_question_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        questionList.setLayoutManager(layoutManager);
        QuestionListAdapter questionListAdapter = new QuestionListAdapter(experiment.getQuestions(), currentUser);
        questionList.setAdapter(questionListAdapter);

        CollectionReference questionsCollectionReference = db.collection("Questions");
        ArrayList<Long> questionCollectionSize = new ArrayList<>();
        questionsCollectionReference.addSnapshotListener((value, error) ->
        {
            experiment.getQuestions().clear();
            for (QueryDocumentSnapshot document : value) {
                if (document.getId().equals("size")) {
                    questionCollectionSize.add((Long) document.getData().get("value"));
                } else if (document.getData().get("experiment id").equals(experiment.getExperimentID())) {
                    Question question = commentManager.getQuestion(document);
                    experiment.addQuestion(question);
                }

                questionListAdapter.notifyDataSetChanged();
            }
        });

        EditText commentBox = findViewById(R.id.experiment_comment_editText);

        Button postComment = findViewById(R.id.question_thread_post_button);

        postComment.setOnClickListener(v -> {
            String comment = commentBox.getText().toString();
            commentBox.setText("");
            commentManager.postQuestion(currentUser, experiment.getExperimentID(), comment, questionCollectionSize.get(0));
            questionCollectionSize.set(0, questionCollectionSize.get(0) + 1);
        });
        locationRequest = LocationRequest.create();
        locationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(2000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        currentLocation.setLatitude(location.getLatitude());
                        currentLocation.setLongitude(location.getLongitude());
                    }
                }
            }
        };
        currentLocation = new Location("provider");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation.setLatitude(location.getLatitude());
                    currentLocation.setLongitude(location.getLongitude());
                }
                else {
                    locationRequest = LocationRequest.create();
                    locationRequest
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(2000);
                }
            }
        });
        startLocationUpdate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdate();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    /**
     * Function for creating and showing an AlertDialog for confirmation before doing something
     * <p>
     * Title:          How do I display an alert dialog on Android?
     * Author:         David Hedlund, https://stackoverflow.com/users/133802
     * Date:           2021-02-06
     * License:        CC BY-SA
     * Availability:   https://stackoverflow.com/a/2115770
     */
//    public void showConfirmationDialog(String title, String message, Runnable runnable) {
//        // Build the AlertDialog and define its contents
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Set builder attributes
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setCancelable(false);
//
//        // Delete the experiment item if the user presses "Yes"
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//            /**
//             * Function for defining the dialog's behavior when the "Yes" button is pressed
//             * @param dialog    :   The dialog that received the click
//             * @param which     :   The item that was clicked (represented by an integer)
//             */
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                runnable.run();
//            }
//
//        });
//
//        // Cancel the dialog if the user presses "No"
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//            /**
//             * Function for defining the dialog's behavior when the "No" button is pressed
//             * @param dialog    :   The dialog that received the click
//             * @param which     :   The button that was clicked (represented by an integer)
//             */
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//
//        });
//
//        // Create and show the dialog
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

}
