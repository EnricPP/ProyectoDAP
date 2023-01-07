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
        TextView championBlurb = findViewById(R.id.tv_champ_blurb);
        TextView championStats = findViewById(R.id.tv_champ_stats);
        TextView championHP = findViewById(R.id.tv_champion_hp);
        TextView championArmor = findViewById(R.id.tv_champion_armor);
        TextView championAtckSpeed = findViewById(R.id.tv_champion_spd);
        TextView championAtckDamage = findViewById(R.id.tv_champion_dmg);
        TextView championAtckRange = findViewById(R.id.tv_champion_rng);
        TextView championSpellBlock = findViewById(R.id.tv_spell_blk);

        Intent intent = getIntent();

        Champion c = (Champion) intent.getSerializableExtra("champion");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("championImg");

        championImg.setImageBitmap(bitmap);
        championBlurb.setText(c.getBlurb());
        championStats.setText(c.getChampionStats());
        championHP.setText(Double.toString(c.getHp()));
        championArmor.setText(Double.toString(c.getArmor()));
        championAtckSpeed.setText(Double.toString(c.getAttackspeed()));
        championAtckDamage.setText(Double.toString(c.getAttackdamage()));
        championAtckRange.setText(Double.toString(c.getAttackrange()));
        championSpellBlock.setText(Double.toString(c.getSpellblock()));

        getSupportActionBar().setTitle(c.getName());
    }
}