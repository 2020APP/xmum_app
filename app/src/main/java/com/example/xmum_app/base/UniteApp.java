package com.example.xmum_app.base;

import android.app.Application;

import com.example.xmum_app.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
