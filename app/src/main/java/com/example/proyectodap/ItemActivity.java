package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView itemID = findViewById(R.id.tv_itemID);

        Bundle extras = getIntent().getExtras();

        int id = -1;

        if (extras != null)
            id = extras.getInt("itemID");

        itemID.setText("ITEM ID:" + id);

    }
}