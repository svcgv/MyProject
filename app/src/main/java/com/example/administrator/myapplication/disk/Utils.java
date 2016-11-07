package com.example.administrator.myapplication.disk;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/11/3.
 */

public class Utils {
    public static void CopyStream(InputStream bis, OutputStream bos) throws IOException {
        int buffer_size = 1024;
        byte[] bytes=new byte[buffer_size];
        for(;;)
        {
            int count= bis.read(bytes, 0, buffer_size);
         //   Log.d("xxx", "copy "+count);
            if(count==-1)
                break;
            bos.write(bytes, 0, count);
        }
        bos.flush();
    }
}
