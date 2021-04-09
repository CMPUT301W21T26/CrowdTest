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
 * Fragment to display a user's owned experiments in the ExperimentListActivity
 */
public class MyExpFragment extends Fragment {

    ExperimentManager experimentManager = new ExperimentManager();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference collectionReference;

    ArrayList<Experiment> ownedExperiments;

    Experimenter user;


    public MyExpFragment() {
        // Required empty public constructor
    }

    /**
     * Pass signed in user value to fragment
     * @param user
     * @return
     */
    public static MyExpFragment newInstance(Experimenter user) {
        MyExpFragment fragment = new MyExpFragment();
        Bundle args = new Bundle();
        args.putSerializable("USER", user);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_exp, container, false);

        user = (Experimenter) getArguments().getSerializable("USER");

        ownedExperiments = new ArrayList<Experiment>();

        ListView listView = (ListView) view.findViewById(R.id.my_exp_list_view);

        ArrayAdapter<Experiment> listViewAdapter = new CustomList(getActivity(), ownedExperiments);

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
            experimentActivityIntent.putExtra("username", user.getUserProfile().getUsername());
            System.out.println(experiment.getClass());

            startActivity(experimentActivityIntent);
        });

        collectionReference = db.collection("Experiments");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                ownedExperiments.clear();

                for (QueryDocumentSnapshot document : value) {

                    String expID = (String) document.getId();

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    if (experimentManager.experimentIsOwned(user, experiment) & experiment.isPublished()) {

                        ownedExperiments.add(experiment);
                    }
                }

                listViewAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                viewExperiment(view, position);

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Inflate context menu, and set visibility of items that depend on user being owner
     * Owners can view options to View, Unpublish, and End Experiments (if Exp is open)
     * Non-owners can see View option
     * @param menu
     * @param v
     * @param menuInfo
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int position = info.position;
        Experiment experiment = ownedExperiments.get(position);

        String status = experiment.getStatus();
        if (experiment.getStatus().toLowerCase().equals("open")) {
            MenuItem endItem = (MenuItem) menu.findItem(R.id.end_option);
            endItem.setVisible(true);
        }
        MenuItem unpublishItem = (MenuItem) menu.findItem(R.id.unpublish_option);
        unpublishItem.setVisible(true);
    }

    /**
     * Execute code based on selected context menu item
     * @param item
     *     The context menu item that was selected
     * @return
     *     A boolean representing success of the operation
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.view_option:

                viewExperiment(info.targetView, info.position);

                return true;

            case R.id.end_option:

                experimentManager.endExperiment(ownedExperiments.get(info.position));

                return true;

            case R.id.unpublish_option:

                experimentManager.updatePublishExperiment(ownedExperiments.get(info.position), false);

                return true;

            default:

                return super.onContextItemSelected(item);
        }

    }

    /**
     * Function for viewing an experiment
     * @param position
     */
    private void viewExperiment(View view, int position) {

        Bundle experimentDetailsBundle = new Bundle();
        Experiment experiment = ownedExperiments.get(position);
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
        experimentActivityIntent.putExtra("username", user.getUserProfile().getUsername());
        startActivity(experimentActivityIntent);
    }
}