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

/**
 * Create a custom layout for displaying experiments in a list view
 */
public class CustomList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    /**
     * Constructor for custom array adapter. Assigns experiments attribute
     * @param context
     *     Current context
     * @param experiments
     *     ArrayList of experiments to be displayed in GUI
     */
    public CustomList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    /**
     *  Set how each entry is displayed based on the search_content layout
     * @param position
     *     Position of experiment being displayed in the ArrayList of experiments
     * @param convertView
     *    The old view to reuse if possible.
     * @param parent
     *     The containing adapter for the view
     * @return
     *    The view the correct TextView values based on the experiments in the experiments ArrayList
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.search_content, parent,false);
        }

        //get the experiment being displayed
        Experiment experiment = experiments.get(position);

        //initialize TextView variables
        TextView owner = view.findViewById(R.id.owner_text);
        TextView status = view.findViewById(R.id.status_text);
        TextView expDesc = view.findViewById(R.id.exp_desc_text);
        TextView expTitle = view.findViewById(R.id.exp_title_text);

        //Set text that is to be displayed for each experiment
        owner.setText("Owner: " + experiment.getOwner());

        if (experiment.isPublished()) {
            status.setText("Status: " + experiment.getStatus());
        }
        else {
            status.setText("Status: unpublished");
        }
        expTitle.setText(experiment.getTitle());
        expDesc.setText("Description: " + experiment.getDescription());
        return view;

    }
}
