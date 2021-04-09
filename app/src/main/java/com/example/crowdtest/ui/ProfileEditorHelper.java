package com.example.crowdtest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.R;
import com.example.crowdtest.UserProfile;

/**
 * ProfileEditorHelper class for showing and defining the behaviour for Dialog Fragments
 * when editing an experimenters user profile
 */
public class ProfileEditorHelper {

    private Context context;
    private ExperimenterManager experimenterManager;
    private Experimenter user;
    private UserProfile userProfile;

    /**
     * ProfileEditorHelper constructor
     * @param activity  :   Activity instance to be 'helped'
     * @param experimenterManager
     * @param user
     */
    public ProfileEditorHelper(Activity activity, ExperimenterManager experimenterManager, Experimenter user) {
        this.context = (Context) activity;
        this.experimenterManager = experimenterManager;
        this.user = user;
    }

    /**
     * Function for creating and showing an AlertDialog with text boxes for editing user contact information
     * @param title
     * @param posButtonText
     * @param negButtonText
     */
    public void editUserProfile(String title, String posButtonText, String negButtonText) {
        // Build an AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.fragment_edit_user, null);

        // Set builder attributes
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton(posButtonText, null);
        builder.setNegativeButton(negButtonText, null);

        // Get user text
        final EditText emailEditText = dialogView.findViewById(R.id.edit_user_email);
        final EditText numberEditText = dialogView.findViewById(R.id.edit_user_phone_number);

        userProfile = user.getUserProfile();

        emailEditText.setText(userProfile.getEmail());
        numberEditText.setText(userProfile.getPhoneNumber());

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();

        // Setup positive button
        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user text
                String emailUserText = emailEditText.getText().toString();
                String numberUserText = numberEditText.getText().toString();

                // Validate text
                if (userProfile.isValidEmail(emailUserText) && userProfile.isValidPhoneNumber(numberUserText)) {
                    userProfile.setEmail(emailUserText);
                    userProfile.setPhoneNumber(numberUserText);
                    experimenterManager.updateExperimenter(user);
                    ((UserProfileActivity)context).updateViews();
                    alert.dismiss();
                } else {
                    if (!userProfile.isValidEmail(emailUserText)) {
                        showInvalidInfoDialog("Invalid Email", "Example Format: jsmith@example.com");
                    }

                    if (!userProfile.isValidPhoneNumber(numberUserText)) {
                        showInvalidInfoDialog("Invalid Phone Number", "Only use digits. Max 15");
                    }
                }
            }
        });

        // Setup negative button
        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    /**
     * Public function for handling the dialog when the user enters invalid information
     * @param title     :   Title of dialog fragment
     * @param message   :   Message to print to the dialog fragment
     *
     * Title:          How do I display an alert dialog on Android?
     * Author:         David Hedlund, https://stackoverflow.com/users/133802
     * Date:           2021-02-06
     * License:        CC BY-SA
     * Availability:   https://stackoverflow.com/a/2115770
     */
    public void showInvalidInfoDialog(String title, String message) {
        // Build the AlertDialog and define its contents
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

        // Set builder attributes
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        // Dismiss the dialog if user presses "Ok"
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            /**
             * Function for defining the dialog's behavior when the "Ok" button is pressed
             * @param dialog    :   The dialog that received the click
             * @param which     :   The button that was clicked (represented by an integer)
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }
}
