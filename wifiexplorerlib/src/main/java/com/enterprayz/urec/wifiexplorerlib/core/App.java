package com.enterprayz.urec.wifiexplorerlib.core;

import android.app.Application;
import android.content.Context;

/**
 * Created by con on 27.04.15.
 */
public final class App extends Application {
    private static Context context;


    @Override

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
