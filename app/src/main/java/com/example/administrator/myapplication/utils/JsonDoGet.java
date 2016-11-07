package com.example.administrator.myapplication.utils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Administrator on 2016/9/26.
 */
public class JsonDoGet {
    public  String url;
    public JsonDoGet(String url) {
        this.url = url;
    }
    public String DoGet(){
        String result = "";
            try {
                URL u = new URL(url);
                Log.d("xx", url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
             return result;
    }
}
