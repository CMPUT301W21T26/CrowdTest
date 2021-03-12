package com.example.crowdtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class NewExpFragment extends DialogFragment {
    private ListView expOptionsList;
    private ArrayList<String> options;
    private ArrayAdapter<String> optionAdapter;
    private OnFragmentInteractionListener listener;

    /*
    public interface OnFragmentInteractionListener {
        void onOkPressed(City newCity);
    }
         */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // from lab 3 demo
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_exp, null);
        expOptionsList = view.findViewById(R.id.exp_type_view);
        String[] optionArray = {"Count-Based", "Binomial Trials", "Non-Negative Count", "Measurement Trials"};

        options = new ArrayList<>();
        options.addAll(Arrays.asList(optionArray));

        optionAdapter = new ArrayAdapter<>(this,R.layout.content,options);
        expOptionsList.setAdapter(optionAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Select experiment Type:")
                .setNegativeButton("Cancel", null)
                .create();
    }


}
