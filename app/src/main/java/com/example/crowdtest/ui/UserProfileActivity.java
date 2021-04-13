package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crowdtest.Experimenter;
import com.example.crowdtest.ExperimenterManager;
import com.example.crowdtest.R;
import com.example.crowdtest.UserProfile;

/**
 * Activity for viewing the user profile, uses a fragment to allow for editing of contact information
 * in the UserProfile object
 */
public class UserProfileActivity extends AppCompatActivity{

    private ImageButton editProfileButton;
    private ImageButton backButton;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhoneNumber;
    private TextView warningMessage;
    private UserProfile userProfile;
    private Experimenter user;
    private Intent intent = new Intent();
    private ExperimenterManager experimenterManager = new ExperimenterManager();

    private ProfileEditorHelper profileEditorHelper;

    /**
     * Custom OnCreate method for the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        // get intents Experimenter and its UserProfile
        user = (Experimenter) getIntent().getSerializableExtra("User");
        userProfile = user.getUserProfile();
        // initialize TextViews and Buttons
        editProfileButton = findViewById(R.id.edit_profile_button);
        backButton = findViewById(R.id.back_button_user_profile_activity);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPhoneNumber = findViewById(R.id.user_phone_number);
        warningMessage = findViewById(R.id.warning_message);
        // update the views
        updateViews();
        // onClickListener for the edit button. Creates a fragment to edit info
        editProfileButton.setOnClickListener(view -> {

            profileEditorHelper = new ProfileEditorHelper(this, experimenterManager, user);
            profileEditorHelper.editUserProfile("Edit Your Contact Info", "Save", "Cancel");

        });
        // onClickListener for the back button. Sends back experimenter and finishes activity
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * Method for updating all UI objects and firestore database
     */
    public void updateViews() {
        userName.setText(userProfile.getUsername());
        userEmail.setText(userProfile.getEmail());
        userPhoneNumber.setText(userProfile.getPhoneNumber());
    }
    
    /**
     * Overrides the devices back button to move app to the background instead of returning to the previous activity
     */
    @Override
    public void onBackPressed() {
        goBack();
    }

    /**
     * Private function for returning to the main page
     */
    private void goBack() {
        intent.putExtra("User", user);
        setResult(RESULT_OK, intent);
        finish();
    }
}


