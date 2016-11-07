package com.example.administrator.myapplication.bean;

/**
 * Created by Administrator on 2016/9/26.
 */
public class JsonInfo {
    /*
    {
         "firstname": "zhou",
         "lastname": "jian",
         "myInfo": [
            {
                "address": "b1",
                "name": "zj",
                "first": "last",
                "last": "u1",
                "middle": "z"
            },
            {
                "address": "b1",
                "name": "zj",
                "first": "last",
                "last": "u1",
                "middle": "z"
            },
            {
                "address": "b1",
                "name": "zj",
                "first": "last",
                "last": "u1",
                "middle": "z"
            },
            {
                "address": "b1",
                "name": "zj",
                "first": "last",
                "last": "u1",
                "middle": "z"
            }
        ]
    }
    * */
/*  public String firstname;
    public String lastname;
    //myInfo myInfo 才是对应数组的名字
    public MyInfo[] myInfo;
    public class MyInfo {
    public String address;
    public String name;
    public String first;
    public String last;
    public String middle;
    }*/
    public Info[] infos;
    public class Info{
        public int id;
        public String username;
        public String contenttext;
        public String contentimageurl;
        public String date;
    }
}

