package com.example.crowdtest.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowdtest.ExperimentManager;
import com.example.crowdtest.Experimenter;
import com.example.crowdtest.R;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static android.content.Context.WINDOW_SERVICE;
/**
 * Fragment for generating QR codes for Binomial experiments
 * Title:          QRGenerator
 * Author:         AndroidMad / Mushtaq M A et al, (https://github.com/androidmads)
 * Date:           2021-04-08
 * License:        MIT
 * Availability:   https://github.com/androidmads/QRGenerator
 */
public class QRCodeBinomialFragment extends Fragment {
    private ImageView qrImageTop;
    private ImageView qrImageBot;
    private String inputValue;
    //private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    //private String savePath2 = "/sdk_gphone_86/pictures";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private ImageButton saveButton;
    private AppCompatActivity activity;
    private String inputValueSuccess;


    public QRCodeBinomialFragment(String input){
        inputValue = input;
    }
    /**
     * Custom OnCreateView method for the fragment
     * Gets the type of new experiment being created and constructs it
     * Passes the experiment to the ExperimentDetailsFragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_binomial_qr, container, false);
        qrImageTop = view.findViewById(R.id.qr_image_top);
        inputValueSuccess = inputValue + " s";
        activity = (AppCompatActivity) getActivity();

        if (inputValueSuccess.length() > 0) {

            qrgEncoder = new QRGEncoder(
                    inputValueSuccess, null,
                    QRGContents.Type.TEXT,
                    700);
            try {
                bitmap = qrgEncoder.getBitmap();
                qrImageTop.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        qrImageBot = view.findViewById(R.id.qr_image_bot);
        String inputValueFailure = inputValue + " f";
        if (inputValueFailure.length() > 0) {

            qrgEncoder = new QRGEncoder(
                    inputValueFailure, null,
                    QRGContents.Type.TEXT,
                    700);
            try {
                bitmap = qrgEncoder.getBitmap();
                qrImageBot.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

}