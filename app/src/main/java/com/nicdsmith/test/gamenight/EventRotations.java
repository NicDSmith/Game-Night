package com.nicdsmith.test.gamenight;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by nicsmith on 1/26/16.
 */
public class EventRotations extends Application{
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
