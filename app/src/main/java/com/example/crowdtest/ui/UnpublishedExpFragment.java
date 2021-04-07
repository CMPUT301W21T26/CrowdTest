package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * Fragment to display the unpublished experiments owned by the signed in user
 */
public class UnpublishedExpFragment extends Fragment {

    ExperimentManager experimentManager = new ExperimentManager();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Experiment> unpublishedExperiments;

    CollectionReference collectionReference;

    Experimenter user;

    public UnpublishedExpFragment() {
        // Required empty public constructor
    }

    /**
     * newInstance method to pass signed in user to the fragment
     * @param user
     *    The Experimenter that is currently signed in
     * @return
     *     A UnpublishedExpFragment with USER added as an argument
     */
    public static UnpublishedExpFragment newInstance(Experimenter user) {
        UnpublishedExpFragment fragment = new UnpublishedExpFragment();
        Bundle args = new Bundle();
        args.putSerializable("USER", user);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Custom onCreateView method
     * Displays experiments that a user owns which are unpublished
     * If experiment is long clicked, context menu with Republish and Delete options is displayed
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subscribed_exp, container, false);

        user = (Experimenter) getArguments().getSerializable("USER");

        unpublishedExperiments = new ArrayList<Experiment>();

        ListView listView = (ListView) view.findViewById(R.id.sub_exp_view);

        ArrayAdapter<Experiment> listViewAdapter = new CustomList(getActivity(), unpublishedExperiments);

        listView.setAdapter(listViewAdapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            Bundle experimentDetailsBundle = new Bundle();
            Experiment experiment = listViewAdapter.getItem(position);
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

        });

        collectionReference = db.collection("Experiments");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                unpublishedExperiments.clear();

                for (QueryDocumentSnapshot document : value) {

                    String expID = (String) document.getId();

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    if (experimentManager.experimentIsOwned(user, experiment) & !experiment.isPublished()) {

                        unpublishedExperiments.add(experiment);
                    }
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
                }

                listViewAdapter.notifyDataSetChanged();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Inflate context menu, and set visibility of items that depend on user being owner
     * Republish and Unpublish options are associated to this menu
     * @param menu
     * @param v
     * @param menuInfo
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.unpublished_context_menu, menu);
    }

    /**
     * Execute code based on selected context menu item
     * Republish changes the experiment to be published again
     * Delete removes the experiment entirely in firestore
     * @param item
     *     The selected menu item
     * @return
     *    Boolean indicating success of operation
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.republish_option:

                experimentManager.updatePublishExperiment(unpublishedExperiments.get(info.position), true);

                return true;

            case R.id.delete_option:

                experimentManager.deleteExperiment(unpublishedExperiments.get(info.position));

                return true;

            default:

                return super.onContextItemSelected(item);
        }

    }
}