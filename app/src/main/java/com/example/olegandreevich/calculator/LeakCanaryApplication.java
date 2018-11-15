package com.example.olegandreevich.calculator;

import android.app.Application;
import android.content.Context;


import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class LeakCanaryApplication extends Application {
    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        LeakCanaryApplication exampleApplication = (LeakCanaryApplication) context.getApplicationContext();
        return exampleApplication.refWatcher;
    }
}
