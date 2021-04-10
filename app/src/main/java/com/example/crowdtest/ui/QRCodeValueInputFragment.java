package com.example.crowdtest.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crowdtest.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Fragment for generating QR codes for Count experiments
 * Title:          QRGenerator
 * Author:         AndroidMad / Mushtaq M A et al, (https://github.com/androidmads)
 * Date:           2021-04-08
 * License:        MIT
 * Availability:   https://github.com/androidmads/QRGenerator
 */
public class QRCodeValueInputFragment extends Fragment {
    private ImageView qrImage;
    private String inputValue;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private String inputValueUpdated;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private TextView text;
    private Button changeValueButton;
    private EditText changeValueEditText;
    private boolean isMeasurement;

    public QRCodeValueInputFragment(String input, boolean type){
        inputValue = input;
        if (type) {
            isMeasurement = true;
        }
        else {
            isMeasurement = false;
        }

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
        View view = inflater.inflate(R.layout.fragment_inputvalue_qr, container, false);
        qrImage = view.findViewById(R.id.qr_image);
        //text.setText("Add NonNegative Count:");
        text = view.findViewById(R.id.count_textView);
        if (inputValue.length() > 0) {

            qrgEncoder = new QRGEncoder(
                    inputValue + " 1", null,
                    QRGContents.Type.TEXT,
                    800);
            try {
                bitmap = qrgEncoder.getBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        changeValueEditText = view.findViewById(R.id.value_new_qr);
        changeValueButton = (Button) view.findViewById(R.id.value_input_change_button);
        changeValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMeasurement) {
                    text.setText("Add count (set to " + changeValueEditText.getText().toString() + ")");
                    double trialInput = Double.parseDouble(changeValueEditText.getText().toString());
                    inputValueUpdated = inputValue + " " + Double.toString(trialInput);
                    changeValueEditText.setText("");

                }
                else {
                    text.setText("Add count (set to " + changeValueEditText.getText().toString() + ")");
                    int trialInput = Integer.parseInt(changeValueEditText.getText().toString());
                    inputValueUpdated = inputValue + " " + Integer.toString(trialInput);
                    changeValueEditText.setText("");

                }
                if (inputValueUpdated.length() > 0) {

                    qrgEncoder = new QRGEncoder(
                            inputValueUpdated, null,
                            QRGContents.Type.TEXT,
                            800);
                    try {
                        bitmap = qrgEncoder.getBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}