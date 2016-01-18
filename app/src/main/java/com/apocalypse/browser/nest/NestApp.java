package com.apocalypse.browser.nest;

import android.app.Application;

import com.apocalypse.browser.nest.utils.SimpleLog;

/**
 * Created by Dave on 2016/1/13.
 */
public class NestApp extends Application{
    private static final String TAG = "NestApp";
    @Override
    public void onCreate() {
        SimpleLog.d(TAG, " onCreate");
        super.onCreate();
    }
}
