package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.Installation;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;

import java.util.ArrayList;

public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        String expTitle = getIntent().getStringExtra("EXTRA_EXP_TITLE");
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.exp_fragment_view, listFragment);
        //transaction.commit();

    }

   // public QRActivity(Experiment exp) {
  //      experiment = exp;
  //  }


}
