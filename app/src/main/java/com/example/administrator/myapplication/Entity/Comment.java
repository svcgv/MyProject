package com.example.administrator.myapplication.Entity;

/**
 * Created by Administrator on 2016/11/7.
 */

public class Comment {
    public int id;
    public int comment_id;
    public String username;
    public String commt;
    public String timedate;

    @Override
    public String toString() {
        return ""+id+comment_id+username+timedate;
    }
}
