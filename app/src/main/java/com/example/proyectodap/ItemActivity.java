package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    private Champion champion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ImageView championImg = findViewById(R.id.iv_champion);
        TextView championName = findViewById(R.id.tv_champion_name);

        Intent intent = getIntent();

        Champion c = (Champion) intent.getSerializableExtra("champion");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("championImg");

        championImg.setImageBitmap(bitmap);
        championName.setText(c.getName());


    }
}