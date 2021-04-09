package com.example.simplebookkeeping;

import android.app.Application;

import com.example.simplebookkeeping.db.DBManager;

/*
 *
 * 全局应用类
 */
public class UniteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        只有一个Context，要在AndroidManifest.xml注册
        DBManager.initDatabase(getApplicationContext());
    }
}
