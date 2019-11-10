package com.example.cadastroproduto;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

public class MyApp extends Application {
    private static MyApp instance; //estava retornando null pq não foi definida no android manifest


    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
//        instance.getApplicationContext();
    }

    public static String versionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }
}
