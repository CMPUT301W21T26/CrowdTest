package com.example.crowdtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    ExperimentManager experimentManager;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_experiment);

        searchWordEditText = findViewById(R.id.search_word_edittext);
        searchButton = findViewById(R.id.search_button);
        allExperimentList = findViewById(R.id.all_experiment_list);

        experimentDataList = new ArrayList<>();

        experimentManager = new ExperimentManager();

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments");

        experimentManager.getAllExperiments(new GetAllExperimentsResults() {
            @Override
            public void onGetAllExperiments(Experiment experiment) {
                experimentDataList.add(experiment);
            }
        });

        experimentAdapter = new CustomList(this, experimentDataList);

        allExperimentList.setAdapter(experimentAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                experimentDataList.clear();

                for (QueryDocumentSnapshot document: value) {

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    experimentDataList.add(experiment);
                }

                experimentAdapter.notifyDataSetChanged();
            }
        });



    }
}