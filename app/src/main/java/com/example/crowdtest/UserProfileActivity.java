package com.example.crowdtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity implements EditUserFragment.OnFragmentInteractionListener{

    private ImageButton editProfileButton;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhoneNumber;
    private UserProfile userProfile;
//    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfile = new UserProfile("RANDOM");

        editProfileButton = findViewById(R.id.edit_profile_button);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPhoneNumber = findViewById(R.id.user_phone_number);

        updateViews();

//        userProfile = (UserProfile) getIntent().getSerializableExtra("UserProfile");

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserFragment fragment = new EditUserFragment().newInstance(userProfile);
                fragment.show(getSupportFragmentManager(), "userProfile");
//                new EditUserFragment().show(getSupportFragmentManager(), "EDIT_USERPROFILE");
//                updateViews();
            }
        });

        final Button backButton = findViewById(R.id.back_button_user_profile_activity);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateViews() {
        userName.setText(userProfile.getUsername());
        userEmail.setText(userProfile.getEmail());
        userPhoneNumber.setText(userProfile.getPhoneNumber());
    }

    @Override
    public void onOkPressed(UserProfile updatedUserProfile) {
        userProfile = updatedUserProfile;
        updateViews();
    }
}
