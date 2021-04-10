package com.example.crowdtest.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Experiment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Class for scanning bar codes and creating custom documents of them
 *
 * Title:          code-scanner
 * Author:         Yuriy Budiyev et al (https://github.com/yuriy-budiyev/code-scanner/graphs/contributors)
 * Date:           2021-04-08
 * License:        MIT
 * Availability:   https://github.com/yuriy-budiyev/code-scanner
 */
public class CustomBarcodeActivity extends AppCompatActivity implements InputValueBarcodeFragment.InputValueBarcodeFragmentListener, BinomialBarcodeFragment.BinomialBarcodeFragmentListener {
    CodeScanner codeScanner;
    CodeScannerView scanView;
    Experiment experiment;
    String currentUserName;
    String output;
    String inputValue;
    boolean inputValueBinomial;
    int barcodeValueInt;
    double barcodeValueDouble;
    boolean barcodeValueBool;

    TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        scanView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scanView);
        String tailValue = "";

        String expId = getIntent().getStringExtra("EXTRA_EXP_ID");
        String expType = getIntent().getStringExtra("EXTRA_EXP_TYPE");
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String scanResult = result.getText();
                        if ("e".equals(Character.toString(scanResult.charAt(0)))) { // QR CODE SCANNED
                            output = "Wrong type of code (QR Code). Tap screen anywhere to try again.";
                            Toast.makeText(CustomBarcodeActivity.this, output, Toast.LENGTH_SHORT).show();

                        } else { // BAR CODE SCANNED
                            if (expType.equals("count")) {
                                output = "Barcode scanned for " + expId + " (" + result.getText() + ") with value 1.";
                                Toast.makeText(CustomBarcodeActivity.this, output, Toast.LENGTH_SHORT).show();
                                barcodeValueInt = 1;
                                //String type = getExperimentType(result.getText());
                                //Toast.makeText(CodeScanActivity.this, id, Toast.LENGTH_SHORT).show(); // will just pop up the exp id
                                // ADD BARCODE TO DATABASE (experiment____)
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference collectionReference = db.collection("Experiments").document(expId).collection("trials");
                                // ADD BARCODE TO DATABASE (experiment____)
                            } else if (expType.equals("nonnegative")) {
                                openInputDialogue();
                                // ADD BARCODE TO DATABASE (experiment____)
                                barcodeValueInt = Integer.parseInt(inputValue);
                            } else if (expType.equals("measurement")) {
                                openInputDialogue();
                                barcodeValueDouble = Double.parseDouble(inputValue);
                                // ADD BARCODE TO DATABASE (experiment____)
                            } else {
                                openBinomialDialogue();
                                barcodeValueBool = inputValueBinomial;
                                // ADD BARCODE TO DATABASE (experiment____)
                                //Toast.makeText(CustomBarcodeActivity.this, output, Toast.LENGTH_SHORT).show();
                            }
                        }
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
                Toast.makeText(CustomBarcodeActivity.this, "Camera permission is required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    // getBoolean is for binomial trials, will return true or false depending on success or failure
    private Boolean getBoolean(String result) {
        String value = Character.toString(result.charAt(result.length()-1));
        if ("s".equals(value)) {
            return true;
        }
        else {
            return false;
        }
    }

    private Double getDouble(String result) {
        String doublePlaceHolder = "";

        int i = 0;
        while (i < result.length()) {
            if (" ".equals(Character.toString(result.charAt(i)))) {
                i++;
                break;
            }
            else {
                i++;
            }
        }

        while (i < result.length()) {
            doublePlaceHolder += result.charAt(i);
            i++;
        }

        double inputDouble = Double.parseDouble(doublePlaceHolder);
        return inputDouble;
    }

    private int getInt(String result) {
        String intPlaceHolder = "";

        int i = 0;
        while (i < result.length()) {
            if (" ".equals(Character.toString(result.charAt(i)))) {
                i++;
                break;
            }
            else {
                i++;
            }
        }

        while (i < result.length()) {
            intPlaceHolder += result.charAt(i);
            i++;
        }

        int inputInt = Integer.parseInt(intPlaceHolder);
        return inputInt;
    }

    /*
    @Override
    public void onBackPressed() {

        Bundle experimentDetailsBundle = new Bundle();
        experimentDetailsBundle.putSerializable("experiment", experiment);

        Intent experimentActivityIntent = null;
        if (experiment instanceof Binomial){
            experimentActivityIntent = new Intent(scanView.getContext(), BinomialActivity.class);
        }
        else if (experiment instanceof Count){
            experimentActivityIntent = new Intent(scanView.getContext(), CountActivity.class);
        }
        else if (experiment instanceof Measurement || experiment instanceof NonNegative){

            experimentActivityIntent = new Intent(scanView.getContext(), ValueInputActivity.class);

        }
        experimentActivityIntent.putExtras(experimentDetailsBundle);
        setResult(1, experimentActivityIntent);
        finish();

    }

     */

    public void openInputDialogue() {
        InputValueBarcodeFragment inputValueDialog = new InputValueBarcodeFragment();
        inputValueDialog.show(getSupportFragmentManager(), "input value dialog");
    }
    @Override
    public void applyTexts(String value) {
        inputValue = value;
    }

    public void openBinomialDialogue() {
        BinomialBarcodeFragment inputStateDialog = new BinomialBarcodeFragment();
        inputStateDialog.show(getSupportFragmentManager(), "input state dialog");
    }
    @Override
    public void applyValue(Boolean value) {
        inputValueBinomial = value;
    }


}
