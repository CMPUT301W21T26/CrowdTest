package com.example.crowdtest.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

/**
 * Class for scanning QR codes and updating database
 *
 * Title:          code-scanner
 * Author:         Yuriy Budiyev et al (https://github.com/yuriy-budiyev/code-scanner/graphs/contributors)
 * Date:           2021-04-08
 * License:        MIT
 * Availability:   https://github.com/yuriy-budiyev/code-scanner
 */
public class CodeScanActivity extends AppCompatActivity  {
    CodeScanner codeScanner;
    CodeScannerView scanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        scanView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scanView);
        ExperimentManager experimentManager = new ExperimentManager();

        // resultData = findViewById(R.id.resultsOfQr);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String id = getExperimentId(result.getText());
                        //String type = getExperimentType(result.getText());
                        Toast.makeText(CodeScanActivity.this, id, Toast.LENGTH_SHORT).show(); // will just pop up the exp id
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference collectionReference = db.collection("Experiments").document(id).collection("trials");
                        //new CountTrial()
                        /*
                        help!!! at this point I want to grab the experiment (we have the id) and then add a trial to it, and then update database
                        I don't know what I'm doing ahhhhh
                         */

                    }
                });
            }
        });

        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    /**
     * Method for requesting camera use permission
     *
     * Title:          Dexter
     * Author:         Karumi et al (https://github.com/Karumi/Dexter/graphs/contributors)
     * Date:           2021-04-08
     * License:        APACHE 2.0
     * Availability:   https://github.com/Karumi/Dexter
     */
    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(CodeScanActivity.this, "Camera permission is required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private String getExperimentId(String result) {
        String id = "";

        int i = 0;

        while (i < result.length()) {
            if (" ".equals(Character.toString(result.charAt(i)))) {
                i = result.length();
            }
            else {
                id += result.charAt(i);
                i++;
            }
        }

        return id;
    }

}
