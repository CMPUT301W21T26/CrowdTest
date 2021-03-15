package com.example.crowdtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowdtest.experiments.Experiment;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    public CustomList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        Experiment experiment = experiments.get(position);

        TextView owner = view.findViewById(R.id.owner_text);
        TextView status = view.findViewById(R.id.status_text);
        TextView expDesc = view.findViewById(R.id.exp_desc_text);

        owner.setText(experiment.getOwner().getUserProfile().getUsername());
        status.setText(experiment.getStatus());
        expDesc.setText(experiment.getExperimentID());

        return view;

    }
}
