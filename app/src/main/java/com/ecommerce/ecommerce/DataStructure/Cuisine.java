package com.ecommerce.ecommerce.DataStructure;

import android.graphics.drawable.Drawable;

public class Cuisine {
    private String name;
    private Drawable icon;

    public Cuisine(String name, Drawable icon){
        this.name = name;
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
