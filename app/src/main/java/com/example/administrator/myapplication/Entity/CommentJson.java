package com.example.administrator.myapplication.Entity;

/**
 * Created by Administrator on 2016/11/8.
 */

public class CommentJson {
   public Comment[] comments;

    @Override
    public String toString() {
        String str="";
        for(int i=0;i<comments.length;i++){
            str += comments[i].toString();
        }
        return  str;
    }
}
