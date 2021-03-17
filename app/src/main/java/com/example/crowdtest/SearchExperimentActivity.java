package com.example.crowdtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchExperimentActivity extends AppCompatActivity {

    EditText searchWordEditText;
    Button searchButton;
    ListView allExperimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    ArrayList<Experiment> allExperimentDataList;
    ExperimentManager experimentManager;
    String searchString;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_experiment);

        searchString = "";

        searchWordEditText = findViewById(R.id.search_word_edittext);
        searchButton = findViewById(R.id.search_button);
        allExperimentList = findViewById(R.id.all_experiment_list);

        allExperimentDataList = new ArrayList<>();
        experimentDataList = new ArrayList<>();

        experimentManager = new ExperimentManager();

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments");

        experimentAdapter = new CustomList(this, experimentDataList);

        allExperimentList.setAdapter(experimentAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                experimentDataList.clear();

                for (QueryDocumentSnapshot document: value) {

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    experimentDataList.add(experiment);

                    allExperimentDataList.add(experiment);

                    experimentAdapter.notifyDataSetChanged();

                }
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchString = searchWordEditText.getText().toString();

                experimentDataList.clear();

                for (int i=0; i < allExperimentDataList.size(); i++) {

                    Experiment currentExperiment = allExperimentDataList.get(i);

                    if (experimentManager.experimentContainsKeyword(searchString, currentExperiment)){

                        experimentDataList.add(currentExperiment);

                    }

                }

                experimentAdapter.notifyDataSetChanged();

            }
        });

    }
}