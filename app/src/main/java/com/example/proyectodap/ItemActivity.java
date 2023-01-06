package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("MAIN3", "CAMPEON");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        Log.i("MAIN1", "CAMPEON");
        TextView itemID = findViewById(R.id.tv_itemID);

        Log.i("MAIN2", "CAMPEON");
        Bundle extras = getIntent().getExtras();

        Log.i("MAIN3", "CAMPEON");
        int id = 0;

        if (extras != null)
            id = extras.getInt("itemID");

        itemID.setText("ITEM ID:" + id);

    }
}