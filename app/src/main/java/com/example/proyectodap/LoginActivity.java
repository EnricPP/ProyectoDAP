package com.example.proyectodap;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    CheckBox remeber_me;
    EditText mail, password;
    ImageButton profilePic;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "rem_me";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PIC = "pic";


    private static final int pic_id = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences.Editor editor;

        //Comprobar que el usuario no este ya logeado anteriormente
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_MAIL,null) != null)
            startMainActivity();

        login_btn = findViewById(R.id.idLogin);
        remeber_me = findViewById(R.id.idRememberMe);
        mail = findViewById(R.id.idUser);
        password = findViewById(R.id.idPassword);
        profilePic = findViewById(R.id.idProfilePic);


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Start the activity with camera_intent, and request pic id
               someActivityResultLauncher.launch(camera_intent);
            }

        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(mail) || isEmpty(password))
                    Toast.makeText(getApplicationContext(),"Rellene mínimo los de Email y Contraseña",Toast.LENGTH_SHORT).show();
                else{

                    if (remeber_me.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_MAIL,mail.getText().toString());
                        editor.putString(KEY_PASSWORD,password.getText().toString());


                        editor.apply();
                    }
                    startMainActivity();
                }
            }
        });
    }



    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // BitMap is data structure of image file which store the image in memory
                        Intent data = result.getData();
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        // Set the image in imageview for display

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encoded = Base64.encodeToString(b, Base64.DEFAULT);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_PIC,encoded);
                        editor.apply();

                        profilePic.setImageBitmap(photo);

                    }
                }
            });



    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    private void startMainActivity() {
        Intent startMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(startMainActivity);
        finish();
    }





}
