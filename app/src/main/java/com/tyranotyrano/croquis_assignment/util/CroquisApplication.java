package com.tyranotyrano.croquis_assignment.util;

import android.app.Application;
import android.content.Context;

public class CroquisApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        CroquisApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CroquisApplication.context;
    }
}
