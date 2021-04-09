package com.example.crowdtest.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.R;

import androidmads.library.qrgenearator.QRGEncoder;

public class QRActivity extends AppCompatActivity {

    private ImageView qrImageTop;
    private ImageView qrImageBot;
    private String inputValue;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;

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
    }
}
