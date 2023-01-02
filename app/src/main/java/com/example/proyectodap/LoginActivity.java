package com.example.proyectodap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    CheckBox remeber_me;
    EditText mail, password;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "rem_me";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASSWORD = "password";


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


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(mail) || isEmpty(password))
                    Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
