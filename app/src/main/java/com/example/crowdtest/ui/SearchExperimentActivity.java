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
import com.example.crowdtest.R;
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
    Experimenter user;

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
            MenuItem endItem = (MenuItem) menu.findItem(R.id.end_option);
            MenuItem unpublishItem = (MenuItem) menu.findItem(R.id.unpublish_option);
            endItem.setVisible(isOwner);
            unpublishItem.setVisible(isOwner);
        }

    }

    /**
     * Execute code based on selected context mneu item
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.view_option:


                return true;

            case R.id.end_option:

                return true;

            case R.id.unpublish_option:

                experimentManager.unpublishExperiment(experimentDataList.get(info.position));
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }
}