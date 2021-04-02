package com.example.crowdtest.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.crowdtest.CustomList;
import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.GetTrials;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Activity to view all experiments and search for those containing a user-specified keyword
 * Long clicking experiments open context menu
 */
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
    Experimenter user;

    /**
     * Custom onCreate method
     * Long click displays context menu with View, Unpublish and End options for experiments
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_experiment);

        user = (Experimenter) getIntent().getSerializableExtra("USER");

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

        registerForContextMenu(allExperimentList);

        collectionReference.addSnapshotListener((value, error) -> {

            experimentDataList.clear();

            for (QueryDocumentSnapshot document: value) {

                Experiment experiment = experimentManager.getFirestoreExperiment(document);
                experimentManager.getTrials(experiment.getExperimentID(), experiment.getClass().getSimpleName(), new GetTrials() {
                    @Override
                    public void getBinomialTrials(BinomialTrial binomialTrial) {
                        ((Binomial) experiment).addTrialFromDb(binomialTrial);
                    }

                    @Override
                    public void getCountTrials(CountTrial countTrial) {
                        ((Count) experiment).getTrials().add(countTrial);
                    }

                    @Override
                    public void getNonNegativeTrials(NonNegativeTrial nonnegativeTrial) {
                        ((NonNegative) experiment).getTrials().add(nonnegativeTrial);
                    }

                    @Override
                    public void getMeasurementTrials(MeasurementTrial measurementTrial) {
                        ((Measurement) experiment).getTrials().add(measurementTrial);
                    }
                });

                if (experiment.isPublished()) {

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

        allExperimentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle experimentDetailsBundle = new Bundle();
                Experiment experiment = experimentAdapter.getItem(position);
                experimentDetailsBundle.putSerializable("experiment", experiment);
                Intent experimentActivityIntent = null;
                if (experiment instanceof Binomial){
                     experimentActivityIntent = new Intent(view.getContext(), BinomialActivity.class);
                }
                else if (experiment instanceof Count){
                    experimentActivityIntent = new Intent(view.getContext(), CountActivity.class);
                }
                else if (experiment instanceof Measurement || experiment instanceof NonNegative){
                    experimentActivityIntent = new Intent(view.getContext(), ValueInputActivity.class);
                }
                experimentActivityIntent.putExtras(experimentDetailsBundle);
                System.out.println(experiment.getClass());

                startActivity(experimentActivityIntent);
            }
        });

    }

    /**
     * Inflate context menu, and set visibility of items that depend on user being owner
     * @param menu
     * @param v
     * @param menuInfo
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int position = info.position;
        Experiment experiment = experimentDataList.get(position);

        Boolean isOwner = experimentManager.experimentIsOwned(user, experiment);
        if (isOwner) {
            if (experiment.getStatus().equals("open")) {
                MenuItem endItem = (MenuItem) menu.findItem(R.id.end_option);
                endItem.setVisible(isOwner);
            }
            MenuItem unpublishItem = (MenuItem) menu.findItem(R.id.unpublish_option);
            unpublishItem.setVisible(isOwner);
        }

        Boolean isSubscriber = experimentManager.experimentIsSubscribed(user, experiment);

        if (!isSubscriber & experiment.getStatus().equals("open")) {

            MenuItem subscribeItem = (MenuItem) menu.findItem(R.id.susbcribe_option);
            subscribeItem.setVisible(true);
        }

    }

    /**
     * Execute code based on selected context menu item
     * @param item
     *    The selected item from the context meny
     * @return
     *    A boolean value that suggests if selection code was successful
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.view_option:


                return true;

            case R.id.end_option:

                experimentManager.endExperiment(experimentDataList.get(info.position));

                return true;

            case R.id.unpublish_option:

                experimentManager.updatePublishExperiment(experimentDataList.get(info.position), false);

                experimentAdapter.notifyDataSetChanged();

                return true;

            case R.id.susbcribe_option:

                Experiment experiment = experimentDataList.get(info.position);

                experimentManager.addSubscriber(experiment, user.getUserProfile().getUsername());

            default:

                return super.onContextItemSelected(item);

        }

    }
}