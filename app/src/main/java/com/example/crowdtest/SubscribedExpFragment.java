package com.example.crowdtest;

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

    ArrayList<Experiment> subscribedExperiments;

    CollectionReference collectionReference;

    Experimenter user;

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

        user = (Experimenter) getArguments().getSerializable("USER");

        subscribedExperiments = new ArrayList<Experiment>();

        ListView listView = (ListView) view.findViewById(R.id.sub_exp_view);

        ArrayAdapter<Experiment> listViewAdapter = new CustomList(getActivity(), subscribedExperiments);

        listView.setAdapter(listViewAdapter);

        registerForContextMenu(listView);

        collectionReference = db.collection("Experiments");

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

    /**
     * Inflate context menu, and set visibility of items that depend on user being owner
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
        Experiment experiment = subscribedExperiments.get(position);

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

                experimentManager.endExperiment(subscribedExperiments.get(info.position));

                return true;

            case R.id.unpublish_option:

                experimentManager.unpublishExperiment(subscribedExperiments.get(info.position));

                return true;

            default:

                return super.onContextItemSelected(item);
        }

    }
}