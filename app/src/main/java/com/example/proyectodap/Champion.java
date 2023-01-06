package com.example.proyectodap;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Champion implements Serializable {

    private String name;
    private String title;



    private Bitmap imageBitmap;
    private int hp;
    private int hpperlevel;
    private int mp;
    private int mpperlevel;
    private int movespeed;
    private int armor;
    private int armorperlevel;
    private int spellblock;
    private int spellblockperlevel;
    private int attackrange;
    private int hpregen;
    private int hpregenperlevel;
    private int mpregen;
    private int mpregenperlevel;
    private int crit;
    private int critperlevel;
    private int attackdamage;
    private int attakdamageperlevel;
    private int attackspeedperlevel;
    private int attackspeed;
    private String imagepath;



    public Champion(String name, String title, int hp, int hpperlevel, int mp, int mpperlevel, int movespeed, int armor, int armorperlevel, int spellblock, int spellblockperlevel, int attackrange, int hpregen, int hpregenperlevel, int mpregen, int mpregenperlevel, int crit, int critperlevel, int attackdamage, int attakdamageperlevel, int attackspeedperlevel, int attackspeed,String imagepath) {
        this.name = name;
        this.title = title;
        //"http://ddragon.leagueoflegends.com/cdn/11.8.1/img/champion/"+nombrecampeon+".png"
        this.hp = hp;
        this.hpperlevel = hpperlevel;
        this.mp = mp;
        this.mpperlevel = mpperlevel;
        this.movespeed = movespeed;
        this.armor = armor;
        this.armorperlevel = armorperlevel;
        this.spellblock = spellblock;
        this.spellblockperlevel = spellblockperlevel;
        this.attackrange = attackrange;
        this.hpregen = hpregen;
        this.hpregenperlevel = hpregenperlevel;
        this.mpregen = mpregen;
        this.mpregenperlevel = mpregenperlevel;
        this.crit = crit;
        this.critperlevel = critperlevel;
        this.attackdamage = attackdamage;
        this.attakdamageperlevel = attakdamageperlevel;
        this.attackspeedperlevel = attackspeedperlevel;
        this.attackspeed = attackspeed;
        this.imagepath = imagepath;
    }

    public Champion(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public int getHp() {
        return hp;
    }

    public int getHpperlevel() {
        return hpperlevel;
    }

    public int getMp() {
        return mp;
    }

    public int getMpperlevel() {
        return mpperlevel;
    }

    public int getMovespeed() {
        return movespeed;
    }

    public int getArmor() {
        return armor;
    }

    public int getArmorperlevel() {
        return armorperlevel;
    }

    public int getSpellblock() {
        return spellblock;
    }

    public int getSpellblockperlevel() {
        return spellblockperlevel;
    }

    public int getAttackrange() {
        return attackrange;
    }

    public int getHpregen() {
        return hpregen;
    }

    public int getHpregenperlevel() {
        return hpregenperlevel;
    }

    public int getMpregen() {
        return mpregen;
    }

    public int getMpregenperlevel() {
        return mpregenperlevel;
    }

    public int getCrit() {
        return crit;
    }

    public int getCritperlevel() {
        return critperlevel;
    }

    public int getAttackdamage() {
        return attackdamage;
    }

    public int getAttakdamageperlevel() {
        return attakdamageperlevel;
    }

    public int getAttackspeedperlevel() {
        return attackspeedperlevel;
    }

    public int getAttackspeed() {
        return attackspeed;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
