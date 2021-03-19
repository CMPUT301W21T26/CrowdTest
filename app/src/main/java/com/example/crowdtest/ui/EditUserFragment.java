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
import androidx.fragment.app.DialogFragment;

import com.example.crowdtest.R;
import com.example.crowdtest.UserProfile;

/**
 * Fragment to collect contact information in the UserProfileActivity
 */
public class EditUserFragment extends DialogFragment {

    private EditText userEmail;
    private EditText userPhoneNumber;
    private OnFragmentInteractionListener listener;

    /**
     * Fragment listener interface for when OK is pressed
     */
    public interface OnFragmentInteractionListener {
        void onOkPressed(int errorInd);
    }

    /**
     * Create a new fragment with a userProfile
     * @param userProfile
     *     The userProfile of the signed in user
     * @return
     *     The fragment with userProfile set as an argument in bundle
     */
    static EditUserFragment newInstance(UserProfile userProfile) {
        Bundle args = new Bundle();
        args.putSerializable("userProfile", userProfile);

        EditUserFragment fragment = new EditUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Establish a listener given context called
     * @param context
     *     The current context
     */
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

    /**
     * Custom onCreateDialog method for the fragment
     * @param savedInstanceState
     * @return
     *     Dialog fragment
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // inflate view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_user, null);
        // initialize EditText objects
        userEmail = view.findViewById(R.id.edit_user_email);
        userPhoneNumber = view.findViewById(R.id.edit_user_phone_number);
        // grab the UserProfile
        Bundle bundle = this.getArguments();
        final UserProfile userProfile = (UserProfile) bundle.getSerializable("userProfile");
        // Fill EditText objects with UserProfile data
        userEmail.setText(userProfile.getEmail());
        userPhoneNumber.setText(userProfile.getPhoneNumber());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit Your Contact Info")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // get user input from EditText objects
                        String user_email = userEmail.getText().toString();
                        String user_phone_number = userPhoneNumber.getText().toString();
                        int errorInd = 0;
                        // check if data is valid then update user profile
                        if (userProfile.isValidEmail(user_email)) {
                            userProfile.setEmail(user_email);
                        } else {
                            errorInd += 1; // invalid email
                        }
                        if (userProfile.isValidPhoneNumber(user_phone_number)) {
                            userProfile.setPhoneNumber(user_phone_number);
                        } else {
                            errorInd += 2; // invalid phone
                        }
                        listener.onOkPressed(errorInd);
                    }}).create();
    }
}
