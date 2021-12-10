package com.example.recipe;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String title;
    private int id;

    public Recipe(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
