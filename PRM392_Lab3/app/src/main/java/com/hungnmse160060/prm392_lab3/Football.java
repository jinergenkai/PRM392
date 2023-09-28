package com.hungnmse160060.prm392_lab3;

import android.graphics.drawable.Drawable;

public class Football {
    private String name;
    private String description;
    private int image;
    private int flag;

    public Football() {
    }

    public Football(String name, String description, int image, int flag) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
