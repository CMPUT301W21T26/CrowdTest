package com.example.crowdtest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.crowdtest.experiments.Experiment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Fragment to display the experiments signed in user is subscribed to in the ExperimentListActivity
 */
public class SubscribedExpFragment extends Fragment {

    ExperimentManager experimentManager = new ExperimentManager();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SubscribedExpFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param user
     * @return
     */
    public static SubscribedExpFragment newInstance(Experimenter user) {
        SubscribedExpFragment fragment = new SubscribedExpFragment();
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

        View view = inflater.inflate(R.layout.fragment_subscribed_exp, container, false);

        Experimenter user = (Experimenter) getArguments().getSerializable("USER");

        ArrayList<Experiment> subscribedExperiments = new ArrayList<Experiment>();

        ListView listView = (ListView) view.findViewById(R.id.sub_exp_view);

        ArrayAdapter<Experiment> listViewAdapter = new CustomList(getActivity(), subscribedExperiments);

        listView.setAdapter(listViewAdapter);

        CollectionReference collectionReference = db.collection("Experiments");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                subscribedExperiments.clear();

                for (QueryDocumentSnapshot document : value) {

                    String expID = (String) document.getId();

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    if (experimentManager.experimentIsSubscribed(user, experiment)) {

                        subscribedExperiments.add(experiment);
                    }

                }

                listViewAdapter.notifyDataSetChanged();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}