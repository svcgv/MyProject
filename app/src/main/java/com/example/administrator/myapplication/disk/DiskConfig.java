package com.example.administrator.myapplication.disk;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2016/11/3.
 */

public class DiskConfig {
 /* - 缓存文件夹
    *   - 缓存文件最大大小
    *   - 默认的加载后的动画
    *   - 图片下载后？要不要秀秀：
    *       - 彩色 -> 黑白 */

    private static String CACHEDIR = "panoramaCache";
    private File cacheDir;
    public DiskConfig(Context context) {
        // 初始化缓存目录
        //先判断sk卡是否存在
        Log.d("xxxname","我到达config的构造函数了");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            //如果存在获取外存卡的下载目录
            cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else {
            cacheDir = Environment.getDataDirectory();
        }
        if (cacheDir != null) {
            cacheDir = new File(cacheDir.getAbsolutePath()+File.separator+CACHEDIR);
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

    }

    public  static  DiskConfig getInstance(Context context){
        return  new DiskConfig(context);
    }

    public File getCacheDir() {
        Log.d("xxx","我的文件名称是"+cacheDir.getAbsolutePath());
        return cacheDir;
    }
}
