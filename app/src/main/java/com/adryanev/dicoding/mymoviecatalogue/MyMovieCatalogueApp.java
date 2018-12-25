package com.adryanev.dicoding.mymoviecatalogue;

import android.app.Application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class MyMovieCatalogueApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this);

    }
}
