package com.example.tmdb;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class TMDBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
