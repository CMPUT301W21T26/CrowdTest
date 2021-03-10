package com.example.crowdtest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Fragment to display a user's owned experiments in the ExperimentListActivity
 */
public class MyExpFragment extends Fragment {


    public MyExpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyExpFragment.
     */
    // TODO: Rename and change types and number of parameters to take in necessary experiment arguments
    public static MyExpFragment newInstance(String param1, String param2) {
        MyExpFragment fragment = new MyExpFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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

        String[] experiments = {"Exp1", "Exp2", "Exp3"}; //TODO: Remove this an have a list of experiments from database be passed in

        ListView listView = (ListView) view.findViewById(R.id.my_exp_list_view);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                experiments
        );

        listView.setAdapter(listViewAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}