package com.example.proyectodap;

import java.io.Serializable;

public class Champion implements Serializable {

    private String name;
    private String title;

    public Champion(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
