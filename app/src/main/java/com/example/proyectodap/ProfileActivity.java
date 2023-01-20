package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView tvMail;
    ImageView ivProfile;

    private static final String SHARED_PREF_NAME = "rem_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String mail = sharedPreferences.getString("mail",null);
        String pic = sharedPreferences.getString("pic",null);

        byte[] imageAsBytes = Base64.decode(pic.getBytes(), Base64.DEFAULT);


        tvMail = findViewById(R.id.tv_myMail);
        tvMail.setText(mail);

        ivProfile  =findViewById(R.id.im_profilePic);
        ivProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

    }
}