package com.baidu.duer.dcs.androidapp;
import android.app.Application;

public class DcsSampleApplication extends Application {
    private static volatile DcsSampleApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static DcsSampleApplication getInstance() {
        return instance;
    }
}