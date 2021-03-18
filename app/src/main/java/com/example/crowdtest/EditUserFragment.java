package com.example.crowdtest;

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
import androidx.fragment.app.DialogFragment;

public class EditUserFragment extends DialogFragment {

    private EditText userName;
    private EditText userEmail;
    private EditText userPhoneNumber;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(UserProfile newUserProfile);
    }

    static EditUserFragment newInstance(UserProfile userProfile) {
        Bundle args = new Bundle();
        args.putSerializable("userProfile", userProfile);

        EditUserFragment fragment = new EditUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_user, null);
        userName = view.findViewById(R.id.edit_user_name);
        userEmail = view.findViewById(R.id.edit_user_email);
        userPhoneNumber = view.findViewById(R.id.edit_user_phone_number);

        Bundle bundle = this.getArguments();
        final UserProfile userProfile = (UserProfile) bundle.getSerializable("userProfile");
        userName.setText(userProfile.getUsername());
        userEmail.setText(userProfile.getEmail());
        userPhoneNumber.setText(userProfile.getPhoneNumber());


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit UserProfile")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String user_name = userName.getText().toString();
                        String user_email = userEmail.getText().toString();
                        String user_phone_number = userPhoneNumber.getText().toString();
                        userProfile.setUsername(user_name);
                        userProfile.setEmail(user_email);
                        userProfile.setPhoneNumber(user_phone_number);
                        listener.onOkPressed(userProfile);
                    }}).create();
    }
}
