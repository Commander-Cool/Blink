package com.capstone.blink.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Initializer extends Application {

    /**
     * Registers the app to the database
     */

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return Initializer.context;
    }
}
