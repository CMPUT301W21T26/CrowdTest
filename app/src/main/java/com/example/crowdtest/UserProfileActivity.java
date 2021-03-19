package com.example.crowdtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity implements EditUserFragment.OnFragmentInteractionListener{

    private ImageButton editProfileButton;
    private ImageButton backButton;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhoneNumber;
    private UserProfile userProfile;
    private Experimenter user;
    private Intent intent = new Intent();
    private ExperimenterManager experimenterManager = new ExperimenterManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = (Experimenter) getIntent().getSerializableExtra("User");
        userProfile = user.getUserProfile();

        editProfileButton = findViewById(R.id.edit_profile_button);
        backButton = findViewById(R.id.back_button_user_profile_activity);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPhoneNumber = findViewById(R.id.user_phone_number);

        updateViews();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserFragment fragment = new EditUserFragment().newInstance(userProfile);
                fragment.show(getSupportFragmentManager(), "userProfile");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("User", user);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void updateViews() {
        userName.setText(userProfile.getUsername());
        userEmail.setText(userProfile.getEmail());
        userPhoneNumber.setText(userProfile.getPhoneNumber());
        experimenterManager.updateExperimenter(user);
    }

    @Override
    public void onOkPressed() {
        updateViews();
    }
}


