package com.engine;

import android.app.Application;
import android.content.Context;

/**
 * Created by gaylen on 2016/3/21.
 */
public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
