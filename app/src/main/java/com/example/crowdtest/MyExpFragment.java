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
 * Fragment to display a user's owned experiments in the ExperimentListActivity
 */
public class MyExpFragment extends Fragment {

    ExperimentManager experimentManager = new ExperimentManager();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ;

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

        Experimenter user = (Experimenter) getArguments().getSerializable("USER");

        ArrayList<Experiment> ownedExperiments = new ArrayList<Experiment>();


        ListView listView = (ListView) view.findViewById(R.id.my_exp_list_view);

        ArrayAdapter<Experiment> listViewAdapter = new CustomList(getActivity(), ownedExperiments);

        listView.setAdapter(listViewAdapter);

        CollectionReference collectionReference = db.collection("Experiments");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                ownedExperiments.clear();

                for (QueryDocumentSnapshot document : value) {

                    String expID = (String) document.getId();

                    Experiment experiment = experimentManager.getFirestoreExperiment(document);

                    if (experimentManager.experimentIsOwned(user, experiment)) {

                        ownedExperiments.add(experiment);
                    }

                }

                listViewAdapter.notifyDataSetChanged();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}