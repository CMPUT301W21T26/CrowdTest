package com.example.crowdtest.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.crowdtest.R;

public class InputValueBarcodeFragment extends AppCompatDialogFragment {
    private EditText value;
    private InputValueBarcodeFragmentListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_inputvalue_barcode, null);
        builder.setView(view)
                .setTitle("Custom Barcode Trial Value")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        value = view.findViewById(R.id.barcodeTrialValue);
                        String number = value.getText().toString();
                        listener.applyTexts(number);
                        //getActivity().finish();
                    }
                });
        //value = view.findViewById(R.id.barcodeTrialValue);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (InputValueBarcodeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface InputValueBarcodeFragmentListener {
        void applyTexts(String value);
    }


}
