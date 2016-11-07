package com.example.administrator.myapplication.utils;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.myapplication.staticc.DefineStatic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Administrator on 2016/9/17.
 */
public class LoginDoGet {
     public  String url;
     public Handler handler;
     String flag;
     public LoginDoGet(String flag,Handler handler,String url) {
        this.url = url;
         this.handler=handler;
         this.flag=flag;
    }
    public void DoGet(){
        try {
            URL u=new URL(url);
            Log.d("xx",url);
            HttpURLConnection conn= (HttpURLConnection) u.openConnection();
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result="";
            String line;
            while((line= reader.readLine())!=null){
                result+=line;
            }
            Log.d("xxx",result);
            //直接可以获得主线程的handle
     //       Handler handle=new Handler(Looper.getMainLooper()) ;
          Log.d("xxx",result);
            Message ms=Message.obtain();
            ms.obj=result;
            if(flag== DefineStatic.LOGIN){
            if(result.equals("成功")){
                ms.what= DefineStatic.SUCCESS;
            }else {
                ms.what=DefineStatic.FEILD;
            }
            }
            if(flag==DefineStatic.RESIGSTER){
                if(result.equals("成功")){
                    ms.what= DefineStatic.Resisger_Success;
                }else {
                    ms.what=DefineStatic.Resisger_Field;
                }
            }
           handler.sendMessage(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
