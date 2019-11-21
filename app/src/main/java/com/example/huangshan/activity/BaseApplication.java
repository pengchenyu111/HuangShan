package com.example.huangshan.activity;

import android.app.Application;

/**
 * 要在 AndroidManifest.xml 中设置启动的 Application 为 BaseApplication
 *
 * 便于快速的获得全局的 context
 *
 * 使用方法：BaseApplication.getInstance().getApplicationContext();
 */
public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }
    public static BaseApplication getInstance(){
        return mBaseApplication;
    }
}