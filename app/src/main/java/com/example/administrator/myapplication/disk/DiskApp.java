package com.example.administrator.myapplication.disk;

import android.app.Application;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/3.
 */

public class DiskApp extends Application {
    DiskConfig disk;

    @Override
    public void onCreate() {
        super.onCreate();
       config();
    }

    public  void  config(){
        Log.d("xxxname1","我进来了config");
        disk=DiskConfig.getInstance(this);

    }
    public  DiskConfig getDisk(){
        Log.d("xxxname1","我进来了getDisk");
        return disk;
    }
}
