package com.ecommerce.ecommerce;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Bean extends Application {
    private static Context context;

    public String baseurl = "https://reood.herokuapp.com/";

    String token;

    public static Context getContext() {
        return context;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));


    }
}

