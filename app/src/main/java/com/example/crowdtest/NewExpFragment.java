package com.example.crowdtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewExpFragment extends Fragment {
    View view;
    ListView expOptionsList;
    ArrayList<String> options;
    ArrayAdapter<String> optionAdapter;


    public NewExpFragment() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_exp, container, false);

        expOptionsList = (ListView) view.findViewById(R.id.optionList);
        String[] optionArray = {"Count-Based", "Binomial Trials", "Non-Negative Count", "Measurement Trials"};
        optionAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                optionArray
        );
        expOptionsList.setAdapter(optionAdapter);

        Button cancelButton = (Button) view.findViewById(R.id.newExpCancelButton);
        /*
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewExpFragment.this, MainActivity.class));
            }
        });
        */

        expOptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpDetailsFragment detailsFragment = new ExpDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.exp_fragment_view, detailsFragment);
                transaction.commit();

            }
        });
        return view;
    }

}