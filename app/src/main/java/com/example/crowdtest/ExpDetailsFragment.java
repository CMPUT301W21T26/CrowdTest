package com.example.crowdtest;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class ExpDetailsFragment extends Fragment {
    TextView header;
    EditText userData;
    String description;
    String location;
    String mode; // Will be either 'description' or 'location' mode

    public ExpDetailsFragment() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_exp_details, container, false);

        header = (TextView) view.findViewById(R.id.expDetailsHeader);
        userData = (EditText) view.findViewById(R.id.expDetailsEditText);
        Button nextButton = (Button) view.findViewById(R.id.detailsNextButton);
        mode = "description";

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // grab text from editText, clear text from editText, and change text
                if (mode == "description") {

                    header.setText("Add a region:");

                    description = userData.getText().toString();
                    userData.setText("");
                    mode = "location";
                }

                else { // get location data and then start new fragment
                    location = userData.getText().toString();
                    /*
                    ExpDetailsFragment detailsFragment = new ExpDetailsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.exp_fragment_view, detailsFragment);
                    transaction.commit();

                     */
                }


            }
        });


        return view;
    }

}
