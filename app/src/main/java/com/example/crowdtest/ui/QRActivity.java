package com.example.crowdtest.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.R;

import androidmads.library.qrgenearator.QRGEncoder;

public class QRActivity extends AppCompatActivity {

    private Button customBarcodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Intent intent = getIntent();
        String expType  = intent.getStringExtra("EXTRA_EXP_TYPE");
        String expId = intent.getStringExtra("EXTRA_EXP_ID");

        if (expType.equals("count")) {
            QRCodeCountFragment QRFragment = new QRCodeCountFragment(expId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.qr_fragment_layout, QRFragment);
            transaction.commit();
        } else if (expType.equals("binomial")) {
            QRCodeBinomialFragment QRFragment = new QRCodeBinomialFragment(expId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.qr_fragment_layout, QRFragment);
            transaction.commit();
        } else {
            // measurement experiment type
            QRCodeMeasurementFragment QRFragment = new QRCodeMeasurementFragment(expId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.qr_fragment_layout, QRFragment);
            transaction.commit();
        }

        /*
        customBarcodeButton = findViewById(R.id.custom_barcode_button);
        customBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
    }
}
