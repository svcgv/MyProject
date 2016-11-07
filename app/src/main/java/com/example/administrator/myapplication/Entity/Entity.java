package com.example.administrator.myapplication.Entity;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragments.Fragment_five;
import com.example.administrator.myapplication.fragments.Fragment_four;
import com.example.administrator.myapplication.fragments.Fragment_one;
import com.example.administrator.myapplication.fragments.Fragment_three;
import com.example.administrator.myapplication.fragments.Fragment_two;
import com.example.administrator.myapplication.staticc.DefineStatic;

/*
* *
 * Created by Administrator on 2016/8/21.
 */
public class Entity {
  public  static  int vt[]={
            R.drawable.main_bottom_attention_normal,
            R.drawable.main_bottom_essence_normal,
            R.drawable.main_bottom_public_normal,
            R.drawable.main_bottom_mine_normal,
            R.drawable.main_bottom_newpost_normal,

   };
    public static String titile[]={
            "精华",
            "新帖",
            "关注",
            "目录",
            "我",
    };
   public static Class<?> fragmentClass[]={
                Fragment_one.class,
                Fragment_two.class,
                Fragment_three.class,
                Fragment_four.class,
                Fragment_five.class,
    };
    public  static  String[] URL={
            DefineStatic.INFO,
            DefineStatic.INFO,
            DefineStatic.INFO,
    };
    public  static  String[] CODE={
            "11",
            "22",
            "33",
    };



}
