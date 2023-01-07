package com.example.proyectodap;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Champion implements Serializable {

    private String id;
    private ArrayList<String> stats;
    private String name;
    private String title;
    private String blurb;
    private transient Bitmap imageBitmap;
    private double hp;
    private double armor;
    private double spellblock;
    private double attackdamage;
    private double attackspeed;
    private double attackrange;
    private String imagepath;


    public Champion(ArrayList<String> stats, String id, String name, String title, String blurb, double hp, double armor,
                    double spellblock, double attackdamage, double attackspeed, double attackrange,String imagepath) {

        this.stats = stats;
        this.id = id;
        this.name = name;
        this.title = title;
        this.blurb = blurb;
        this.hp = hp;
        this.armor = armor;
        this.spellblock = spellblock;
        this.attackdamage = attackdamage;
        this.attackspeed = attackspeed;
        this.attackrange = attackrange;
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getImagepath() {
        return imagepath;
    }


    public String getChampionStats(){
        String stats = String.join("/",this.stats);
        return stats;
    }

    public double getHp() {
        return hp;
    }

    public double getArmor() {
        return armor;
    }

    public double getSpellblock() {
        return spellblock;
    }

    public double getAttackdamage() {
        return attackdamage;
    }

    public double getAttackrange() {
        return attackrange;
    }

    public double getAttackspeed() {
        return attackspeed;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
