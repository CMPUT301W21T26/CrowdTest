package com.example.crowdtest.ui;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
/*
/**
 * Fragment for displaying measurement QR text
 */
public class QRCodeBinomialFragment extends Fragment {
    private ImageView qrImageTop;
    private ImageView qrImageBot;
    private String inputValue;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;

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
        String inputValueSuccess = inputValue + " s";

        if (inputValueSuccess.length() > 0) {

            qrgEncoder = new QRGEncoder(
                    inputValueSuccess, null,
                    QRGContents.Type.TEXT,
                    500);
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
                    inputValueSuccess, null,
                    QRGContents.Type.TEXT,
                    500);
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