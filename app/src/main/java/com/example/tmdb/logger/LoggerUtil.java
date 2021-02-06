package com.example.tmdb.logger;

import android.util.Log;

import com.example.tmdb.BuildConfig;

public class LoggerUtil {

    public static void log(String message){
        if(BuildConfig.DEBUG){
            Log.d("Taaha", message);
        }
    }
}
