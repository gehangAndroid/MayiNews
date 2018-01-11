package com.mayinews.z.app;

import android.app.Application;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static MyApplication getInstance() {
        return instance;
    }
}
