package com.example.akaash.assignment.utils;

import android.app.Application;

/**
 * Created by akaash on 9/1/18.
 */

public class App extends Application {
    private static App instance;

    private static AppPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appPreferences = AppPreferences.init(this);

    }

    public static App getAppContent() {
        return instance;
    }

    public static AppPreferences getPreference() {
        return appPreferences;
    }
}
