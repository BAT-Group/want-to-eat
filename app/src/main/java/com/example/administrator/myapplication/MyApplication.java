package com.example.administrator.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created with Android Studio
 * User: yuanxiaoru
 * Date: 2018/8/21.
 */

public class MyApplication extends Application{

    private static MyApplication instance;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (mContext == null){
            mContext = getApplicationContext();
        }
    }
    /**
     * 获取application实例
     * @return
     */
    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return mContext;
    }
}
